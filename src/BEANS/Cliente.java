package BEANS;

import java.util.Objects;

public class Cliente {

    private String nombre;
    private String direccion;
    private String poblacion;
    private String telefono;
    private String nif;

    
    public Cliente(String nombre, String direccion, String poblacion, String telefono, String nif){
        this.nombre = nombre;
        this.direccion = direccion;
        this.poblacion = poblacion;
        this.telefono = telefono;
        this.nif = nif;
    }
    
    //CONSTRUCTOR CON LA CLAVE PARA QUE COMPARE SOLO EL NIF
    public Cliente(String nif){
        this.nif= nif;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return this.direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getPoblacion() {
        return this.poblacion;
    }

    public void setPoblacion(String poblacion) {
        this.poblacion = poblacion;
    }

    public String getTelefono() {
        return this.telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNif() {
        return this.nif;
    }

    public void setNif(String nif) {
        this.nif = nif;
    }

    public String toString() {
        return this.nif + " - " + this.nombre;    
    }
    
    public String toStringLargo(){
        return this.nombre + "("+ nif +")" + ", " + direccion + " - " + poblacion
                + ", tel: " + telefono;

    }


    @Override
    public boolean equals(Object obj) {
        Cliente clObjetivo = (Cliente)obj;
        if(this.getNif().equalsIgnoreCase(clObjetivo.getNif()))
            return true;
        else
            return false;
    }
    
    
}
