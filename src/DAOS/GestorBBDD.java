
package DAOS;

import BEANS.Cliente;
import BEANS.Hilo_BarraProgreso;
import BEANS.Producto;
import BEANS.Venta;
//IMPORTACIONES NECESARIAS PARA TRABAJAR CON LA BBDD DB4O
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import java.io.File;
import java.io.FileWriter;

import java.io.IOException;
import java.sql.CallableStatement;
//IMPORTACIONES NECESARIASPARA TRABAJAR CON MYSQL Y SQLITE
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
//IMPORTACIONES NECESARIAS PARA TRABAJAR CON FICHEROS XML
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class GestorBBDD {

    //FICHEROS XML Y DB4O
    private File ficheroXML;
    private final String DB4O = "efecto2000.yap";
    private ObjectContainer db4oC;
    
    //DATOS PARA CONEXIÓN SERVIDOR MYSQL
    private String user;
    private String pass;
    private String databaseName;
    private String servidor_puerto;
    
    //PREPAREDSTATEMENT Y CADENAS
    private PreparedStatement preparedInsercion;
    private PreparedStatement preparedBusquedaObjeto;
    
    //CADENA PARA LAS DIFERENTES CONSULTAS
    private final String insercionClientes = "insert into clientes (nombre,direccion,poblacion,telefono,nif) values (?,?,?,?,?)";
    private final String busquedaNif = "select count(nif) from clientes where nif=?";
    private final String insercionProductos = "insert into productos (id,descripcion,stockactual,stockminimo,pvp) values (?,?,?,?,?)";
    private final String busquedaIdProducto = "select count(id) from productos where id=?";
    private final String consultaTodosClientes = "select nombre,direccion,poblacion,telefono,nif from clientes";
    private final String consultaTodosProductos = "select id,descripcion,stockactual,stockminimo,pvp from productos";
    private final String consultaCliente = "select nombre,direccion,poblacion,telefono from clientes where nif=?";
    private final String consultaProducto = "select descripcion,stockactual,stockminimo,pvp from productos where id=?";
    private final String updateTablaClientes = "update clientes set nombre=?, direccion=?, poblacion=?, telefono=?, nif=? where nif=?";
    private final String updateTablaProducto = "update productos set id=?, descripcion=?, stockactual=?, stockminimo=?, pvp=? where id=?";
    private final String consultaVentasVacia = "select count(idventa) from ventas";
    private final String consultaSiguienteIdVentas = "select max(idventa) from ventas";
    private final String consultaStockActualProducto = "select stockactual from productos where id=?";
    private final String insercionVenta = "insert into ventas (fechaventa,cliente, total) values (?,?,?)";
    private final String insercionLinea = "insert into lineas (idventa,idproducto,cantidad) values (?,?,?)";
    private final String cambioStockProducto = "update productos set stockactual = ? where id = ?";
    private final String comprobarStockMinimo = "select (stockactual - stockminimo)from productos where id = ?";
    
    //LLAMADAS A PROCEDIMIENTOS  Y FUNCIONES ALMACENADOS
    private final String llamadaProcedimientoInsertarVenta = "{call insertarVenta (?,?)}";
    private final String llamadaProcedimientoInsertarLinea = "{call insertarLinea (?,?,?)}";
    private final String funcionCambioStock = "select cambioStock(?,?)";
    
    
    //BARRA DE PROGRESO REFERENCIADA DE LA INTERFACE
    private JProgressBar barraProgreso1;
    //HILO PARA MANEJAR LA BARRA
    private Hilo_BarraProgreso h1;

    public GestorBBDD(String dbName, String serverPuerto, String user, String password, JProgressBar barra) {
        this.databaseName = dbName;
        this.servidor_puerto = serverPuerto;
        this.user = user;
        this.pass = password;
        this.barraProgreso1 = barra;
        this.h1 = null;
    }

    //MÉTODO QUE DEVUELVE UNA CONEXIÓN DEPENDIENDO DEL TIPO QUE NOS SOLICITEN
    public Connection crearConexion(String tipo) {
        Connection cn = null;
        tipo = tipo.toLowerCase();
        switch (tipo) {
            case "mysql":
                cn = conexionMysql();
                break;
            case "sqlite":
                cn = conexionSQLITE();
                break;
        }
        return cn;
    }

    //MÉTODO QUE DEVUELVE UNA CONEXIÓN MYSQL
    public Connection conexionMysql() {
        Connection cn = null;
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();

            cn = DriverManager.getConnection("jdbc:mysql://" + this.servidor_puerto + "/" + this.databaseName, this.user, this.pass);
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
            mostrarPanelError(e.getLocalizedMessage());
        }
        return cn;
    }

    //MÉTODO QUE DEVUELVE UNA CONEXIÓN SQLITE
    public Connection conexionSQLITE() {
        Connection cn = null;
        try {
            Class.forName("org.sqlite.JDBC");
            cn = DriverManager.getConnection("jdbc:sqlite:efecto2000.db");
        } catch (ClassNotFoundException | SQLException e) {
            mostrarPanelError(e.getLocalizedMessage());
        }
        return cn;
    }

    //MÉTODO QUE DEVUELVE UN MAPA CON LOS OBJETOS INSERTADOS EN LA BBDD
    //QUE LE PASEMOS COMO PARÁMETRO
    public HashMap<String, ArrayList> cargarFicherosXML(String tipo) {
        Connection cn = null;
        try {
            
            //ABRIMOS EL FICHERO XML Y CONFECCIONAMOS ARRAYLIST CON CLIENTES Y PRODUCTOS
            SAXBuilder builder = new SAXBuilder();
            Document document = builder.build(this.ficheroXML);
            Element root = document.getRootElement();

            Element elemClientes = root.getChild("CLIENTES");
            Element elemProductos = root.getChild("PRODUCTOS");
            
            List<Element> listaElemClientes;
            List<Element> listaElemProductos;
            
            //CONTROLAMOS QUE EL FICHERO TENGA ELEMENTOS CLIENTE Y/O PRODUCTO
            if(elemClientes != null)
                listaElemClientes = elemClientes.getChildren("CLIENTE");
            else
                listaElemClientes = null;
                
            if(elemProductos != null)
                listaElemProductos = elemProductos.getChildren("PRODUCTO");
            else
                listaElemProductos =  null;

            ArrayList<Cliente> listaClientes = crearListaClientes(listaElemClientes);
            ArrayList<Producto> listaProductos = crearListaProductos(listaElemProductos);
            
            //DEPENDIENDO DE QUE BBDD NOS INDIQUEN PEDIMOS LA CONEXIÓN,
            //REALIZAMOS LA INSERCIÓN Y DEVOLVEMOS EL MAPA CON LOS OBJETOS INSERTADOS
            switch (tipo) {
                case "mysql":
                    cn = conexionMysql();
                    break;
                case "sqlite":
                    cn = conexionSQLITE();
                    break;
                case "db4o":
                    return insertarDB4O_XML(listaClientes, listaProductos);
            }
            
            return insertarListasProductosClientes(cn, listaProductos, listaClientes);
            
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(GestorBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    //MÉTODO QUE CREA UN ARRAYLIST DE CLIENTES A PARTIR DE UNA LISTA DE ELEMENTOS XML
    public ArrayList<Cliente> crearListaClientes(List<Element> elemClientes) {
        
        ArrayList<Cliente> listaC = new ArrayList();
           
        if(elemClientes != null){
            for (Element elem : elemClientes) {
                String nombre = elem.getChildText("NOMBRE");
                String direccion = elem.getChildText("DIRECCION");
                String poblacion = elem.getChildText("POBLACION");
                String telefono = elem.getChildText("TELEFONO");
                String nif = elem.getChildText("NIF");

                listaC.add(new Cliente(nombre, direccion, poblacion, telefono, nif));         
            }
        }
        return listaC;
    }

    //MÉTODO QUE CREA UN ARRAYLIST DE PRODUCTOS A PARTIR DE UNA LISTA DE ELEMENTOS XML
    public ArrayList<Producto> crearListaProductos(List<Element> elemProductos) {
        
        ArrayList<Producto> listaP = new ArrayList();
        
        if(elemProductos != null){
            for (Element elem : elemProductos) {
                int id = Integer.parseInt(elem.getChildText("ID"));
                String descripcion = elem.getChildText("DESCRIPCION");
                int stockA = Integer.parseInt(elem.getChildText("STOCKACTUAL"));
                int stockM = Integer.parseInt(elem.getChildText("STOCKMINIMO"));
                float pvp = Float.parseFloat(elem.getChildText("PVP"));

                listaP.add(new Producto(id, stockA, stockM, descripcion, pvp));
            }
        }
        return listaP;
    }

    //MÉTODO QUE INSERTA LOS ARRAYLIST DE CLIENTES Y PRODUCTOS EN LA BBDD
    //DEVUELVE UN MAPA CON LAS INSERCIONES
    //ANTES DE INSERTAR COMPRUEBA QUE DICHO OBJETO NO SE ENCUENTRE YA EN LA BBDD
    public HashMap<String, ArrayList> insertarListasProductosClientes(Connection cn, ArrayList<Producto> listaP, ArrayList<Cliente> listaC) {
        
        HashMap<String, ArrayList> mapaInserciones = new HashMap();
        ArrayList<Cliente> listaCInsertados = new ArrayList();
        ArrayList<Producto> listaPInsertados = new ArrayList();
        
        //CONDICIONAMOS SI LAS LISTAS DE ELEMENTOS ESTÁN VACIAS
        //POR SI EL ARCHIVO XML NO CONTIENE ELEMENTOS CLIENTES O PRODUCTOS
        if(!listaC.isEmpty() && !listaP.isEmpty()){
            //VALOR INICIAL DE LA BARRA DE PROGRESO
            this.h1 = new Hilo_BarraProgreso(barraProgreso1);
            
            //CALCULAMOS EL % DE PROGRESO DE CADA INSERCIÓN
            float porcentajeProgreso = 0;
            float incremento = 100f/(listaC.size() + listaP.size());

            try {
                //CONFIGURAMOS EL PREPAREDSTATEMENT CON LA CADENA CORRESPONDIENTE Y AJUSTAMOS PARÁMETROS
                this.preparedInsercion = cn.prepareStatement(this.insercionClientes);

                //RECORREMOS LA LISTA DE CLIENTES, CONFIGURAMOS EL PREPAREDSTATEMENT
                //REALIZAMOS LA INSERCIÓN EN LA BBDD
                //VAMOS ACTUALIZANDO LA BARRA DE PROGRESO EN UN HILO DIFERENTE
                h1.start();

                for(Cliente cliente : listaC){         
                    if (!clienteYaInsertado(cliente.getNif(), cn)) {
                        this.preparedInsercion.setString(1, cliente.getNombre());
                        this.preparedInsercion.setString(2, cliente.getDireccion());
                        this.preparedInsercion.setString(3, cliente.getPoblacion());
                        this.preparedInsercion.setString(4, cliente.getTelefono());
                        this.preparedInsercion.setString(5, cliente.getNif());

                        this.preparedInsercion.executeUpdate();
                        listaCInsertados.add(cliente);
                    }
                    //ACTUALIZAMOS EL PROGRESO DE LA INSERCIÓN
                    porcentajeProgreso = porcentajeProgreso + incremento;
                    h1.setEstado(Math.round(porcentajeProgreso)); 
                }

                //CONFIGURAMOS EL PREPAREDSTATEMENT CON LA CADENA CORRESPONDIENTE Y AJUSTAMOS PARÁMETROS
                this.preparedInsercion = cn.prepareStatement(this.insercionProductos);

                for (Producto producto : listaP) {
                    if (!productoYaInsertado(producto.getId(), cn)) {
                        this.preparedInsercion.setInt(1, producto.getId());
                        this.preparedInsercion.setString(2, producto.getDescripcion());
                        this.preparedInsercion.setInt(3, producto.getStockActual());
                        this.preparedInsercion.setInt(4, producto.getStockMinimo());
                        this.preparedInsercion.setFloat(5, producto.getPvp());

                        this.preparedInsercion.executeUpdate();
                        listaPInsertados.add(producto);
                    }
                    //ACTUALIZAMOS EL PROGRESO DE LA INSERCIÓN
                    porcentajeProgreso = porcentajeProgreso + incremento;
                    h1.setEstado(Math.round(porcentajeProgreso));
                }
                //CERRAMOS EL STATEMENT Y LA CONEXIÓN
                this.preparedInsercion.close();
                cn.close();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        else{//SACAMOS ERROR POR PANTALLA, FORMATO XML
            mostrarPanelError(this.ficheroXML.getName() + " no tiene el formato correcto");
        }
        //AÑADIMOS AL MAPA LOS OBJETOS INSERTADOS
        mapaInserciones.put("clientes", listaCInsertados);
        mapaInserciones.put("productos", listaPInsertados);
            
        return mapaInserciones;
    }

    //MÉTODO PARA COMPROBAR SI EXISTE EL CLIENTE EN LA BBDD
    public boolean clienteYaInsertado(String nif, Connection cn) {
        boolean existe = false;
        try {
            this.preparedBusquedaObjeto = cn.prepareStatement(this.busquedaNif);
            this.preparedBusquedaObjeto.setString(1, nif);
            ResultSet rs = this.preparedBusquedaObjeto.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                existe = true;
            }
            rs.close();
            this.preparedBusquedaObjeto.close();
             
        } catch (SQLException ex) {
            Logger.getLogger(GestorBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;
    }

    //MÉTODO PARA COMPROBAR SI EXISTE EL PRODUCTO EN LA BBDD
    public boolean productoYaInsertado(int id, Connection cn) {
        boolean existe = false;
        try {
            this.preparedBusquedaObjeto = cn.prepareStatement(this.busquedaIdProducto);
            this.preparedBusquedaObjeto.setInt(1, id);
            ResultSet rs = this.preparedBusquedaObjeto.executeQuery();
            rs.next();
            if (rs.getInt(1) > 0) {
                existe = true;
            }
            rs.close();
            this.preparedBusquedaObjeto.close();
            
        } catch (SQLException ex) {
            Logger.getLogger(GestorBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return existe;
    }

    //MÉTODO QUE REALIZA LA INSERCÍON EN LA BBDD DB4O
    //DEVUELVE UN MAPA CON LOS OBJETOS INSERTADOS
    public HashMap<String, ArrayList> insertarDB4O_XML(ArrayList<Cliente> listaC, ArrayList<Producto> listaP) {
        
        HashMap<String, ArrayList> mapaInserciones = new HashMap();
        ArrayList<Cliente> clientesInsertados = new ArrayList();
        ArrayList<Producto> productosInsertados = new ArrayList();
        
        if(!listaC.isEmpty() && !listaP.isEmpty()){
            
            //INICIAMOS HILO PARA LA BARA DE PROGRESO
            this.h1 =  new Hilo_BarraProgreso(barraProgreso1);
            //CALCULAMOS EL % DE PROGRESO DE CADA INSERCIÓN
            float porcentajeProgreso = 0;
            float incremento = 100f/(listaC.size() + listaP.size());

            //ABRIMOS LA BBDD DB4O,COMPROBAMOS QUE NO ESTÉ EL OBJETO Y LO INSERTAMOS
            this.db4oC = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "efecto2000.yap");
            //INICIAMOS EL HILO DE LA BARRA DE PROGRESO
            h1.start();
                     
            for (Cliente cl : listaC) {
                //CREAMOS UN CLIENTE DE EJEMPLO CON EL NIF Y LO BUSCAMOS EN LA BBDD
                Cliente clienteEjemplo = new Cliente(cl.getNif());
                ObjectSet<Cliente> result = this.db4oC.queryByExample(clienteEjemplo);
                if (result.isEmpty()) {
                    this.db4oC.store(cl);
                    clientesInsertados.add(cl);
                }
                //ACTUALIZAMOS EL PROGRESO DE LA INSERCIÓN
                porcentajeProgreso = porcentajeProgreso + incremento;
                h1.setEstado(Math.round(porcentajeProgreso));
            }
            
            for (Producto p : listaP) {
                //CREAMOS UN CLIENTE DE EJEMPLO CON EL NIF Y LO BUSCAMOS EN LA BBDD
                Producto prodEjemplo = new Producto(p.getId());
                ObjectSet<Cliente> result = this.db4oC.queryByExample(prodEjemplo);
                if (result.isEmpty()) {
                    this.db4oC.store(p);
                    productosInsertados.add(p);
                }
                //ACTUALIZAMOS EL PROGRESO DE LA INSERCIÓN
                porcentajeProgreso = porcentajeProgreso + incremento;
                h1.setEstado(Math.round(porcentajeProgreso));
            }
            //CONFIRMAMOS Y CERRAMOS FICHERO
            this.db4oC.commit();
            this.db4oC.close();
        }
        //CONFIGURAMOS MAPA Y LO DEVOLVEMOS
        mapaInserciones.put("clientes", clientesInsertados);
        mapaInserciones.put("productos", productosInsertados);

        return mapaInserciones;
    }
    
    //MÉTODO QUE DEVUELVE UN ARRAYLIST CON TODOS LOS CLIENTES DE LA BBDD
    public ArrayList<Cliente> pedirListaTodosClientes(String tipoBBDD){
        
        ArrayList<Cliente> listaC = new ArrayList<>();
        Connection cn=null;
        try {
             
            switch (tipoBBDD) {
                case "mysql":
                    cn = conexionMysql();
                    break;
                case "sqlite":
                    cn = conexionSQLITE();
                    break;            
            }
            
            this.preparedBusquedaObjeto =  cn.prepareStatement(this.consultaTodosClientes);
            //CREAMOS RESULTSET PARA EL RESULTADO DE LA CONSULTA Y LO VAMOS RECORRIENDO PARA 
            //CREAR LOS OBJETOS CLIENTE Y GUARDARLOS EN LA LISTA A DEVOLVER
            ResultSet rs = this.preparedBusquedaObjeto.executeQuery();
            while(rs.next()){            
                String nombre = rs.getString(1);
                String direccion = rs.getString(2);
                String poblacion = rs.getString(3);
                String tel = rs.getString(4);
                String nif = rs.getString(5);
                
                listaC.add(new Cliente(nombre, direccion, poblacion, tel, nif));
            }  
            rs.close();
            this.preparedBusquedaObjeto.close();
            cn.close();
                 
        } catch (SQLException ex) {
            Logger.getLogger(GestorBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaC;
    }
    
    //MÉTODO QUE DEVUELVE UN ARRAYLIST CON TODOS LOS PRODUCTOS DE LA BBDD
    public ArrayList<Producto> pedirListaTodosProductos(String tipoBBDD){
        
        ArrayList<Producto> listaP = new ArrayList<>();
        Connection cn=null;
        try {
             
            switch (tipoBBDD) {
                case "mysql":
                    cn = conexionMysql();
                    break;
                case "sqlite":
                    cn = conexionSQLITE();
                    break;            
            }          
            this.preparedBusquedaObjeto =  cn.prepareStatement(this.consultaTodosProductos);
            //CREAMOS RESULTSET PARA EL RESULTADO DE LA CONSULTA Y LO VAMOS RECORRIENDO PARA 
            //CREAR LOS OBJETOS PRODUCTO Y GUARDARLOS EN LA LISTA A DEVOLVER
            ResultSet rs = this.preparedBusquedaObjeto.executeQuery();
            while(rs.next()){
                int id = rs.getInt(1);
                String descripcion = rs.getString(2);
                int stockA = rs.getInt(3);
                int stockM = rs.getInt(4);
                float tel = rs.getFloat(5);
           
                listaP.add(new Producto(id, stockA, stockM, descripcion, tel));
            }  
            rs.close();
            this.preparedBusquedaObjeto.close();
            cn.close();
                 
        } catch (SQLException ex) {
            Logger.getLogger(GestorBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaP;
    }
    
    //MÉTODO QUE DEVUELVE UNA LISTA CON TODOS LOS CLIENTES DE LA BBDD DB4O
    public ArrayList<Cliente> pedirListaClientesDB4O(){
        
        ArrayList<Cliente> listaC =  new ArrayList<>();
        //ABRIMOS LA BBDD DB4O
        this.db4oC = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "efecto2000.yap");
        //ESPECIFICAMOS QUE CLASE DE OBJETO QUEREMOS BUSCAR EN LA BBDD
        Class<Cliente> cl = Cliente.class;
        ObjectSet<Cliente> res =  this.db4oC.query(cl);
        
        for(Cliente c: res){
            listaC.add(c);
        }   
        this.db4oC.close();
        return listaC;      
    }
    
    //MÉTODO QUE DEVUELVE UNA LISTA CON TODOS LOS PRODUCTOS DE LA BBDD DB4O
    public ArrayList<Producto> pedirListaProductosDB4O(){
        
        ArrayList<Producto> listaP =  new ArrayList<>();
        //ABRIMOS LA BBDD DB4O
        this.db4oC = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "efecto2000.yap");
        //ESPECIFICAMOS QUE CLASE DE OBJETO QUEREMOS BUSCAR EN LA BBDD
        Class<Producto> pr = Producto.class;
        ObjectSet<Producto> res =  this.db4oC.query(pr);
        
        for(Producto p: res){
            listaP.add(p);
        }   
        this.db4oC.close();
        return listaP;      
    }
    
    //MÉTODO QUE RECUPERA UN CLIENTE EN CONCRETO DE LA BBDD ESPECIFICADA
    //SI EL CLIENTE NO EXITE DEVOLVERÁ NULL
    public Cliente pedirClienteBBDD(String nif, String tipoBBDD){
        
        Cliente cliente = null;
        Connection cn = null;
        
        //CREAMOS CONEXIÓN DEPENDIENDO DEL TIPO SELECCIONADO
        switch (tipoBBDD) {
            case "mysql":
                cn = conexionMysql();
                break;
            case "sqlite":
                cn = conexionSQLITE();
                break;      
        }
 
        try {
            this.preparedBusquedaObjeto = cn.prepareStatement(this.consultaCliente);
            
            //ASIGNAMOS PARÁMETROS AL PREPAREDSTATEMENT
            this.preparedBusquedaObjeto.setString(1, nif);
            //REALIZAMOS CONSULTA Y GUARDAMOS RESULTADO
            ResultSet rs = this.preparedBusquedaObjeto.executeQuery();
            if (rs.next()){
                String nombre = rs.getString(1);
                String direccion = rs.getString(2);
                String poblacion = rs.getString(3);
                String tel = rs.getString(4);
                         
                cliente =  new Cliente(nombre, direccion, poblacion, tel, nif);
            }
            //CERRAMOS OBJETOS DE LA BBDD
            rs.close();
            this.preparedBusquedaObjeto.close();
            cn.close();
                       
        } catch (SQLException ex) {
            Logger.getLogger(GestorBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
          
        return cliente;
    }
    
    //MÉTODO QUE RECUPERA UN PRODUCTO EN CONCRETO DE LA BBDD ESPECIFICADA
    //SI EL PRODUCTO NO EXITE DEVOLVERÁ NULL
    public Producto pedirProductoBBDD(int id, String tipoBBDD){
        
        Producto producto = null;
        Connection cn = null;
        
        //CREAMOS CONEXIÓN DEPENDIENDO DEL TIPO SELECCIONADO
        switch (tipoBBDD) {
            case "mysql":
                cn = conexionMysql();
                break;
            case "sqlite":
                cn = conexionSQLITE();
                break;      
        } 
        try {
            this.preparedBusquedaObjeto = cn.prepareStatement(this.consultaProducto);
            
            //ASIGNAMOS PARÁMETROS AL PREPAREDSTATEMENT
            this.preparedBusquedaObjeto.setInt(1, id);
            //REALIZAMOS CONSULTA Y GUARDAMOS RESULTADO
            ResultSet rs = this.preparedBusquedaObjeto.executeQuery();
            if (rs.next()){
                String descripcion = rs.getString(1);
                int stockA = rs.getInt(2);
                int stockM = rs.getInt(3);
                float tel = rs.getFloat(4);
                
                producto =  new Producto(id, stockA, stockM, descripcion, tel);
            }
            //CERRAMOS OBJETOS DE LA BBDD
            rs.close();
            this.preparedBusquedaObjeto.close();
            cn.close();
                       
        } catch (SQLException ex) {
            Logger.getLogger(GestorBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
               
       return producto;
    }
    
    //MÉTODO QUE DEVUELVE UN CLIENTE DE LA BBDD DB40
    public Cliente pedirCLienteDB4O(String nif){
        
        Cliente cliente = null;
        
        //ABRIMOS LA BBDD DB4O,COMPROBAMOS QUE NO ESTÉ EL OBJETO Y LO INSERTAMOS
        this.db4oC = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "efecto2000.yap");
        //REALIZAMOS CONSULATA COMPARANDO CON UN CLIENTE CON EL ID PASADO COMO ARGUMENTO
        Cliente clObjetivo = new Cliente(nif);
        ObjectSet<Cliente> result = this.db4oC.queryByExample(clObjetivo);
        
        if(!result.isEmpty())
            cliente = result.next();
        
        //CERRAMOS BBDD
        this.db4oC.close();
       
        return cliente;
    }
    
    //MÉTODO QUE DEVUELVE UN PRODUCTO DE LA BBDD DB40
    public Producto pedirProductoDB4O(int id){
        
        Producto producto = null;
        
        //ABRIMOS LA BBDD DB4O,COMPROBAMOS QUE NO ESTÉ EL OBJETO Y LO INSERTAMOS
        this.db4oC = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "efecto2000.yap");
        //REALIZAMOS CONSULATA COMPARANDO CON UN CLIENTE CON EL ID PASADO COMO ARGUMENTO
        Producto pObjetivo = new Producto(id);
        ObjectSet<Producto> result = this.db4oC.queryByExample(pObjetivo);
        
        if(!result.isEmpty())
            producto = result.next();
        
        //CERRAMOS BBDD
        this.db4oC.close();
       
        return producto;
    }
    
    //MÉTODO QUE INGRESA O MODIFICA UN CLIENTE DEPENDIENDO SI EXISTE O NO EN LA BASE DE DATOS
    public int insertarClienteBBDD(Cliente cliente,String tipoBBDD){
        
        Connection cn = null;
        
        //CREAMOS CONEXIÓN DEPENDIENDO DEL TIPO SELECCIONADO
        switch (tipoBBDD) {
            case "mysql":
                cn = conexionMysql();
                break;
            case "sqlite":
                cn = conexionSQLITE();
                break;      
        } 
        
        try {
            //COMPROBAMOS SI EL CLIENTE EXISTE YA O NO EN LA BBDD
            //SI EXISTE CARGAMOS LA CONSULTA DE UPDATE EN EL PREPAREDSTATEMENT
            if(clienteYaInsertado(cliente.getNif(), cn)){
                this.preparedBusquedaObjeto =  cn.prepareStatement(this.updateTablaClientes);
                //CONFIGURAMOS PARÁMETRO
                this.preparedBusquedaObjeto.setString(6, cliente.getNif());                          
            }
            else              
                //SI NO EXISTE CARGAMOS LA CONSULTA DE INSERCIÓN EN EL STATEMENT
                this.preparedBusquedaObjeto =  cn.prepareStatement(this.insercionClientes);
            
            this.preparedBusquedaObjeto.setString(1, cliente.getNombre());
            this.preparedBusquedaObjeto.setString(2, cliente.getDireccion());
            this.preparedBusquedaObjeto.setString(3, cliente.getPoblacion());
            this.preparedBusquedaObjeto.setString(4, cliente.getTelefono());
            this.preparedBusquedaObjeto.setString(5, cliente.getNif());
            
            //EJECUTAMOS CONSULTA
            int resp= this.preparedBusquedaObjeto.executeUpdate();
            this.preparedBusquedaObjeto.close();
            cn.close();
            return resp;
            
        } 
        catch (SQLException ex) {
                Logger.getLogger(GestorBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }
    
    //MÉTODO QUE INSERTA-ACTUALIZA UN PRODUCTO EN LA BBDD SELECCIONADA
    public int insertarProductoBBDD(Producto producto, String tipoBBDD){
        
        Connection cn = null;
        
        //CREAMOS CONEXIÓN DEPENDIENDO DEL TIPO SELECCIONADO
        switch (tipoBBDD) {
            case "mysql":
                cn = conexionMysql();
                break;
            case "sqlite":
                cn = conexionSQLITE();
                break;      
        } 
        
        try {
            //COMPROBAMOS SI EL PRODUCTO EXISTE YA O NO EN LA BBDD
            //SI EXISTE CARGAMOS LA CONSULTA DE UPDATE EN EL PREPAREDSTATEMENT
            if(productoYaInsertado(producto.getId(), cn)){
                this.preparedBusquedaObjeto =  cn.prepareStatement(this.updateTablaProducto);
                //CONFIGURAMOS PARÁMETRO 
                this.preparedBusquedaObjeto.setInt(6, producto.getId());                          
            }
            else              
                //SI NO EXISTE CARGAMOS LA CONSULTA DE INSERCIÓN EN EL STATEMENT
                this.preparedBusquedaObjeto =  cn.prepareStatement(this.insercionProductos);
            
            this.preparedBusquedaObjeto.setInt(1, producto.getId());
            this.preparedBusquedaObjeto.setString(2, producto.getDescripcion());
            this.preparedBusquedaObjeto.setInt(3, producto.getStockActual());
            this.preparedBusquedaObjeto.setInt(4, producto.getStockMinimo());
            this.preparedBusquedaObjeto.setFloat(5, producto.getPvp());
            
            //EJECUTAMOS CONSULTA
            int resp= this.preparedBusquedaObjeto.executeUpdate();
            this.preparedBusquedaObjeto.close();
            cn.close();
            return resp;
            
        } 
        catch (SQLException ex) {
                Logger.getLogger(GestorBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return -1;
    }
    
    //MÉTODO QUE DEVUELVE UN ENTERO CON EL SIGUIENTE ID DISPONIBLE DE LA TABLA VENTAS EN LA BBDD 
    public int pedirSiguienteIdVentas(String tipoBBDD){
        
        int id = -1;
        Connection cn = crearConexion(tipoBBDD);
        
        try {
            //COMPROBAMOS QUE LA TABLA VENTAS NO ESTÁ VACIA
            preparedBusquedaObjeto = cn.prepareStatement(this.consultaVentasVacia);
            ResultSet rs = preparedBusquedaObjeto.executeQuery();
            rs.next();
            
            //SI NO ESTÁ VACIA PEDIMOS EL SIGUIENTE ID DISPONIBLE
            if(rs.getInt(1)!=0){
                preparedBusquedaObjeto = cn.prepareStatement(consultaSiguienteIdVentas);
                rs = preparedBusquedaObjeto.executeQuery();
                rs.next();
                
                id = rs.getInt(1) + 1;
            }
            else
                id = 1; //SI ESTÁ VACIA EL SIGUIENTE ID SERÁ EL 1
            
            rs.close();
            preparedBusquedaObjeto.close();
            cn.close();
              
        } catch (SQLException ex) {
            Logger.getLogger(GestorBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
             
        return id; 
    }
    
    //MÉTODO QUE DEVUELVE UN ENTERO CON EL ID SIGUIENTE DE LA TABLA VENTAS EN LA BBDD DB4O
    public int pedirSiguienteIdVentaDB4O(){
        
        int id = 0;       
        //ABRIMOS LA BBDD DB4O
        this.db4oC = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "efecto2000.yap");
        //ESPECIFICAMOS QUE CLASE DE OBJETO QUEREMOS BUSCAR EN LA BBDD
        Class<Venta> vt = Venta.class;
        ObjectSet<Venta> res =  this.db4oC.query(vt);
        
        //SI EL RESULTSET ESTÁ VACIO DEVOLVEMOS 1 Y SI NO  BUSCAMOS EL MAX ID
        if(!res.isEmpty()){
            for(Venta venta: res){
                if(venta.getId_venta() > id)
                    id = venta.getId_venta();
            }   
            
            id = id++;
        }
        else
            id = 1;
       
        this.db4oC.close();    
        return id;
    }
    
    //MÉTODO QUE INSERTA UN OBJETO EN EL FICHERO XML
    public boolean insertarClienteEnXML(Cliente cliente){
        
        try {
            //ABRIMOS EL FICHERO XML Y CONFECCIONAMOS ARRAYLIST CON CLIENTES Y PRODUCTOS
            SAXBuilder builder = new SAXBuilder();
            this.ficheroXML = new File("efecto2000.xml");
            Document document = builder.build(ficheroXML);
            Element root = document.getRootElement();

            Element elemClientes = root.getChild("CLIENTES");

            ArrayList<Cliente> listaC = crearListaClientes(elemClientes.getChildren("CLIENTE"));

            //SI EL CLIENTE NO ESTÁ EN LA LISTA LO AÑADIMOS 
            if (!listaC.contains(cliente)) {

                Element nuevoElemCliente = new Element("CLIENTE");

                Element elemNombre = new Element("NOMBRE");
                elemNombre.setText(cliente.getNombre());
                nuevoElemCliente.addContent(elemNombre);

                Element elemDireccion = new Element("DIRECCION");
                elemDireccion.setText(cliente.getDireccion());
                nuevoElemCliente.addContent(elemDireccion);

                Element elemPoblacion = new Element("POBLACION");
                elemPoblacion.setText(cliente.getPoblacion());
                nuevoElemCliente.addContent(elemPoblacion);

                Element elemTelefono = new Element("TELEFONO");
                elemTelefono.setText(cliente.getTelefono());
                nuevoElemCliente.addContent(elemTelefono);

                Element elemNif = new Element("NIF");
                elemNif.setText(cliente.getNif());
                nuevoElemCliente.addContent(elemNif);

                elemClientes.addContent(nuevoElemCliente);

                XMLOutputter xmlOutput = new XMLOutputter();
                xmlOutput.setFormat(Format.getPrettyFormat());
                xmlOutput.output(root, new FileWriter(ficheroXML));

                return true;
            }         
        } catch (JDOMException | IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
    
    //MÉTODO PARA INSERTAR UN PRODUCTO EN EL FICHERO XML
    public boolean insertarProductoEnXML(Producto producto){
        
        try {
            //ABRIMOS EL FICHERO XML Y CONFECCIONAMOS ARRAYLIST CON CLIENTES Y PRODUCTOS
            SAXBuilder builder = new SAXBuilder();
            this.ficheroXML = new File("efecto2000.xml");
            Document document = builder.build(ficheroXML);
            Element root = document.getRootElement();
            
            Element elemProductos = root.getChild("PRODUCTOS");
            ArrayList<Producto> listaP = crearListaProductos(elemProductos.getChildren("PRODUCTO"));
            
            //COMPROBAMOS SI EL PRODUCTO YA ESTÁ EN EL FICHERO XML
            if (!listaP.contains(producto)) {
                
                Element nuevoElemProducto = new Element("PRODUCTO");
                Element elemID = new Element("ID").setText(String.valueOf(producto.getId()));
                nuevoElemProducto.addContent(elemID);
                
                Element elemDesc = new Element("DESCRIPCION").setText(producto.getDescripcion());
                nuevoElemProducto.addContent(elemDesc);
                
                Element elemStockA = new Element("STOCKACTUAL").setText(String.valueOf(producto.getStockActual()));
                nuevoElemProducto.addContent(elemStockA);
                
                Element elemStockI = new Element("STOCKMINIMO").setText(String.valueOf(producto.getStockMinimo()));
                nuevoElemProducto.addContent(elemStockI);
                
                Element elemPVP = new Element("PVP").setText(String.valueOf(producto.getPvp()));
                nuevoElemProducto.addContent(elemPVP);
                
                elemProductos.addContent(nuevoElemProducto);
                XMLOutputter xmlOutput = new XMLOutputter();
                xmlOutput.setFormat(Format.getPrettyFormat());
                xmlOutput.output(root, new FileWriter(ficheroXML));
                
                return true;
                
            }       
        } catch (JDOMException | IOException ex) {
            Logger.getLogger(GestorBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
        
    
    //MÉTODO QUE INSERTA O ACTUALIZA UN CLIENTE SI ESTE ESTÁ O NO EN LA BBDD DB4O
    public boolean insertarClienteDB4O(Cliente cliente){
        
        boolean existeCLiente = pedirListaClientesDB4O().contains(cliente);
        //ABRIMOS LA BBDD DB4O,COMPROBAMOS QUE NO ESTÉ EL CLIENTE Y LO INSERTAMOS
        this.db4oC = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "efecto2000.yap");
            
        //COMPROBAMOS SI EL CLIENTE ESTÁ EN LA BBDD
        if(existeCLiente){
            
            //BORRAMOS CLIENTE E INSERTAMOS EL MODIFICADO
            this.db4oC.delete(cliente);
            this.db4oC.store(cliente);
            
            //CONFIRMAMOS CAMBIOS Y CERRAMOS BBDD
            this.db4oC.commit();
            this.db4oC.close();
            return false;
        }
        else
            this.db4oC.store(cliente);
        
        this.db4oC.commit();    
        this.db4oC.close();
        return true;
    }
    
    //MÉTODO QUE INSERTA O ACTUALIZA UN CLIENTE SI ESTE ESTÁ O NO EN LA BBDD DB4O
    public boolean insertarProductoDB4O(Producto producto){
        
       
        //ABRIMOS LA BBDD DB4O,COMPROBAMOS QUE NO ESTÉ EL CLIENTE Y LO INSERTAMOS
        this.db4oC = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "efecto2000.yap");
            
        ObjectSet<Cliente> result = this.db4oC.queryByExample(new Producto(producto.getId()));
        
        //COMPROBAMOS SI EL CLIENTE ESTÁ EN LA BBDD
        if(result.isEmpty()){
            //SI NO EXISTE LO INSERTAMOS
           this.db4oC.store(producto); 
           
           this.db4oC.commit();    
           this.db4oC.close();
           return true;
        }
        else{
            //SI EXISTE BORRAMOS E INSERTAMOS
            this.db4oC.delete(result.next());
            this.db4oC.store(producto);
        }
         
        this.db4oC.commit();    
        this.db4oC.close();
        return false;
    }
    
    //MÉTODO QUE DEVUELVE UN ENTERO CON EL STOCK ACTUAL DEL PRODUCTO
    public int pedirStockActualProducto(String tipoBBDD, int idProducto){
        
        Connection cn = null;
        int stockProducto = 0;
        
        //CREAMOS CONEXIÓN DEPENDIENDO DEL TIPO SELECCIONADO
        switch (tipoBBDD) {
            case "mysql":
                cn = conexionMysql();
                break;
            case "sqlite":
                cn = conexionSQLITE();
                break;      
        } 
        
        try {        
            preparedBusquedaObjeto = cn.prepareStatement(consultaStockActualProducto);
            preparedBusquedaObjeto.setInt(1, idProducto);
            ResultSet rs = preparedBusquedaObjeto.executeQuery();
            
            if(rs.next())
                stockProducto = rs.getInt(1);
            
            rs.close();
            preparedBusquedaObjeto.close();
            cn.close();
                  
        } 
        catch (SQLException ex) {
                Logger.getLogger(GestorBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return stockProducto;
    }
    
    
    //MÉTODO QUE DEVUELVE EL STOCK ACTUAL DE UN PRODUCTO EN LA BBDD DB40
    public int pedirStockActualProductoDB4O(int idProducto){
        
        int stock = 0;
        //ABRIMOS LA BBDD DB4O
        this.db4oC = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "efecto2000.yap");
        
        //COMPROBAMOS QU EL OBJETO ESTÉ EN LA BBDD
        ObjectSet<Producto> result = this.db4oC.queryByExample(new Producto(idProducto));
        
        //DEVOLVEMOS EL STOCK ACTUAL DEL PRODUCTO
        if(!result.isEmpty()){
            stock = result.next().getStockActual();
        }
            
        this.db4oC.close();
        return stock;
    }
    
    //MÉTODO QUE INSERTA UNA VENTA CON SUS CORRESPONDIENTES LINEAS EN LA BBDD MYSQL
    //PARA ELLO UTILIZARÁ 2 FUNCIONES ALMACENADAS EN MYSQL Y 1 FUNCIÓN QUE ACTUALIZARÁ EL STOCK
    //DEVOLVIENDO 0 - 1 DEPENDIENDO SI SE HA ALCANZADO EL STOCK MINIMO
    public ArrayList<Producto> insertarVentaMysql (Cliente cliente, HashMap<Producto, Integer> mapaVenta, float totalVenta){
        
        //CREAMOS ARRAYLIST PARA IR GUARDANDO LOS PRODUCTOS QUE HAN REBAJADO EL STOCKMINIMO
        //PARA MOSTRARLOS AL TERMINAR EL MÉTODO
        ArrayList<Producto> listaAlertaStock = new ArrayList<>();
        
        try {
            //EL PRIMER PASO ES CREAR LA CONEXIÓN Y REGISTRAR LA VENTA EN LA BBDD
            Connection cn = conexionMysql();
       
            //PARA INSERTAR LA VENTA UTILIZAMOS EL PROCEDIMIENTO ALMACENADO 'procedimientoInsertarVenta'
            //UTILIZAMOS LA CLASE CALLABLE STATEMENT, INSTANCIAMOS TAMBIÉN EL PREPARED STATEMENT PARA LA FUNCIÓN
            //CAMBIO STOCK
            CallableStatement callP = cn.prepareCall(this.llamadaProcedimientoInsertarVenta);
            preparedInsercion = cn.prepareStatement(this.funcionCambioStock);
            
            //CONFIGURAMOS LOS PARÁMETROS
            callP.setString(1, cliente.getNif());
            callP.setFloat(2, totalVenta);
            
            //EJECUTAMOS EL PROCEDIMIENTO, NO DEVUELVE RESULTADO
            callP.execute();
                    
            //UNA VEZ INSERTADA LA VENTA PROCEDEMOS CON LAS LINEAS
            //INSTANCIAMOS EL CALLABLE STATEMENT
            callP = cn.prepareCall(this.llamadaProcedimientoInsertarLinea);
            
            //PEDIMOS EL ID DE LA VENTA QUE ACABAMOS DE INSERTAR
            //RESTAMOS 1 PORQUE EL METODO NOS DEVUELVE EL SIGUIENTE DISPONIBLE,
            //NO EL ÚLTIMO INSERTADO
            int idV = pedirSiguienteIdVentas("mysql") - 1;
            
            //RECORREMOS EL MAPA VENTA
            Iterator<Producto> it = mapaVenta.keySet().iterator();
            int idP;
            int cantidad;
            
            while (it.hasNext()) {
                Producto producto = it.next();
                idP = producto.getId();
                cantidad = mapaVenta.get(producto);
                
                //CONFIGURAMOS PARÁMETROS DEL STATEMENT
                callP.setInt(1, idV);
                callP.setInt(2, idP);
                callP.setInt(3, cantidad);
                
                //INSERTAMOS LA LINEA
                callP.execute();
                
                //UNA VEZ INSERTADA LA LINEA ACTUALIZAMOS EL STOCK DEL PRODUCTO
                //CON LA FUNCIÓN ALMACENADA 'cambioStock(idProducto in, unidadesVendidas int)'
                preparedInsercion.setInt(1, idP);
                preparedInsercion.setInt(2, cantidad);
                
                //EJECUTAMOS EL PREPARED STATEMENT Y ACTUALIZAMOS EL STOCK
                //SI DEVUELVE 0 NO SE HA REBAJADO EL STOCKMINIMO, SI DEVUELVE 1 SI Y
                //METEMOS ESE OBJETO EN EL MAPA PARA MOSTRAR MÁS ADELANTE LA ALERTA
                ResultSet rs = preparedInsercion.executeQuery();
                rs.next();
                
                if(rs.getInt(1) == 1)
                    listaAlertaStock.add(producto);
                
                //CERRAMOS RESULTSET 
                rs.close();    
            }
        
            //CERRAMOS STATEMENTS Y CONEXIÓN
            preparedInsercion.close();
            callP.close();
            cn.close();    
            
        } catch (SQLException ex) {
            mostrarPanelError(ex.getLocalizedMessage());
        }
        
        return listaAlertaStock;
    }
    
    //MÉTODO QUE INSERTA UNA VENTA Y SUS RESPECTIVAS LINEAS EN LA BBDD SQLITE
    //DEVUELVE UNA LISTA CON LOS PRODUCTOS QUE HAN REBAJADO EL STOCK MINIMO
    
    public ArrayList<Producto> insertarVentaSQLite(Cliente cliente, HashMap<Producto, Integer> mapaVenta, float totalVenta){
        
        ArrayList<Producto> listaP =  new ArrayList<>();
        
        try {
                       
            //INSTANCIAMOS PREPARED STATEMENT
            Connection cn = conexionSQLITE();
            preparedInsercion =  cn.prepareStatement(this.insercionVenta);
            
            //RESCATAMOS TIMESTAMP
            Timestamp timeS = new Timestamp( System.currentTimeMillis());
                        
            //CONFIGURAMOS PARÁMETROS E INSERTAMOS
            preparedInsercion.setTimestamp(1, timeS);
            preparedInsercion.setString(2, cliente.getNif());
            preparedInsercion.setFloat(3, totalVenta);
            
            //INSERTAMOS LA VENTA
            preparedInsercion.executeUpdate();
            
            //RECORREMOS EL MAPA VENTA
            Iterator<Producto> it = mapaVenta.keySet().iterator();
            int idP;
            int cantidad;
            
            //PEDIMOS EL ID DE LA VENTA QUE ACABAMOS DE INSERTAR
            //RESTAMOS 1 PORQUE EL METODO NOS DEVUELVE EL SIGUIENTE DISPONIBLE,
            //NO EL ÚLTIMO INSERTADO
            int idV = pedirSiguienteIdVentas("sqlite") - 1;
            
            while (it.hasNext()) {
                Producto producto = it.next();
                idP = producto.getId();
                cantidad = mapaVenta.get(producto);
                
                //INSTANCIAMOS PREPARED STATEMENT
                preparedInsercion = cn.prepareStatement(this.insercionLinea);
                
                //CONFIGURAMOS PRÁMETROS
                preparedInsercion.setInt(1, idV);
                preparedInsercion.setInt(2, idP);
                preparedInsercion.setInt(3, cantidad);
                
                preparedInsercion.executeUpdate();
                
                //INSTANCIAMOS PREPARED STATEMENT PARA ACTUALIZAR EL STOCK
                preparedInsercion = cn.prepareStatement(this.cambioStockProducto);
                
                //CONFIGURAMOS PARÁMETROS
                //NUEVO STOCK
                int nuevoStock = producto.getStockActual() - cantidad;         
                preparedInsercion.setInt(1, nuevoStock);
                preparedInsercion.setInt(2, idP);
             
                preparedInsercion.executeUpdate();
                
                //COMPROBAMOS EL STOCK MINIMO
                preparedInsercion = cn.prepareStatement(this.comprobarStockMinimo);
                preparedInsercion.setInt(1, idP);
                
                //SI EL RESULTADO DE LA CONSULTA ES MENOR QUE 0 AÑADIMOS EL OBJETO A LA LISTA
                ResultSet rs = preparedInsercion.executeQuery();
                rs.next();
                if(rs.getInt(1) < 0)
                    listaP.add(producto);                 
            }
            
            //CERRAMOS STATEMENT Y CONEXION
            preparedInsercion.close();
            cn.close();
            
                 
            
        } catch (SQLException ex) {
            Logger.getLogger(GestorBBDD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return listaP;
    }
    
    
    
    
    public void setFicheroXML(File ficheroXML) {
        this.ficheroXML = ficheroXML;
    }
    
    //MÉTODO QUE RESETEA LA BARRA
    public void resetearBarraProgreso(){
        if(h1 == null)
            h1 = new Hilo_BarraProgreso(barraProgreso1);
        
        h1.resetearBarra();
    }
    
    //MÉTODO PARA MOSTRAR INFORMACIÓN EN LA PANTALLA
    public void mostrarPanelError(String msg) {
        JOptionPane.showMessageDialog(null, msg, "ERROR", JOptionPane.ERROR_MESSAGE);
    }
}
