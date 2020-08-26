package graph;

import
        java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Graph {

    private String _name;
    private HashMap<String, Vertex> _vertexMap;
    private HashMap<String, HashMap<String, Integer>> _edgeMap;

    public Graph(String name) {
        _name = name;
        _vertexMap = new HashMap<String, Vertex>();
        _edgeMap = new HashMap<String, HashMap<String, Integer>>();
    }

    public void addVertex(String id, Vertex vertex) {
        _vertexMap.put(id, vertex);
        _edgeMap.put(id, new HashMap<String, Integer>());
    }

    public Vertex getVertex(String id) {
        return _vertexMap.get(id);
    }

    public boolean hasVertex(String id) {
        return _vertexMap.containsKey(id);
    }

    public int getEdgeWeight(String from, String to) {
        return _edgeMap.get(from).get(to);
    }

    public boolean hasEdge(String from, String to) {
        return _edgeMap.containsKey(from) && _edgeMap.get(from).containsKey(to);
    }

    /**
     * Return the root vertex of the graph. Relies on the assumption that only the root node has no
     * incoming edges, and should only be called once the Graph has been fully constructed.
     * @return Root Vertex of the graph
     */
    public Vertex getRoot() {
        // TODO: May need optimising in future.
        Vertex root = null;
        for (Vertex v : _vertexMap.values()) {
            if (v.getIncomingVertices().size() == 0) {
                if (root != null) {
                    throw new RuntimeException("More than one root candidate!");
                } else {
                    root = v;
                }
            }
        }
        return root;
    }

    /**
     * Creates a directed edge between two vertices.
     * Note: Vertices with ID "[fromId]" must already exist in the Graph.
     * @param fromId ID of the Vertex at the tail
     * @param toId ID of the Vertex at the head
     * @param weight Weighting of the edge.
     */
    public void addEdge(String fromId, String toId, int weight) {
        if (!_edgeMap.containsKey(fromId)) {
            throw new RuntimeException("No vertex with id '" + fromId + "' exists in this graph.");
        } else if (!_edgeMap.containsKey(toId)) {
            throw new RuntimeException("No vertex with id '" + toId + "' exists in this graph.");
        }
        _edgeMap.get(fromId).put(toId, weight);
        _vertexMap.get(fromId).addOutgoingVertex(_vertexMap.get(toId));
        _vertexMap.get(toId).addIncomingVertex(_vertexMap.get(fromId));
    }


    public List<Vertex> getVertices() {
        return new ArrayList<Vertex>(_vertexMap.values());
    }

    public String getName() {
        return _name;
    }

    public int getNoOfVertices() {
        return _vertexMap.size();
    }

}
