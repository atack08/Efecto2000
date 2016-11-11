
package GRAFICAS;

import BEANS.Cliente;
import BEANS.Producto;
import DAOS.GestorBBDD;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;


/**
 *
 * @author atack08
 */
public class Principal extends javax.swing.JFrame {
    
    //VARIABLE QUE DETERMINA EL SISTEMA OPERATIVO DONDE SE EJECUTA LA APLICACIÓN
    private final String SO = System.getProperty("os.name");
    private int puerto;
    //VARIABLE QUE DETERMINA CON QUE TIPO DE BBDD TRABAJARÁ LA INTERFACE
    private String tipoBBDD;
    //OBJETO GESTOR DE LAS BBDD
    private GestorBBDD gestor1;
    //MAPA QUE CONTIENE LAS INSERCIONES REALIZADAS EN CADA OPERACIÓN
    private HashMap <String,ArrayList> mapaInserciones;
    //MODELOS PARA LAS LISTAS DE INSERCIONES
    private DefaultListModel<String> modeloListaInsercionCliente, modeloListaInsercionProducto, modeloListaInsercionVenta,
            modelolistaObjetos;
    //MODELOS PARA LOS COMBOBOX
    private DefaultComboBoxModel<Cliente> modeloComboClientes;
    private DefaultComboBoxModel<Producto> modeloComboProductos;
    private DefaultComboBoxModel<Producto> modeloComboProdElegidos;
    //MAPA PARA LA VENTA
    private HashMap<Producto, Integer> mapaVenta;
  
    public Principal() {
        initComponents();
        //LLAMAMOS AL MÉTODO QUE  CAMBIA EL PUERTO SEGÚN EL SO
        configurarPuertoSO();
        
       //DAMOS VALOR INICIAL AL TIPO DE BBDD CON EL
       //TIPO SELECCIONADO INICIALMENTE EN EL COMBO
       this.tipoBBDD = comboTipoBBDD.getSelectedItem().toString().toLowerCase();
       
       //INSTANCIAMOS EL GESTOR BBDD
       this.gestor1 = new GestorBBDD("efecto2000", "localhost:"+String.valueOf(puerto), "root", "root",barraProgreso1);
       
       //INSTANCIAMOS MODELO DE LA LISTA DE OBJETOS
       this.modelolistaObjetos =  new DefaultListModel<>();
       
       //INSTANCIAMOS EL MODELO PARA LOS PRODUCTOS ELEGIDOS
       // Y LO ASIGNAMOS AL COMBO
       this.modeloComboProdElegidos = new DefaultComboBoxModel<>();
       this.comboProductosSeleccionados.setModel(modeloComboProdElegidos);
       
       
       //INICIAMOS LOS COMBOS DE LA PESTAÑA VENTA
       actualizarComboClientes();
       actualizarComboProductos();
       
       //ACTUALIZAMOS EL SIGUIENTE ID DE LA PESTAÑA VENTAS
       actualizarIdVenta();
       //ACTUALIZAMOS LOS CAMPOS DE TEXTO DE LA PESTAÑA VENTAS
       actualizarClienteVentas();
             
       //INSTANCIAMOS EL MAPA PARA LA VENTA
       mapaVenta = new HashMap<>();
               
       //ESCUCHADOR PARA EL SLIDER DE LA PESTAÑA VENTAS
       this.sliderVentas.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                //CADA VEZ QUE CAMBIA EL VALOR DEL SLIDER LO REFLEJAMOS EN EL CAMPO DE TEXTO
               textCantidadVenta.setText(String.valueOf(sliderVentas.getValue()));
            }
        });
    }
    
    //MÉTOD QUE ASIGNA UN VALOR AL PUERTO SEGÚN EL SISTEMA OPERATIVO
    public void configurarPuertoSO(){
            
        if(SO.contains("Mac") || SO.contains("OS X"))
            this.puerto = 8889;
        else
            this.puerto = 3306; 
    }
    
    //MÉTODO QUE ACTUALIZA EL MODELO DEL COMBO PARA LOS PRODUCTOS 
    //QUE VAMOS AÑADIENDO EN UNA MISMA VENTA
    public void actualizarProductosElegidos(){        
        //RESCATAMOS PRODUCTOS DEL COMBO DE PRODUCTOS
        Producto producto = (Producto)modeloComboProductos.getSelectedItem();
        //AÑADIMOS AL MODELO DE PRODUCTOS SELECCIONADOS Y AL MAPA DE LA VENTA
        //COMPROBAMOS QUE EL PRODUCTO YA NO ESTÉ EN EL MODELO
        //METEMOS TODOS LOS PRODUCTOS DEL MODELO EN UNA LISTA
        ArrayList<Producto> listaP = new ArrayList<>();
        for(int i=0; i < modeloComboProdElegidos.getSize(); i++){
            listaP.add(modeloComboProdElegidos.getElementAt(i));
        }
        
        if(!listaP.contains(producto))
            modeloComboProdElegidos.addElement(producto);     
        
        mapaVenta.put(producto, sliderVentas.getValue());
        
    }
    
    //MÉTODO QUE ELIMINA DEL MODELO DE PRODUCTOS SELECCIONADOS UN PRODUCTO EN CUESTIÓN
    public void eliminarProductoSeleccionado(){
        
        if(modeloComboProdElegidos.getSize()>0){
            Producto producto = (Producto) modeloComboProdElegidos.getSelectedItem();
            //LO BORRAMOS DEL MODELO
             modeloComboProdElegidos.removeElement(producto);
            //LO BORRAMOS DEL MAPA DE LA VENTA
            mapaVenta.remove(producto);
        }
    }
    
    
    
    //MÉTODOS PARA MOSTRAR LOS DIFERENTES MENSAJES AL USUARIO
    public void mostrarPanelError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "ERROR", 0);
    }

    public void mostrarPanelInfo(String msg) {
        JOptionPane.showMessageDialog(this, msg, "INFO", 1);
    }

    private void cerrar() {
        dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jDialog1 = new javax.swing.JDialog();
        jScrollPane5 = new javax.swing.JScrollPane();
        textConfVenta = new javax.swing.JTextArea();
        jButton2 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        comboTipoBBDD = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        botonSeleccionFile = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        textNombreCLiente = new javax.swing.JTextField();
        textDirCliente = new javax.swing.JTextField();
        textPobCliente = new javax.swing.JTextField();
        textTelCliente = new javax.swing.JTextField();
        textNifCliente = new javax.swing.JTextField();
        botonInCliente = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        textIdProducto = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        textDesProducto = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        textStockAProducto = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        textStockMProducto = new javax.swing.JTextField();
        botonInProducto = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        textPvpProducto = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        textVentaCliente = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        sliderVentas = new javax.swing.JSlider();
        textCantidadVenta = new javax.swing.JTextField();
        comboBoxClientes = new javax.swing.JComboBox<>();
        comboBoxProductos = new javax.swing.JComboBox<>();
        jLabel16 = new javax.swing.JLabel();
        textIdVenta = new javax.swing.JTextField();
        jButton5 = new javax.swing.JButton();
        botonProducto = new javax.swing.JButton();
        comboProductosSeleccionados = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        radiobbdd = new javax.swing.JRadioButton();
        radioXML = new javax.swing.JRadioButton();
        jPanel7 = new javax.swing.JPanel();
        radioClientes = new javax.swing.JRadioButton();
        radioProductos = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        listaObjetosBBDD = new javax.swing.JList<>();
        jPanel8 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        listaProductosInsertados = new javax.swing.JList<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        listaVentasInsertadas = new javax.swing.JList<>();
        jScrollPane4 = new javax.swing.JScrollPane();
        listaClientesInsertados = new javax.swing.JList<>();
        barraProgreso1 = new javax.swing.JProgressBar();
        botonCargarXML = new javax.swing.JButton();
        labelFichero = new javax.swing.JLabel();

        jDialog1.setTitle("Confirmación de venta");
        jDialog1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jDialog1.setIconImage(null);
        jDialog1.setLocation(new java.awt.Point(433, 184));
        jDialog1.setMinimumSize(new java.awt.Dimension(500, 400));
        jDialog1.setModalityType(java.awt.Dialog.ModalityType.APPLICATION_MODAL);
        jDialog1.setResizable(false);

        textConfVenta.setColumns(20);
        textConfVenta.setRows(5);
        jScrollPane5.setViewportView(textConfVenta);

        jButton2.setText("Confirmar venta");

        javax.swing.GroupLayout jDialog1Layout = new javax.swing.GroupLayout(jDialog1.getContentPane());
        jDialog1.getContentPane().setLayout(jDialog1Layout);
        jDialog1Layout.setHorizontalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5)
                .addContainerGap())
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addGap(141, 141, 141)
                .addComponent(jButton2)
                .addContainerGap(148, Short.MAX_VALUE))
        );
        jDialog1Layout.setVerticalGroup(
            jDialog1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jDialog1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 245, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(1366, 768));

        jPanel1.setPreferredSize(new java.awt.Dimension(1366, 722));

        jLabel1.setText("Seleccione BBDD");

        comboTipoBBDD.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "MYSQL", "SQLITE", "DB4O" }));
        comboTipoBBDD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboTipoBBDDActionPerformed(evt);
            }
        });

        jLabel2.setText("Fichero XML:");

        botonSeleccionFile.setText("Seleccionar Fichero XML");
        botonSeleccionFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonSeleccionFileActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Inserción individual"));

        jLabel4.setText("Nombre: ");

        jLabel5.setText("Dirección:");

        jLabel6.setText("Población: ");

        jLabel7.setText("Teléfono: ");

        jLabel8.setText("NIF:");

        textNombreCLiente.setForeground(new java.awt.Color(51, 102, 255));
        textNombreCLiente.setToolTipText("Nombre completo max 50 caracteres");

        textDirCliente.setForeground(new java.awt.Color(51, 102, 255));
        textDirCliente.setToolTipText("Introduce tu dirección, maximo 50 caracteres");

        textPobCliente.setForeground(new java.awt.Color(51, 102, 255));
        textPobCliente.setToolTipText("Introduce tu población, máximo 50 caracteres");

        textTelCliente.setForeground(new java.awt.Color(51, 102, 255));
        textTelCliente.setToolTipText("Introduce tu teléfono, máximo 10 caracteres");

        textNifCliente.setForeground(new java.awt.Color(51, 102, 255));
        textNifCliente.setToolTipText("DNI+LETRA: 47280569-M");

        botonInCliente.setText("INSERTAR O MODIFICAR");
        botonInCliente.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonInClienteActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(57, 57, 57)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textNifCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textTelCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textPobCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textDirCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(textNombreCLiente, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(botonInCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textNombreCLiente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textDirCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textPobCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textTelCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textNifCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(botonInCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Cliente", jPanel4);

        jLabel9.setText("Id:");

        textIdProducto.setForeground(new java.awt.Color(51, 102, 255));
        textIdProducto.setToolTipText("Identificador del producto, 111111");

        jLabel10.setText("Descripción:");

        textDesProducto.setForeground(new java.awt.Color(51, 102, 255));
        textDesProducto.setToolTipText("Descripción del producto, max 50 caracteres");

        jLabel11.setText("Stock Actual:");

        textStockAProducto.setForeground(new java.awt.Color(51, 102, 255));
        textStockAProducto.setToolTipText("Introduce el stock actual del producto");

        jLabel12.setText("Stock Mínimo:");

        textStockMProducto.setForeground(new java.awt.Color(51, 102, 255));
        textStockMProducto.setToolTipText("Introduce el stock mínimo del producto");

        botonInProducto.setText("INSERTAR O MODIFICAR");
        botonInProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonInProductoActionPerformed(evt);
            }
        });

        jLabel17.setText("Precio:");

        textPvpProducto.setForeground(new java.awt.Color(51, 102, 255));
        textPvpProducto.setToolTipText("Introduce el stock mínimo del producto");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(60, 60, 60)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(textDesProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(38, 38, 38)
                        .addComponent(textIdProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(botonInProducto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(textStockAProducto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textStockMProducto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textPvpProducto, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(70, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textIdProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textDesProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textStockAProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textStockMProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textPvpProducto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(botonInProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(25, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Producto", jPanel5);

        jLabel13.setText("Cliente:");

        textVentaCliente.setToolTipText("Introduce el NIF del cliente");

        jLabel14.setText("Producto:");

        jLabel15.setText("Cantidad:");

        sliderVentas.setMaximum(20);
        sliderVentas.setValue(0);

        textCantidadVenta.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        textCantidadVenta.setForeground(new java.awt.Color(51, 153, 255));
        textCantidadVenta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textCantidadVenta.setText("0");
        textCantidadVenta.setToolTipText("Selecciona o Introduce la cantidad de producto en la venta");

        comboBoxClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                comboBoxClientesActionPerformed(evt);
            }
        });

        jLabel16.setText("Id Venta:");

        textIdVenta.setEditable(false);
        textIdVenta.setFont(new java.awt.Font("Lucida Grande", 1, 13)); // NOI18N
        textIdVenta.setForeground(new java.awt.Color(51, 153, 255));
        textIdVenta.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        textIdVenta.setToolTipText("Se cargará con el siguiente ID libre en la BBDD");

        jButton5.setText("INSERTAR");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        botonProducto.setText("Añadir");
        botonProducto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonProductoActionPerformed(evt);
            }
        });

        comboProductosSeleccionados.setToolTipText("Lista de productos añadidos a la venta");

        jButton1.setText("Quitar de la lista");
        jButton1.setToolTipText("Elimina el producto seleccionado de la lista");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(textVentaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboBoxClientes, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel15, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(textIdVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(textCantidadVenta, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(sliderVentas, javax.swing.GroupLayout.DEFAULT_SIZE, 270, Short.MAX_VALUE)
                                    .addComponent(comboProductosSeleccionados, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addComponent(botonProducto, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(comboBoxProductos, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(8, 8, 8)))))
                .addGap(16, 16, 16))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap(28, Short.MAX_VALUE)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textVentaCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBoxClientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(comboBoxProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(botonProducto))
                                .addGap(14, 14, 14))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)))
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(textCantidadVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(sliderVentas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textIdVenta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboProductosSeleccionados, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jTabbedPane2.addTab("Venta", jPanel6);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Opciones"));

        buttonGroup2.add(radiobbdd);
        radiobbdd.setToolTipText("Si selecciona esta opción las insercciones se harán en la Base de Datos");
        radiobbdd.setLabel("BBDD");

        buttonGroup2.add(radioXML);
        radioXML.setToolTipText("Si selecciona esta opción las insercciones se harán en el fichero XML");
        radioXML.setLabel("XML");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(radiobbdd)
                    .addComponent(radioXML))
                .addContainerGap(72, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(radiobbdd)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(radioXML)
                .addContainerGap(208, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 561, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 311, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Listas BBDD"));

        buttonGroup1.add(radioClientes);
        radioClientes.setText("Clientes");
        radioClientes.setToolTipText("Volver a pulsar el boton deseado al cambiar de BBDD");
        radioClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioClientesActionPerformed(evt);
            }
        });

        buttonGroup1.add(radioProductos);
        radioProductos.setText("Productos");
        radioProductos.setToolTipText("Volver a pulsar el boton deseado al cambiar de BBDD");
        radioProductos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                radioProductosActionPerformed(evt);
            }
        });

        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        listaObjetosBBDD.setToolTipText("Volver a pulsar el boton deseado al cambiar de BBDD");
        listaObjetosBBDD.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                listaObjetosBBDDMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(listaObjetosBBDD);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 323, Short.MAX_VALUE)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addComponent(radioClientes)
                        .addGap(27, 27, 27)
                        .addComponent(radioProductos)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(radioClientes)
                    .addComponent(radioProductos))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Objetos Insertados"));

        jScrollPane2.setBorder(javax.swing.BorderFactory.createTitledBorder("PRODUCTOS"));
        jScrollPane2.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane2.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jScrollPane2.setViewportView(listaProductosInsertados);

        jScrollPane3.setBorder(javax.swing.BorderFactory.createTitledBorder("VENTAS"));
        jScrollPane3.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane3.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jScrollPane3.setViewportView(listaVentasInsertadas);

        jScrollPane4.setBorder(javax.swing.BorderFactory.createTitledBorder("CLIENTES"));
        jScrollPane4.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        jScrollPane4.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jScrollPane4.setViewportView(listaClientesInsertados);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addContainerGap(17, Short.MAX_VALUE)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 427, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );

        barraProgreso1.setMaximumSize(new java.awt.Dimension(32767, 50));
        barraProgreso1.setMinimumSize(new java.awt.Dimension(10, 50));

        botonCargarXML.setEnabled(false);
        botonCargarXML.setLabel("CARGAR XML EN BBDD");
        botonCargarXML.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                botonCargarXMLActionPerformed(evt);
            }
        });

        labelFichero.setFont(new java.awt.Font("Lucida Grande", 2, 10)); // NOI18N
        labelFichero.setForeground(new java.awt.Color(51, 153, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(97, 97, 97)
                                    .addComponent(labelFichero, javax.swing.GroupLayout.PREFERRED_SIZE, 114, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(17, 17, 17)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(barraProgreso1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(botonCargarXML, javax.swing.GroupLayout.DEFAULT_SIZE, 201, Short.MAX_VALUE)))
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addGap(17, 17, 17)
                                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addComponent(comboTipoBBDD, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(botonSeleccionFile, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(21, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comboTipoBBDD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(labelFichero, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonSeleccionFile, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(58, 58, 58)
                        .addComponent(barraProgreso1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(botonCargarXML, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(35, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Inserciones", jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void botonSeleccionFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonSeleccionFileActionPerformed

        //CREAMOS SELECTOR DE FICHEROS
        JFileChooser jf = new JFileChooser();
        //CREAMOS FILTROS PARA TIPOS DE FICHEROS
        FileNameExtensionFilter filtro = new FileNameExtensionFilter("Ficheros  XML", "xml");
        jf.setFileFilter(filtro);

        //ABRIMOS EL SELECTOR
        int retorno = jf.showOpenDialog(null);

        //SI HEMOS ACEPTADO Y LA EXTENSIÓN COINCIDE ASIGNAMOS LA RUTA DEL FICHERO A LA VARIABLE
        if(retorno == JFileChooser.APPROVE_OPTION){
            File f = jf.getSelectedFile();
            String extension = f.getName().substring(f.getName().length() - 3); //COGEMOS EXTENSIÓN
            
            //RESETEAMOS LA BARRA DE PROGRESO
            gestor1.resetearBarraProgreso();
            
            if(extension.equalsIgnoreCase("xml")){
               
                gestor1.setFicheroXML(f);
                mostrarPanelInfo("Fichero seleccionado correctamente");
                labelFichero.setText(f.getName());
                
                //HABILITAMOS BOTÓN PARA CARGAR EL FICHERO
                botonCargarXML.setEnabled(true);
            }
            else{
                mostrarPanelError("Fichero no válido, solo se admiten ficheros XML");
                botonCargarXML.setEnabled(false);
            }
        }
    }//GEN-LAST:event_botonSeleccionFileActionPerformed

    //ESCUCHADOR PARA CAMBIAR EL TIPO DE BBDD DEPENDIENDO DE LA OPCIÓN SELECCIONADA EN EL COMBO
    private void comboTipoBBDDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboTipoBBDDActionPerformed

        this.tipoBBDD = comboTipoBBDD.getSelectedItem().toString().toLowerCase();
        //RESETEAMOS BARRA PROGRESO
        gestor1.resetearBarraProgreso();
        //RECARGAMOS EL MODELO DE LA LISTA DE OBJETOS DE LA BBDD
        this.modelolistaObjetos =  new DefaultListModel<>();
        listaObjetosBBDD.setModel(modelolistaObjetos);
        
        //ACTUALIZAMOS LOS MODELOS DE LOS COMBOBOX DE LA PESTAÑA VENTAS
        actualizarComboClientes();
        actualizarComboProductos();
        
        //ACTUALIZAMOS LAS LISTAS DE OBJETOS A MOSTRAR
        if(radioClientes.isSelected())
            actualizarListaClientes();
        else{
            if(radioProductos.isSelected())
                actualizarListaProductos();
        }
            
                
        
        //ACTUALIZAMOS EL CAMPO DE TEXTO QUE MUESTRA EL SIGUIENTE ID DISPONIBLE
        actualizarIdVenta();
        
        //ACTUALIZAMOS LOS CAMPOS DE TEXTO DE LA PESTAÑA VENTAS
        actualizarClienteVentas();
       
        
    }//GEN-LAST:event_comboTipoBBDDActionPerformed
    
    //MÉTODO QUE ACTUALIZA EL ID DE LA VENTA CON EL SIGUIENTE DISPONIBLE EN CADA BBDD
    public void actualizarIdVenta(){
        
        if(tipoBBDD.equals("db4o"))
            textIdVenta.setText(String.valueOf(gestor1.pedirSiguienteIdVentaDB4O()));
        else   
            textIdVenta.setText(String.valueOf(gestor1.pedirSiguienteIdVentas(tipoBBDD)));
    }
    
    //MÉTODO QUE ACTUALIZA LOS CAMPOS DE TEXTO SEGÚN EL CLIENTE SELECCIONADO EN LA PESTAÑA VENTAS
    public void actualizarClienteVentas(){
        //COGEMOS EL CLIENTE  SELECCIONADOS EN LOS COMBOS
        if(modeloComboClientes.getSize() != 0){
            Cliente clienteSel = (Cliente)modeloComboClientes.getSelectedItem();
            textVentaCliente.setText(clienteSel.getNif());
        }
           
    }
    
    
    
    
    //MÉTODO QUE ACTUALIZA EL COMBOBOX DE CLIENTES EN LA PESTAÑA VENTAS
    public void actualizarComboClientes(){
        
        //RESETEAMOS MODELO
        this.modeloComboClientes = new DefaultComboBoxModel<>();
        //SACAMOS LA LISTA DE CLIENTES SEGÚN LA BBDD SELECCIONADA
        ArrayList<Cliente> listaC;
        if(this.tipoBBDD.equals("db4o"))
            listaC = gestor1.pedirListaClientesDB4O();
        else
            listaC = gestor1.pedirListaTodosClientes(this.tipoBBDD);
        
        for(Cliente cliente: listaC){
            this.modeloComboClientes.addElement(cliente);
        }
        
        comboBoxClientes.setModel(this.modeloComboClientes);
        
    }
    
    //MÉTODO QUE ACTUALIZA EL COMBOBOX DE PRODUCTOS EN LA PESTAÑA VENTAS
    public void actualizarComboProductos(){
        
        //RESETEAMOS MODELO
        this.modeloComboProductos = new DefaultComboBoxModel<>();
        //SACAMOS LA LISTA DE CLIENTES SEGÚN LA BBDD SELECCIONADA
        ArrayList<Producto> listaP;
        if(this.tipoBBDD.equals("db4o"))
            listaP = gestor1.pedirListaProductosDB4O();
        else
            listaP = gestor1.pedirListaTodosProductos(this.tipoBBDD);
        
        for(Producto producto: listaP){
            this.modeloComboProductos.addElement(producto);
        }
        
        comboBoxProductos.setModel(this.modeloComboProductos);
        
    }
    
    //MÉTODO QUE INICIA EL VOLCADO DEL FICHERO XML EN LA BBDD SELECCIONADA
    private void botonCargarXMLActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonCargarXMLActionPerformed
        
        mapaInserciones = gestor1.cargarFicherosXML(this.tipoBBDD);
        
        //SACAMOS DEL MAPA LAS LISTAS DE INSERCIONES
        ArrayList<Cliente> listaCI = (ArrayList<Cliente>) mapaInserciones.get("clientes");
        ArrayList<Producto> listaPI = (ArrayList<Producto>) mapaInserciones.get("productos");
        
        //MOSTRAMOS UN PRIMER MENSAJE CON LA INFORMACIÓN INSERTADA
        mostrarPanelInfo("Se insertaron " + listaCI.size() + " clientes y " + listaPI.size() + " productos.");
        
        //CARGAMOS LAS INSERCIONES EN LAS DIFERENTES LISTAS DE LA INTERFACE
        cargarListaInserciones( listaCI, listaPI);
        
        //ACTUALIZAMOS LAS LISTAS DEPRODUCTOS Y CLIENTES DE LA PESTAÑA VENTAS
        actualizarComboClientes();
        actualizarComboProductos();
        
    }//GEN-LAST:event_botonCargarXMLActionPerformed

    //EVENTO QUE CONTROLA EL RADIO CLIENTES- MUESTRA TODOS LOS CLIENTES DE UNA DETERMINADA BBDD
    private void radioClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioClientesActionPerformed
        
        //LLAMAMOS AL MÉTODO QUE ACTUALIZA LA LISTA DE CLIENTES
       actualizarListaClientes();
        
    }//GEN-LAST:event_radioClientesActionPerformed

    //EVENTO QUE CONTROLA EL RADIO PRODUCTOS- MUESTRA TODOS LOS CLIENTES DE UNA DETERMINADA BBDD
    private void radioProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_radioProductosActionPerformed
        
        //LLAMAMOS AL MÉTODO QUE ACTUALIZA LA LISTA DE PRODUCTOS
        actualizarListaProductos();
    }//GEN-LAST:event_radioProductosActionPerformed
    
//MÉTODO QUE RELLENA LA LISTA DE CLIENTES EN LA INTERFACE GRÁFICA
    public void actualizarListaClientes(){
        
        //INSTANCIAMOS-RESETEAMOS EL MODELO PARA LA LISTA
        this.modelolistaObjetos = new DefaultListModel<>();
        //CREAMOS LISTA PARA GUARDAR TODOS LOS CLIENTES Y LLAMAMOS AL MÉTODO DEL GESTOR_BBDD
        ArrayList<Cliente> listaC;
        
        if(this.tipoBBDD.equalsIgnoreCase("db4o"))
            listaC = gestor1.pedirListaClientesDB4O();
        else
            listaC = gestor1.pedirListaTodosClientes(tipoBBDD);
        
        //CARGAMOS LISTA EN EL MODELO
        for(Cliente cliente:listaC){
            modelolistaObjetos.addElement(cliente.toString());
        }
        
        //ASIGNAMOS MODELO A LA LISTA DE LA INTERFACE GRÁFICA
        listaObjetosBBDD.setModel(modelolistaObjetos);
    }
    
    
    //MÉTODO QUE RELLENA LA LISTA DE PRODUCTOS  EN LA INTERFACE GRÁFICA
    private void actualizarListaProductos(){
        
        //INSTANCIAMOS-RESETEAMOS EL MODELO PARA LA LISTA
        this.modelolistaObjetos = new DefaultListModel<>();
        //CREAMOS LISTA PARA GUARDAR TODOS LOS CLIENTES Y LLAMAMOS AL MÉTODO DEL GESTOR_BBDD
        ArrayList<Producto> listaP;
        
        if(this.tipoBBDD.equalsIgnoreCase("db4o"))
            listaP = gestor1.pedirListaProductosDB4O();
        else
            listaP = gestor1.pedirListaTodosProductos(tipoBBDD);
        
        //CARGAMOS LISTA EN EL MODELO
        for(Producto producto:listaP){
            modelolistaObjetos.addElement(producto.toString());
        }
        
        //ASIGNAMOS MODELO A LA LISTA DE LA INTERFACE GRÁFICA
        listaObjetosBBDD.setModel(modelolistaObjetos);      
    }
    
    
    
    
    private void listaObjetosBBDDMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_listaObjetosBBDDMouseClicked
       
        //SI HACEMOS DOBLE CLICK EN UN VALOR DE LA LISTA
        if (evt.getClickCount() == 2) {
           
            //SI ESTÁ SELECCIONADA LA LISTA DE CLIENTES Y EL MODELO NO ESTÁ VACIO
            if(radioClientes.isSelected() && !this.modelolistaObjetos.isEmpty()){
                String value = listaObjetosBBDD.getSelectedValue();
                //SACAMOS EL NIF DEL CLIENTE DE LA LISTA
                String nifCliente = value.substring(0, value.indexOf(" -"));
                //OBTENEMOS EL OBJETO CLIENTE CON ESE NIF DE LA BBDD SELECCIONADA
                Cliente cliente;
                if(this.tipoBBDD.equalsIgnoreCase("db4o"))
                    cliente = this.gestor1.pedirCLienteDB4O(nifCliente);
                else
                    cliente =  this.gestor1.pedirClienteBBDD(nifCliente, this.tipoBBDD);
                
                cargarClienteEnFormulario(cliente);
                
                //SELECCIONAMOS LA PESTAÑA CLIENTE
                jTabbedPane2.setSelectedIndex(0);
            }
            else{
                if(radioProductos.isSelected() && !this.modelolistaObjetos.isEmpty()){
                    String value = listaObjetosBBDD.getSelectedValue();
                    int idProducto = Integer.parseInt(value.substring(0, value.indexOf(" -")));
                    Producto producto;
                    
                    if(this.tipoBBDD.equalsIgnoreCase("db4o"))
                        producto = this.gestor1.pedirProductoDB4O(idProducto);
                    else
                        producto =  this.gestor1.pedirProductoBBDD(idProducto, this.tipoBBDD);
                
                    cargarProductoEnFormulario(producto);
                    //SELECCIONAMOS LA PESTAÑA PRODUCTO
                    jTabbedPane2.setSelectedIndex(1);
                }
            }
            
           
        }
    }//GEN-LAST:event_listaObjetosBBDDMouseClicked

    //EVENTO QUE REALIZA LA INSERCIÓN/ACTUALIZACIÓN DE UN CLIENTE EN LA BBDD
    private void botonInClienteActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonInClienteActionPerformed
       
        //CREAMOS EL OBJETO CLIENTE
        String nombre = textNombreCLiente.getText();
        String dir = textDirCliente.getText();
        String pob = textPobCliente.getText();
        String tel = textTelCliente.getText();
        String nif = textNifCliente.getText();
        
        if(nombre.equals("") || dir.equals("") || pob.equals("") || tel.equals("") || nif.equals(""))
            mostrarPanelError("Faltan datos por insertar");
        else{
            Cliente cliente = new Cliente(nombre, dir, pob, tel, nif);
            
            //SI ESTÁ SELECCINADO EL RADIO BBDD INSERTAMOS O ACTUALIZAMOS EN LA BBDD
            if(radiobbdd.isSelected()){
                
                //DISTINGUIMOS SI LA BBDD ES DB4O
                if (!this.tipoBBDD.equals("db4o")) {
                    if (gestor1.insertarClienteBBDD(cliente, tipoBBDD) > 0) {                
                        mostrarPanelInfo("Insertado o modificado el cliente: " + cliente.getNombre());
                        //MOSTRAMOS EN EL PANEL DE INSERCIONES
                        mostrarInsercionClientePanel(cliente);
                    } else 
                        mostrarPanelError("No se pudo actualizar/insertar el cliente");                    
                }
                else{
                    //INSERCIÓN INDIVIDUAL PARA DB4O
                    if(gestor1.insertarClienteDB4O(cliente))
                        mostrarPanelInfo("NUEVO CLIENTE INSERTADO EN LA BBDD DB4O");
                    else
                        mostrarPanelInfo("CLIENTE MODIFICADO EN LA BBDD DB4O");
                    
                    mostrarInsercionClientePanel(cliente);
                }
                actualizarListaClientes();
                radioClientes.setSelected(true);
            }
            else
            {
                if(radioXML.isSelected()){                  
                       //INSERTAMOS EN EL XML
                    if (gestor1.insertarClienteEnXML(cliente)){
                       mostrarPanelInfo("CLIENTE INSERTADO EN FICHERO XML");
                       mostrarInsercionClientePanel(cliente);
                    }
                    else 
                       mostrarPanelError("EL CLIENTE YA ESTÁ EN EL FICHERO XML");
                    
                }
                else
                    mostrarPanelError("Ningúna opción de inserción seleccionada.");
            }
        }
        
        //FALTA PONERLE LA ACTUALIZACIÓN INSERCIÓN EN LA BBDD DB4O
    }//GEN-LAST:event_botonInClienteActionPerformed

    private void botonInProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonInProductoActionPerformed
        // TODO add your handling code here:
        //CREAMOS EL OBJETO PRODUCTO
        try{
            int id = Integer.parseInt(textIdProducto.getText());
            String desc = textDesProducto.getText();
            int stockA = Integer.parseInt(textStockAProducto.getText());
            int stockM = Integer.parseInt(textStockMProducto.getText());
            
            //COGEMOS EL PRECIO Y LE QUITAMOS EL SIMBOLO €
            //COMPROBAMOS SI LLEVA EL SIMBOLO €
            String pvpS = textPvpProducto.getText();
            float pvp;
            if(pvpS.contains("€"))
                 pvp = Float.parseFloat(pvpS.substring(0, pvpS.length()-1));
            else
                pvp = Float.parseFloat(pvpS);

            //COMPROBAMOS EL FORMATO DE LOS CAMPOS DE TEXTO
            if(id <= 0 || desc.equals("") || stockA <= 0 || stockM <= 0 || pvp <= 0 )
                mostrarPanelError("Faltan datos por insertar/  los datos numéricos deben ser positivos");
            else{
                Producto producto = new Producto(id, stockA, stockM, desc, pvp);

                //SI ESTÁ SELECCINADO EL RADIO BBDD INSERTAMOS O ACTUALIZAMOS EN LA BBDD
                if(radiobbdd.isSelected()){
                    if (!this.tipoBBDD.equals("db4o")) {
                        if (gestor1.insertarProductoBBDD(producto, tipoBBDD) > 0) {                          
                            mostrarPanelInfo("Insertado o modificado el producto: " + producto.getDescripcion());
                            
                            //MOSTRAMOS EN EL PLANE DE LA INTERFACE GRAFICA LA INSERCIÓN
                            mostrarInsercionProductoPanel(producto);
                        } 
                        else 
                            mostrarPanelError("No se pudo actualizar/insertar el producto");                                                     
                    }
                    else{
                        //PARA LA INSERCIÓN INDIVIDUAL DE PRODUCTOS EN DB4O
                        if(gestor1.insertarProductoDB4O(producto))
                            mostrarPanelInfo("NUEVO PRODUCTO INSERTADO EN LA BBDD DB4O");
                        else
                            mostrarPanelInfo("PRODUCTO MODIFICADO EN LA BBDD DB4O");

                            mostrarInsercionProductoPanel(producto);
                    }
                    
                    //ACTUALIZAMOS LISTA DE PRODUCTOS
                     actualizarListaProductos();
                     radioProductos.setSelected(true);
                }
                else{               
                    if(radioXML.isSelected()){
                        //INSERTAMOS EN EL XML
                        if(gestor1.insertarProductoEnXML(producto)){
                            mostrarPanelInfo("PRODUCTO INSERTADO EN FICHERO XML");
                            mostrarInsercionProductoPanel(producto);
                        }
                        else
                            mostrarPanelError("EL PRODUCTO YA ESTÁ EN EL FICHERO XML");
                    }
                    else
                        mostrarPanelError("Ningúna opción de inserción seleccionada.");
                }
            }
        }
        catch(NumberFormatException ex){
            ex.printStackTrace();
            mostrarPanelError("Formáto numérico erroneo en los campos de texto.");
        }
        
        //FALTA PONERLE LA ACTUALIZACIÓN INSERCIÓN EN LA BBDD DB4O
    }//GEN-LAST:event_botonInProductoActionPerformed

    //EVENTO PARA EL CAMBIO DE CLIENTE EN EL COMBO DE LA PESTAÑA VENTAS
    private void comboBoxClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_comboBoxClientesActionPerformed
        
        //CADA VEZ QUE SE SELECCIONE UN NUEVO CLIENTE SE ACTUALIZARÁ EN LOS CAMPOS DE TEXTO
        actualizarClienteVentas();
    }//GEN-LAST:event_comboBoxClientesActionPerformed

    //EVENTO PARA EL BOTÓN AÑADIR DE LA PESTAÑA VENTAS
    private void botonProductoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_botonProductoActionPerformed
        
        if(sliderVentas.getValue() > 0)
            actualizarProductosElegidos();
        else
            mostrarPanelError("La cantidad no puede ser 0");
    }//GEN-LAST:event_botonProductoActionPerformed

    //EVENTO PARA EL BOTÓN QUE BORRA UN PRODUCTO DE LA LISTA DE PRODUCTOS SELECCIONADOS
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        
        eliminarProductoSeleccionado();
        
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        //AQUIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII
        configurarTextAreaVenta();
        jDialog1.setVisible(true);
    }//GEN-LAST:event_jButton5ActionPerformed

    public void configurarTextAreaVenta(){
        
        String cadena1 = "JAVIER SERRANO GRAZAITI";
        String cadena2 = "MARAI MARCOS GRAZIATI";
        
        StringBuffer buffer = new StringBuffer(60);
        buffer.append(cadena1);
        
        
        
    }
    
    //MÉTODO QUE CARGA LAS INSERCIONES EN LAS DIFERENTES LISTAS DE LA INTERFACE(SI NO ESTÁN VACIAS)
    public void cargarListaInserciones(ArrayList<Cliente> listaCI , ArrayList<Producto> listaPI){
        
        //RESETEAMOS MODELOS DE LAS LISTAS DE INSERCIONES
        this.modeloListaInsercionCliente = new DefaultListModel<>();
        this.modeloListaInsercionProducto =  new DefaultListModel<>();
        this.modeloListaInsercionVenta =  new DefaultListModel<>();
        
        if(!listaCI.isEmpty()){
            for(Cliente cliente : listaCI){
                modeloListaInsercionCliente.addElement(cliente.toString());
            }
        }
        
        if(!listaPI.isEmpty()){
            for(Producto producto : listaPI){
                modeloListaInsercionProducto.addElement(producto.toString());
            }
        }
        
        //ASIGNAMOS LOS MODELOS A LAS LISTAS D ELA INTERFACE
        listaClientesInsertados.setModel(modeloListaInsercionCliente);
        listaProductosInsertados.setModel(modeloListaInsercionProducto);
        listaVentasInsertadas.setModel(modeloListaInsercionVenta);
        
        
    }
    
   
    //MÉTODO QUE CARGA EL CLIENTE SELECCIONADO EN LOS CAMPOS DE TEXTO DE LA INTERFACE GRÁFICA
    public void cargarClienteEnFormulario(Cliente cliente){
        
        textNombreCLiente.setText(cliente.getNombre());
        textDirCliente.setText(cliente.getDireccion());
        textPobCliente.setText(cliente.getPoblacion());
        textTelCliente.setText(cliente.getTelefono());
        textNifCliente.setText(cliente.getNif());
    }
    
    //MÉTODO QUE CARGA EL PRODUCTO SELECCIONADO EN LOS CAMPOS DE TEXTO DE LA INTERFACE GRÁFICA
    public void cargarProductoEnFormulario(Producto producto){
        
        textIdProducto.setText(String.valueOf(producto.getId()));
        textDesProducto.setText(producto.getDescripcion());
        textStockAProducto.setText(String.valueOf(producto.getStockActual()));
        textStockMProducto.setText(String.valueOf(producto.getStockMinimo()));
        textPvpProducto.setText(String.valueOf(producto.getPvp())+ "€");
    }
    
    //MÉTODO QUE ACTUALIZA EL MODELO DE LAS INSERCIONES INDIVIDUALES PARA
    //MOSTRARLO EN LA INTERFACE GRAFICA
    public void mostrarInsercionClientePanel(Cliente cliente){
        
        //RESETEAMOS  MODELO
        this.modeloListaInsercionCliente = new DefaultListModel<>();
        //AÑADIMOS INSERCIÓN A MODELO
        this.modeloListaInsercionCliente.addElement(cliente.toString());  
        
        this.listaClientesInsertados.setModel(modeloListaInsercionCliente);
    }
    
    public void mostrarInsercionProductoPanel(Producto producto){
        
        //RESETEAMOS  MODELO
        this.modeloListaInsercionProducto = new DefaultListModel<>();
        //AÑADIMOS INSERCIÓN A MODELO
        this.modeloListaInsercionProducto.addElement(producto.toString()); 
        
        this.listaProductosInsertados.setModel(modeloListaInsercionProducto);
    }
    
    public void mostrarInsercionVentaPanel(){
        
        
        
    }
    
    
    
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JProgressBar barraProgreso1;
    private javax.swing.JButton botonCargarXML;
    private javax.swing.JButton botonInCliente;
    private javax.swing.JButton botonInProducto;
    private javax.swing.JButton botonProducto;
    private javax.swing.JButton botonSeleccionFile;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<Cliente> comboBoxClientes;
    private javax.swing.JComboBox<Producto> comboBoxProductos;
    private javax.swing.JComboBox<Producto> comboProductosSeleccionados;
    private javax.swing.JComboBox<String> comboTipoBBDD;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton5;
    private javax.swing.JDialog jDialog1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JLabel labelFichero;
    private javax.swing.JList<String> listaClientesInsertados;
    private javax.swing.JList<String> listaObjetosBBDD;
    private javax.swing.JList<String> listaProductosInsertados;
    private javax.swing.JList<String> listaVentasInsertadas;
    private javax.swing.JRadioButton radioClientes;
    private javax.swing.JRadioButton radioProductos;
    private javax.swing.JRadioButton radioXML;
    private javax.swing.JRadioButton radiobbdd;
    private javax.swing.JSlider sliderVentas;
    private javax.swing.JTextField textCantidadVenta;
    private javax.swing.JTextArea textConfVenta;
    private javax.swing.JTextField textDesProducto;
    private javax.swing.JTextField textDirCliente;
    private javax.swing.JTextField textIdProducto;
    private javax.swing.JTextField textIdVenta;
    private javax.swing.JTextField textNifCliente;
    private javax.swing.JTextField textNombreCLiente;
    private javax.swing.JTextField textPobCliente;
    private javax.swing.JTextField textPvpProducto;
    private javax.swing.JTextField textStockAProducto;
    private javax.swing.JTextField textStockMProducto;
    private javax.swing.JTextField textTelCliente;
    private javax.swing.JTextField textVentaCliente;
    // End of variables declaration//GEN-END:variables
}
