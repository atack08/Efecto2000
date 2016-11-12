package PRUEBAS;

import BEANS.Cliente;
import BEANS.Producto;

import DAOS.GestorBBDD;


public class Pruebas
{
  public static void main(String[] args){
 
    GestorBBDD g1 = new GestorBBDD("efecto2000", "localhost:8889", "root", "root",null);
      System.out.println(g1.pedirStockActualProducto("sqlite", 122659));
    
    
    
      
      
  }
}
