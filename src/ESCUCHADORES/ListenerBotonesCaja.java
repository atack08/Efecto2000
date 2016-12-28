package ESCUCHADORES;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import BEANS.Cliente;
import BEANS.Linea;
import BEANS.Producto;
import DAOS.GestorBBDD;
import HILOS.HiloQR;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.scene.shape.Line;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JTextField;

public class ListenerBotonesCaja implements ActionListener {

    //VISOR PARA EL CÓDIGO DEL ARTÍCULO
    private JTextField textIdArticulo;
    //GESTOR BASE DE DATOS
    private GestorBBDD g1;
    //LISTA PARA LAS LINEAS DE LA FACTURA
    private JList<Linea> listaLineasCaja;
    private DefaultListModel<Linea> modeloLineasCaja;
    private JFrame ventanaPrincipal;
    //ENTRADAS FORMULARIO
    private JTextField textId, textDesc, textStockA, textStockM, textPrecio;
    private JButton botonIntroCaja, botonComprar;
    private JSpinner cantidadCaja;
    private Producto p;
    private HashMap<Producto, Integer> mapaVenta;
    private float totalVenta;

    //CLASE QUE CREARÁ UN HILO NUEVO DE EJECUCIÓN PARA RECIBIR EL CÓDIGO QR POR RED
    private HiloQR hiloQR;

    public ListenerBotonesCaja(JTextField textIdArticulo, GestorBBDD g, JList<Linea> listaL, JFrame vP, JTextField tId, JTextField tDesc,
            JTextField tSA, JTextField tSM, JTextField tP, JButton botonIntro, JSpinner cantidadCaja, JButton bComprar, JLabel lb) {

        this.textIdArticulo = textIdArticulo;
        this.g1 = g;
        this.listaLineasCaja = listaL;
        this.ventanaPrincipal = vP;
        this.textId = tId;
        this.textDesc = tDesc;
        this.textStockA = tSA;
        this.textStockM = tSM;
        this.textPrecio = tP;
        this.cantidadCaja = cantidadCaja;
        this.p = null;
        this.botonComprar = bComprar;

        this.botonIntroCaja = botonIntro;

        modeloLineasCaja = new DefaultListModel<>();
        listaL.setModel(modeloLineasCaja);

        iniciarHiloQR(lb,textIdArticulo);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String action = e.getActionCommand();
        String n = textIdArticulo.getText();

        switch (action) {

            case "ca":
                textIdArticulo.setText("");
                resetearFormulario();
                break;

            case "cl":
                if (!n.equals("")) {
                    textIdArticulo.setText(n.substring(0, n.length() - 1));
                }
                break;

            case "intro":

                int cant = ((Integer) cantidadCaja.getValue());
                Linea l = new Linea(p, cant);

                if (!modeloLineasCaja.contains(l)) {
                    modeloLineasCaja.addElement(l);
                } else {
                    Linea lVieja = modeloLineasCaja.getElementAt(modeloLineasCaja.indexOf(l));
                    lVieja.setCantidad(l.getCantidad() + lVieja.getCantidad());
                    listaLineasCaja.repaint();
                }

                botonComprar.setEnabled(true);
                textIdArticulo.setText("");
                resetearFormulario();
                

                break;

            case "comprar":
                configurarMapaVentas();
                g1.insertarVentaMysql(new Cliente("00000000-0"), mapaVenta, totalVenta, new Timestamp(System.currentTimeMillis()));
                resetearCaja();
                break;

            default:
                if (textIdArticulo.getText().length() <= 9) {

                    textIdArticulo.setText(textIdArticulo.getText() + e.getActionCommand());
                }
                break;
        }

        buscarRellenarProducto();
        

    }
    
    public void buscarRellenarProducto(){
        
        System.err.println("ENTRA EL METODITO");
        if (!textIdArticulo.getText().equals("")) {
            p = g1.pedirProductoBBDD(Integer.parseInt(textIdArticulo.getText()), "mysql");
            
            if (p != null) {
                rellenarFormularioProducto(p);
                botonIntroCaja.setEnabled(true);
                cantidadCaja.setEnabled(true);
            } 
            else {
                botonIntroCaja.setEnabled(false);
                cantidadCaja.setEnabled(false);
                resetearFormulario();

                if (modeloLineasCaja.isEmpty()) 
                    botonComprar.setEnabled(false);
                else 
                    botonComprar.setEnabled(true); 
            }
        }
        
        
    }

    public void rellenarFormularioProducto(Producto p) {

        textId.setText(String.valueOf(p.getId()));
        textDesc.setText(p.getDescripcion());
        textStockA.setText(String.valueOf(p.getStockActual()));
        textStockM.setText(String.valueOf(p.getStockMinimo()));
        textPrecio.setText(String.valueOf(p.getPvp()) + "€");

    }

    public void resetearFormulario() {

        textId.setText("");
        textDesc.setText("");
        textStockA.setText("");
        textStockM.setText("");
        textPrecio.setText("");
    }

    public void configurarMapaVentas() {

        mapaVenta = new HashMap<>();
        totalVenta = 0;

        Object[] objetos = modeloLineasCaja.toArray();
        for (Object obj : objetos) {
            Linea l = (Linea) obj;
            mapaVenta.put(l.getProducto(), l.getCantidad());
            totalVenta = totalVenta + (l.getProducto().getPvp() * l.getCantidad());
        }
    }

    public void resetearCaja() {
        mostrarPanelInfo("COMPRA REALIZADA CON EXITO");
        modeloLineasCaja.clear();
        botonComprar.setEnabled(false);
    }
    
    public void iniciarHiloQR(JLabel lb, JTextField id){
        hiloQR = new HiloQR(lb,id,this);
        hiloQR.start();
    }

    public void mostrarPanelError(String msg) {
        JOptionPane.showMessageDialog(ventanaPrincipal, msg, "ERROR", 0);
    }

    public void mostrarPanelInfo(String msg) {
        JOptionPane.showMessageDialog(ventanaPrincipal, msg, "INFO", 1);
    }

}
