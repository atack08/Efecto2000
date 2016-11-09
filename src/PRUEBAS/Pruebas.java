package PRUEBAS;

import BEANS.Cliente;
import BEANS.Producto;

import DAOS.GestorBBDD;


public class Pruebas
{
  public static void main(String[] args){
 
    GestorBBDD dao = new GestorBBDD("efecto2000", "localhost:8889", "root", "root", null);
   
    Cliente c =  new Cliente("Marcial sdfsdfdsfdsfres", "Cl dsfsdfdsf 193", "dsfsdf", "945098sdfds258", "51689544-K");
    Producto p = new Producto(222145, 10, 2, "Nvidia GTX 980 4GB", 340.20f);
    
    
    
    
      
      
  }
}
