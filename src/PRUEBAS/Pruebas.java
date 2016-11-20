package PRUEBAS;

import BEANS.Cliente;
import BEANS.Producto;
import BEANS.Venta;

import DAOS.GestorBBDD;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;


public class Pruebas
{
    public static void main(String[] args) {

        GestorBBDD g1 = new GestorBBDD("efecto2000", "localhost:8889", "root", "root", null);

        /*Cliente cl = new Cliente("48025698-B");

        HashMap<Producto, Integer> mapaV = new HashMap<>();
        mapaV.put(new Producto(122634, 12, 2, "CPU Intel i7-6600 3.8Ghz", 220f), new Integer(11)); //10
        //mapaV.put(new Producto(122187), new Integer(15)); //10
        //mapaV.put(new Producto(122547), new Integer(4));
        
        
        ArrayList<Producto> listaP = g1.insertarVentaMysql(cl, mapaV, 650.55f, new Timestamp(System.currentTimeMillis()));
        System.out.println("Los siguientes productos han rebajado el stock mínimo");
        for(Producto p : listaP){
            System.out.println(p.toStringLargo());
            
        }*/
        ObjectContainer db4oC = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), "efecto2000.yap");
        ObjectSet<Venta> result = db4oC.queryByExample(new Venta());
        
        System.out.println(result.size());
        
        for(Venta venta: result){
            db4oC.delete(venta);
        }
        
        
        
        
        
        db4oC.close();
        
    }
}
