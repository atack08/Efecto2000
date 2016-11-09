/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BEANS;

/**
 *
 * @author atack08
 */
public class Linea {
    
    private int idVenta;
    private Producto producto;
    private int cantidad;

    public Linea(int idVenta, Producto producto, int cantidad) {
        this.idVenta = idVenta;
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public int getIdVenta() {
        return idVenta;
    }

    public Producto getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }
    
    
    
}
