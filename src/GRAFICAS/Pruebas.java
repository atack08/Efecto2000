/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GRAFICAS;

import BEANS.Venta;
import DAOS.GestorBBDD;
import java.util.ArrayList;

/**
 *
 * @author atack08
 */
public class Pruebas {
    
    public static void main(String args[]){
        
        GestorBBDD gestor1 = new GestorBBDD("efecto2000", "localhost:8889", "root", "root",null);
        
        ArrayList<Venta> listaV = gestor1.pedirListaTodosVentas("mysql");
        
        for(Venta venta:listaV){
            System.out.println(venta.toStringLargo()+"\n");
        }
        
        
        
    }
    
}
