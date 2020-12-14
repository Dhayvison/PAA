/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paa2020.grafosaciclicos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

/**
 *
 * @author dhayv
 */
public class PAA2020GrafosAciclicos {

    final static JFileChooser fc = new JFileChooser(new File("").getAbsolutePath() + "/src/inputs/");
    public static final ArrayList<Vertice> vertices = new ArrayList<Vertice>();
    
    public static File selectFile (){
        File file = null;
        int retr = fc.showOpenDialog(fc);
        
        if (retr == JFileChooser.APPROVE_OPTION) 
             file = fc.getSelectedFile();
        else 
            System.out.println("Erro: File not found");

        return file;
    }
    
    public static Vertice getVerticeById(String id){
        try {
            int index = Integer.parseInt(id);
            return vertices.get(index);
        } catch (Exception e) {
            for (Vertice vertice : vertices) {
                if(vertice.getId().equals(id))
                    return vertice;
            }
            return null;
        }
        
    }
    
    public static void makeGraph(){
        BufferedReader br;
        try {
            br = new BufferedReader(new FileReader(selectFile()));
            String line = br.readLine();
            line = line.replace("[", "");            
            line = line.replace("]", "");
            
            String[] verts = line.split(", ");
            
            for (String vert : verts) {
                vertices.add(new Vertice(vert));
            }
            
            line = br.readLine();
            line = line.replace("[", "").replace("]", "");
            line = line.replace("(", "").replace(')', 'x');
            
            String[] edges = line.split("x[, ]*");
            
            Vertice head, tail;
            Edge ed;
            int weight;
            String[] tuple;
            for (String edge : edges) {
                tuple = edge.split(", ");
               
                head = getVerticeById(tuple[0]);
                tail = getVerticeById(tuple[1]);
                weight = 1;
                
                try {
                    weight = Integer.parseInt(tuple[2], 10);
                } catch (ArrayIndexOutOfBoundsException e) {}
                
                ed = new Edge(head, tail, weight);
                head.addLeavingEdge(ed);
                tail.addIncomingEdge(ed);
            };
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(PAA2020GrafosAciclicos.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(PAA2020GrafosAciclicos.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    public static boolean hasEdgesGraph(){
        for (Vertice vertice : vertices) {
            for (Edge edge : vertice.getLeaving()) {
                if(!edge.isRemoved())
                    return true;
            }
        }
        return false;
    }
    
    public static void main(String[] args) {
        makeGraph();
        
        for (Vertice vertice : vertices) {
            System.out.print(vertice + " => ");
            for (Edge edge : vertice.getLeaving()) {
                System.out.print(edge.getTail() + ":" + edge.getWeight() + " ");
            }
            System.out.println("");
        }
        System.out.println("");
        
        for (Vertice vRow : vertices) {
            for (Vertice vCol : vertices) {
               if (vRow.isAdjacency(vCol)){
                   System.out.print("1,");
               } else {
                   System.out.print("0,");
               }
            }
            System.out.println("");
        }
        
//        Collections.sort(vertices, new Comparator<Vertice>() {;
//            @Override
//            public int compare(Vertice v1, Vertice v2)
//            {
//                return v1.getLeaving().size() - (v2.getLeaving().size());
//            }
//        });
        
        ArrayList<Vertice> ordered = new ArrayList<>();
        ArrayList<Vertice> hasNotIncoming = new ArrayList<>();

        vertices.stream().filter((vertice) -> (vertice.getIncoming().isEmpty())).forEachOrdered((_item) -> {
            hasNotIncoming.add(_item);
        });
        
        System.out.print("\nSet of all nodes with no incoming edge: { ");
        for (Vertice vertice : hasNotIncoming) {
            System.out.print(vertice + " ");
        }
        System.out.println("}");
        
        Vertice nodo;
        while(!hasNotIncoming.isEmpty()){
            nodo = hasNotIncoming.get(0);
            hasNotIncoming.remove(0);
            
            ordered.add(nodo);
            
            for (Edge edge : nodo.getLeaving()) {
                edge.setRemoved(true);
                if(!(edge.getTail().hasIncomingEdges())){
                    hasNotIncoming.add(edge.getTail());
                }
            }
        }
        
        if (hasEdgesGraph()){
            System.out.println("Erro: Graph has at least one cycle.");
        } else {
            System.out.print("Success: A topologically sorted order: [ ");
            for (Vertice vertice : ordered) {
                System.out.print(vertice + " ");
            }
            System.out.println("]");
        }
        
        //CALCULAR CAMINHO MÍNIMO
        Vertice origem = getVerticeById("2");
        Vertice tail;
        
        origem.setDist(0);
        
        for (Vertice i : ordered) {
            for (Edge e : i.getLeaving()) {
                tail = e.getTail();
                if (i.getDist() + e.getWeight() < tail.getDist()){
                    tail.setDist(i.getDist() + e.getWeight());
                    tail.setPred(i);
                }
            }
            
        }
        
        System.out.println("\n\nSuccess: Caminhos MÍNIMOS com origem: " + origem);
        for (Vertice vertice : vertices) {
            System.out.println(vertice + " <=" + vertice.getHierarqPred());
        }
        
        //CALCULAR CAMINHO MÁXIMO 
        for (Vertice vertice : vertices) {
            vertice.setDist(-1000000);
        }
        
        origem.setDist(1000000);
        
        for (Vertice i : ordered) {
            for (Edge e : i.getLeaving()) {
                tail = e.getTail();
                if (i.getDist() + e.getWeight() > tail.getDist()){
                    tail.setDist(i.getDist() + e.getWeight());
                    tail.setPred(i);
                }
            }
            
        }
        
        System.out.println("\n\nSuccess: Caminhos MÁXIMOS com origem: " + origem);
        for (Vertice vertice : vertices) {
            System.out.println(vertice + " <=" + vertice.getHierarqPred());
        }
    }
    
}
