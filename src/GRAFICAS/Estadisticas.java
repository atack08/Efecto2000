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
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author atack08
 */
public class Estadisticas extends JFrame{
    
    private GestorBBDD g1;
    
    public Estadisticas( String frameTitulo , String tablaTitulo, GestorBBDD g1, int tipo)
   {
       super(frameTitulo);
       this.g1 = g1;

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

               break;

       }

       ChartPanel chartPanel = new ChartPanel(barChart);
       chartPanel.setPreferredSize(new java.awt.Dimension(1366, 768));
       setContentPane(chartPanel);

       this.pack();
       this.setVisible(true);

   }
    
   private CategoryDataset createDatasetVentasUsuarios( )
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
}
