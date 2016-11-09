    package BEANS;

public class Producto {

    private int id;
    private int stockActual;
    private int stockMinimo;
    private String descripcion;
    private float pvp;

    public Producto(int id, int stockActual, int stockMinimo, String descripcion, float pvp) {
        this.id = id;
        this.stockActual = stockActual;
        this.stockMinimo = stockMinimo;
        this.descripcion = descripcion;
        this.pvp = pvp;
    }
    
    public Producto(int id){
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getStockActual() {
        return this.stockActual;
    }

    public void setStockActual(int stockActual) {
        this.stockActual = stockActual;
    }

    public int getStockMinimo() {
        return this.stockMinimo;
    }

    public void setStockMinimo(int stockMinimo) {
        this.stockMinimo = stockMinimo;
    }

    public String getDescripcion() {
        return this.descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public float getPvp() {
        return this.pvp;
    }

    public void setPvp(float pvp) {
        this.pvp = pvp;
    }

    public String toStringLargo() {
        return "Producto{id=" + this.id + ", stockActual=" + this.stockActual + ", stockMinimo=" + this.stockMinimo + ", descripcion=" + this.descripcion + ", pvp=" + this.pvp + '}';
    }
    
    public String toString(){
        return this.id + " - " + this.descripcion;
    }

    @Override
    public boolean equals(Object obj) {
        
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Producto other = (Producto) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }
    
    
}
