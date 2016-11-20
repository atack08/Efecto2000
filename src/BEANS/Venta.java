package BEANS;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Venta {

    private int id_venta;
    private Cliente cliente;
    private ArrayList<Linea> lineas;
    private Date fechaVenta;
    private float total;
    
    //CONSTRUCTOR VACIO PARA DB4O
    public Venta(){};
    
    public Venta(int id){
        this.id_venta = id;
    }
    
    public Venta(int id_venta, Cliente cliente, ArrayList<Linea> lineas, Date fechaVenta, float total) {
        this.id_venta = id_venta;
        this.cliente = cliente;
        this.lineas = lineas;
        this.fechaVenta = fechaVenta;
        this.total = total;
    }

    public int getId_venta() {
        return id_venta;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public ArrayList<Linea> getLineas() {
        return lineas;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public float getTotal() {
        return total;
    }

    public void setLineas(ArrayList<Linea> lineas) {
        this.lineas = lineas;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
 
    

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Venta other = (Venta) obj;
        if (this.id_venta != other.id_venta) {
            return false;
        }
        return true;
    }

    
    public String toStringLargo() {
        
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy -- HH:mm:ss");
        String fechaFormateada = sdf.format(this.fechaVenta);
        
        DecimalFormat df = new DecimalFormat("###.##");
        
        String cadenaVenta="ID: " + this.id_venta + "\n"
                + "Cliente: " + this.cliente.getNombre() + " (" + cliente.getNif() + ")\n"
                + "Fecha: " + fechaFormateada+"\n"
                + "Productos: \n";
        
        for(Linea linea: this.lineas){
            Producto p = linea.getProducto();
            
            cadenaVenta += "\t- " + linea.getCantidad() + "X " + p.getDescripcion()
                    + " - " + df.format((linea.getCantidad() * p.getPvp()))+ "€\n";       
        }
        
        cadenaVenta += "\nTotal: " + df.format(this.total) + "€";
        
        return cadenaVenta;
    }

    @Override
    public String toString() {
        
        //FROMATEAMOS FECHA Y TOTAL
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String fechaFormateada = sdf.format(this.fechaVenta);
        DecimalFormat df = new DecimalFormat("###.##");
        
        return  this.cliente.getNif() + " - " + fechaFormateada + " - " + df.format(this.total) + "€";
    }
    
    
    
    

    
}
