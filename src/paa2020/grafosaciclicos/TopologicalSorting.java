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
public class TopologicalSorting {
    private final ArrayList<Vertice> vertices;

    public TopologicalSorting(ArrayList vertices) {
        this.vertices = vertices;
    }
    
    private Vertice getVerticeById(String id){
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

    
}
