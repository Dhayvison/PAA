/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package paa2020.grafosaciclicos;

/**
 *
 * @author dhayv
 */
public class Edge {
    private final Vertice head;
    private final Vertice tail;
    private final int weight;
    private boolean removed;

    public Edge(Vertice head, Vertice tail, int weight) {
        this.head = head;
        this.tail = tail;
        this.weight = weight;
        this.removed = false;
    }
    
    @Override
    public String toString(){
        return "(" + this.head.getId() + ", " + this.tail.getId() + ")" ;
    }
    
    public boolean isRemoved() {
        return removed;
    }

    public void setRemoved(boolean removed) {
        this.removed = removed;
    }

    public Vertice getHead() {
        return head;
    }

    public Vertice getTail() {
        return tail;
    }

    public int getWeight() {
        return weight;
    }
    
    
}
