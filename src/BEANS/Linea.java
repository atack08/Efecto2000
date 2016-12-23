/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BEANS;

import java.text.DecimalFormat;
import java.util.Objects;

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
    
    public Linea(Producto producto, int cantidad) {
     
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

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public void setIdVenta(int idVenta) {
        this.idVenta = idVenta;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    
    
    
    

    @Override
    public String toString() {
        DecimalFormat df = new DecimalFormat("###.##");
        return producto.getDescripcion() + "  ----  " + producto.getPvp() + "€   X" + cantidad + "  ----  " + df.format(cantidad*producto.getPvp()) + "€";
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
        final Linea other = (Linea) obj;
        if (this.idVenta != other.idVenta || !Objects.equals(this.producto, other.producto)) {
            return false;
        }
       
        return true;
    }
    
    
    
}
