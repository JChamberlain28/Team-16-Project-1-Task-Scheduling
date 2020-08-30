package graph;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class GraphCopier {

    /**
     * Copies the vertices and edges from the passed Grah instance into a new Graph.
     * @param other
     * @return The newly created Graph instance.
     */
    public static Graph copyGraph(Graph other) {

        Graph newGraph = new Graph("");

        for (Vertex v : other.getVertices()) {
            Vertex newV = new Vertex(v.getLabel(), v.getCost());
            newV.setId(v.getId());
            newGraph.addVertex(newV);
        }

        for (Vertex v : other.getVertices()) {
            Vertex newV = newGraph.getVertex(v.getId());
            int newId = newV.getId();
            for (Vertex incV : v.getIncomingVertices()) {
                int incId = incV.getId();
                newGraph.addEdge(incId, newId, other.getEdgeWeight(incId, newId));
            }
        }

        return newGraph;

    }

}
