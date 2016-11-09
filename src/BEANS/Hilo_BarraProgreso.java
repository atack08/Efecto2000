/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BEANS;

import javax.swing.JProgressBar;

/**
 *
 * @author atack08
 */
public class Hilo_BarraProgreso extends Thread{
    
    //LE PASAMOS LA BARRA PARA QUE LA ACTUALICE
    private JProgressBar barraProgreso;
    private int estado;
    
    public Hilo_BarraProgreso(JProgressBar barra){
        this.barraProgreso = barra;
        this.estado = 0;
        barraProgreso.setValue(estado);
        barraProgreso.repaint();
    }
    
    @Override
    public void run(){
        
        //IRÁ COMPROBANDO LA VARIABLE EN EL GESTOR BBDD  
        //E IRÁ ACTUALIZANDO LA BARRA
        while(estado < 100){
            barraProgreso.setValue(estado);
            barraProgreso.repaint();
        }
        barraProgreso.setValue(100);
        barraProgreso.repaint();
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }
    
    //MÉTODO PARA RESETEAR LA BARRA
    public void resetearBarra(){
        barraProgreso.setValue(0);
    }
    
}
