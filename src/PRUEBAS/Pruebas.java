package PRUEBAS;

import BEANS.Cliente;
import BEANS.Producto;

import DAOS.GestorBBDD;


public class Pruebas
{
  public static void main(String[] args){
 
    String cadena1 = "JAVIER SERRANO GRAZAITI";
    String cadena2 = "MARAI MARCOS GRAZIATI";
        
    StringBuffer buffer = new StringBuffer(60);
    buffer.insert(0,cadena1);
    System.out.println(cadena1);
    System.out.println(cadena1.length());
    System.out.println(buffer);
    System.out.println(buffer.length());
    
    
    
    
      
      
  }
}
