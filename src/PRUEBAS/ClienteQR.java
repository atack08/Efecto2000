/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PRUEBAS;

import BEANS.Venta;
import DAOS.GestorBBDD;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author atack08
 */
public class ClienteQR {
    
    public static void main(String[] args) {

        try {
            Socket socketCliente = new Socket(InetAddress.getByName("192.168.1.103"), 5555);
            DataOutputStream salida = new DataOutputStream(socketCliente.getOutputStream());
            salida.writeUTF("122122");
            
            salida.close();
            socketCliente.close();
            
        } catch (UnknownHostException ex) {
            Logger.getLogger(ClienteQR.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ClienteQR.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
