package BEANS;

import java.util.ArrayList;
import java.util.Date;

public class Venta {

    private int id_venta;
    private Cliente cliente;
    private ArrayList<Linea> lineas;
    private Date fechaVenta;

    public Venta(int id_venta, Cliente cliente, ArrayList<Linea> lineas, Date fechaVenta) {
        this.id_venta = id_venta;
        this.cliente = cliente;
        this.lineas = lineas;
        this.fechaVenta = fechaVenta;
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
    
    

    
}
