
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ricardocuevas
 */

public class Listas {
    
    public List<String>[] leeArchivo(String ruta){
        File archivo_in = null;
        FileReader fr = null;
        BufferedReader br = null;
        String linea;
        boolean es_listaA = false;
        boolean es_listaB = false;
        List<String> listaA = new ArrayList<>();
        List<String> listaB = new ArrayList<>();
        List<String>[] listas = new List[2];
        
        try{
            archivo_in = new File (ruta);
            fr = new FileReader(archivo_in);
            br = new BufferedReader(fr);
            while((linea=br.readLine())!=null){
                if(linea.equals("LISTA A")){
                    es_listaA = true;
                    if(es_listaB == true)
                        es_listaB = false;
                    continue;
                }
                if(linea.equals("LISTA B")){
                    es_listaB = true;
                    if(es_listaA==true)
                        es_listaA = false;
                    continue;
                }
                if(es_listaA==true){
                    listaA.add(linea);
                }
                if(es_listaB==true){
                    listaB.add(linea);
                }
            }
    
            listas[0] = listaA;
            listas[1] = listaB;
            
        }catch (Exception e){
            e.printStackTrace();
        }finally{
            try{
                fr.close();
            }catch (Exception e2){
                e2.printStackTrace();
            }
        }
        return listas;
    }
    
    public List<String> comparaListas(List[] listas){
        List<String> listaA = new ArrayList<>();
        List<String> listaB = new ArrayList<>();
        List<String> listaOut = new ArrayList<>();
        listaA = listas[0];
        listaB = listas[1];
        String[] palabrasA;
        String[] palabrasB;
        boolean es_igual; 
        boolean es_igual2; 
        String e;
        
        //Pasar a mayuscula toda la lista, para comparar entre puras mayusculas. Ej: hola!=hOlA, HOLA=HOLA
        listaA.replaceAll(String::toUpperCase);
        listaB.replaceAll(String::toUpperCase);
        
        //Quitar acentos para que no importen en la comparacion. Ej: mas!=más, mas=mas
        for(int r=0; r<listaA.size(); r++){
            e = listaA.get(r);
            e = Normalizer.normalize(e, Normalizer.Form.NFD);
            e = e.replaceAll("[^\\p{ASCII}]", "");
            listaA.set(r,e);
        }
        for(int r=0; r<listaB.size(); r++){
            e = listaB.get(r);
            e = Normalizer.normalize(e, Normalizer.Form.NFD);
            e = e.replaceAll("[^\\p{ASCII}]", "");
            listaB.set(r,e);
        }

        //Comparacion de cada palabra de cada frase de A, con cada palabra de cada frase de B. 
        for(String fraseA: listaA){
            palabrasA = fraseA.split(" ");
            for(String fraseB: listaB){
               palabrasB = fraseB.split(" ");
               es_igual = false;
               es_igual2 = true;
               for(int i=0; i<palabrasA.length; i++){
                   for(int j=0; j<palabrasB.length; j++){
                       if(es_igual2 == true){
                            if(palabrasB[j].equals(palabrasA[i])){
                                es_igual = true;
                            }
                       }
                   }
                   if(es_igual==true){
                       es_igual = false;
                   }else
                       es_igual2 = false; 
               }
               if(es_igual2 == true){
                   System.out.println("A" + (listaA.indexOf(fraseA)+1) + " B" + (listaB.indexOf(fraseB)+1));
                   listaOut.add("A" + (listaA.indexOf(fraseA)+1) + " B" + (listaB.indexOf(fraseB)+1));
               }
            }
        }
        return listaOut;
    }
    
    public void escribeArchivo(List<String> listaOut){
        FileWriter archivoOut = null;
        PrintWriter pw = null;
        try{
            archivoOut = new FileWriter("/Users/ricardocuevas/Desktop/archivoOut.txt");
            pw = new PrintWriter(archivoOut);

            for(String linea: listaOut)
                pw.println(linea);

        }catch (Exception e){
            e.printStackTrace();
        }finally {
           try{
              archivoOut.close();
           }catch (Exception e2){
              e2.printStackTrace();
           }
        }
    }
    
    public static void main(String[] args){
        Listas prueba = new Listas();
        
        prueba.escribeArchivo(prueba.comparaListas(prueba.leeArchivo("/Users/ricardocuevas/Desktop/archivoIn.txt")));
    }
}
