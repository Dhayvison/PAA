/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paa2020.grafosaciclicos;

import java.util.ArrayList;


/**
 *
 * @author dhayv
 */
public class Vertice {
    
    private final String id;
    private final ArrayList<Edge> incoming;
    private final ArrayList<Edge> leaving;
    private int dist;
    private Vertice pred;

    public Vertice(String id) {
        this.incoming = new ArrayList<Edge>();
        this.leaving = new ArrayList<Edge>();
        this.id = id;
        this.dist = 1000000;
        this.pred = null;
    }
    
    public void addIncomingEdge(Edge edge) {
        this.incoming.add(edge);
    }
    
    public void addLeavingEdge(Edge edge) {
        this.leaving.add(edge);
    }
    
    public boolean hasIncomingEdges(){
        for (Edge edge : incoming) {
            if(!edge.isRemoved())
                return true;
        }
        return false;
    }
    
    public boolean isAdjacency (Vertice vertice){
        for (Edge edge : leaving) {
            if(edge.getTail() == vertice)
                return true;
        }
        return false;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Edge> getIncoming() {
        return incoming;
    }

    public ArrayList<Edge> getLeaving() {
        return leaving;
    }

    public int getDist() {
        return dist;
    }

    public void setDist(int dist) {
        this.dist = dist;
    }

    public Vertice getPred() {
        return pred;
    }
    
    public String getHierarqPred(){
        if(this.pred == null){
            return "" + this.dist;
        } else {
            int weight = 0;
            for (Edge edge : incoming) {
                if(edge.getHead() == pred){
                    weight = edge.getWeight();
                }
            }
            return "(" + weight +") " + this.pred.toString()+ " <=" + this.pred.getHierarqPred();
        }
    }

    public void setPred(Vertice pred) {
        this.pred = pred;
    }
    
    @Override
    public String toString() {
        return id;
    }
    
}
