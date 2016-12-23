/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HILOS;

import Escuchadores.ListenerBotonesCaja;
import com.barcodelib.barcode.QRCode;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author atack08
 */
public class HiloQR extends Thread {

    private ServerSocket socketServidor;
    private DataInputStream entrada;
    private Socket clienteConectado;
    private String cadenaQR;
    private boolean escuchar;
    private JLabel labelQR;
    private JTextField textIdP;
    private ListenerBotonesCaja lbt;
    
    
    //VALORES CODIGO QR
    int udm, resol, rot;
    float mi,md,ms,min,tam;

    public HiloQR(JLabel lb, JTextField id, ListenerBotonesCaja lbt) {
        try {
            socketServidor = new ServerSocket(5555);
            escuchar = true;
            
            this.labelQR = lb;
            this.textIdP = id;
            this.lbt = lbt;
            
            udm = 0;
            resol = 72;
            rot = 0;
            mi = 0.000f;
            md = 0.000f;
            ms = 0.000f;
            min = 0.000f;
            tam = 5.00f;
            
        } catch (IOException ex) {
            mostrarPanelError(ex.getLocalizedMessage());
        }
    }

    @Override
    public void run() {
        try {

            while (escuchar) {

                clienteConectado = socketServidor.accept();

                //INSTANCIAMOS LOS STREAMS
                entrada = new DataInputStream(clienteConectado.getInputStream());

                //RECIBIMOS EL CÓDIGO
                cadenaQR = entrada.readUTF();
                
                System.err.println(cadenaQR);

                entrada.close();
                clienteConectado.close();
                
                generarQR();

            }

            socketServidor.close();
            
        } 
        catch (IOException ex) {
            mostrarPanelError(ex.getLocalizedMessage());
        }

    }
    
    public void generarQR(){
        
        QRCode cod = new QRCode();
        
        cod.setData(cadenaQR);
        cod.setDataMode(QRCode.MODE_BYTE);
        
        cod.setUOM(udm);
        cod.setLeftMargin(mi);
        cod.setResolution(resol);
        cod.setRightMargin(md);
        cod.setTopMargin(ms);
        cod.setBottomMargin(min);
        cod.setRotate(rot);
        cod.setModuleSize(tam);
        
        
        pintarCodigoQR(cod);
        textIdP.setText(cadenaQR);
        
        lbt.buscarRellenarProducto();
          
    }
    
    public void pintarCodigoQR(QRCode codigo){
        try {
            BufferedImage codImg = codigo.renderBarcode();
            ImageIcon img = new ImageIcon(codImg);
            Icon icono = new ImageIcon(img.getImage().getScaledInstance(labelQR.getWidth(), labelQR.getHeight(), Image.SCALE_DEFAULT));
            labelQR.setIcon(icono);
            labelQR.repaint();
            
            
        } catch (Exception ex) {
            mostrarPanelError(ex.getLocalizedMessage());
        }
        
    }

    public void mostrarPanelError(String msg) {
        JOptionPane.showMessageDialog(null, msg, "ERROR", 0);
    }

    public void mostrarPanelInfo(String msg) {
        JOptionPane.showMessageDialog(null, msg, "INFO", 1);
    }

    public void setEscuchar(boolean escuchar) {
        this.escuchar = escuchar;
    }

    public String getCadenaQR() {
        return cadenaQR;
    }

    public void setCadenaQR(String cadenaQR) {
        this.cadenaQR = cadenaQR;
    }
    
    
    
    
}
