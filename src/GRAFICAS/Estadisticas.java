/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GRAFICAS;

import DAOS.GestorBBDD;
import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.BarRenderer3D;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author atack08
 */
public class Estadisticas extends JFrame{
    
    private GestorBBDD g1;
    private Color azulGoogle;
    
    public Estadisticas( String frameTitulo , String tablaTitulo, GestorBBDD g1, int tipo)
   {
       super(frameTitulo);
       this.g1 = g1;
       
       azulGoogle =  new Color(33,150,243);

       JFreeChart barChart = null;

       switch (tipo) {

           case 1:

               barChart = ChartFactory.createBarChart3D(
                       tablaTitulo,
                       "Clientes",
                       "Ventas",
                       createDatasetVentasUsuarios(),
                       PlotOrientation.VERTICAL,
                       true, true, false);

               break;

           case 2:

               barChart = ChartFactory.createBarChart3D(
                       tablaTitulo,
                       "Producto",
                       "Cantidad Vendida",
                       createDataSetProductosVendidos(),
                       PlotOrientation.HORIZONTAL,
                       true, true, false);

               break;

           case 3:

               barChart = ChartFactory.createBarChart3D(
                       tablaTitulo,
                       "Producto",
                       "Stocks",
                       createDatasetProductosStocks(),
                       PlotOrientation.HORIZONTAL,
                       true, true, false);

               break;

       }

       
       CategoryPlot plot = barChart.getCategoryPlot();
       BarRenderer3D render = (BarRenderer3D)plot.getRenderer();
       render.setSeriesPaint(0, azulGoogle);
       
       
       ChartPanel chartPanel = new ChartPanel(barChart);
       chartPanel.setPreferredSize(new java.awt.Dimension(1366, 768));
       
  
       JScrollPane scroll = new JScrollPane();
       scroll.setViewportView(chartPanel);
       
       setContentPane(scroll);

       this.pack();
       this.setVisible(true);

   }
    
   public CategoryDataset createDatasetVentasUsuarios( )
   {
      final String totalVentas = "TOTAL VENTAS EN €";        
           
      final DefaultCategoryDataset dataset = 
      new DefaultCategoryDataset( ); 
      
       HashMap<String, Float> mapaCV = g1.mapaClientesVentas();
       Iterator it = mapaCV.keySet().iterator();
       
       while (it.hasNext()) {
           
           String nif = (String)it.next();
           Float total = mapaCV.get(nif);
           
           dataset.addValue( total , totalVentas , nif );   
       }

      return dataset; 
   }
   
   public CategoryDataset createDataSetProductosVendidos( )
   {
      final String totalVentas = "CANTIDAD VENDIDA";        
           
      final DefaultCategoryDataset dataset = 
      new DefaultCategoryDataset( ); 
      
       HashMap<String, Integer> mapaCV = g1.pedirMapaTotalVentasProducto();
       Iterator it = mapaCV.keySet().iterator();
       
       while (it.hasNext()) {
           
           String desc = (String)it.next();
           int total = mapaCV.get(desc);
           
           dataset.addValue( total , totalVentas , desc );   
       }

      return dataset; 
   }
   
   public CategoryDataset createDatasetProductosStocks( )
   {
      final String stockActual = "Stock Actual"; 
      final String stockMinimo = "Stock Mínimo";
           
      final DefaultCategoryDataset dataset = 
      new DefaultCategoryDataset( ); 
      
       HashMap<String, Integer[]> mapaCV = g1.pedirMapaDescStocksProductos();
       Iterator it = mapaCV.keySet().iterator();
       
       while (it.hasNext()) {
           
           String desc = (String)it.next();
           Integer[] stocks = (Integer[])mapaCV.get(desc);
           int stockA = stocks[0];
           int stockM = stocks[1];
           
           dataset.addValue( stockA , stockActual , desc ); 
           dataset.addValue( stockM , stockMinimo , desc ); 
       }

      return dataset; 
   }
}
