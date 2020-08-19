package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/*
* Graph implementation to store the data in the ".dot" file.
* This class is instantiated in the cli parser.
* the and called with the algorithm.
* */
public class Graph {

    private String _name;
    private HashMap<String, Vertex> _vertexMap;
    private HashMap<String, HashMap<String, Integer>> _edgeMap;

    // Stores the bottom level for each vertex
    private HashMap<String, Integer> _bottomLevelMap;

    public Graph(String name) {
        _name = name;
        _vertexMap = new HashMap<String, Vertex>();
        _edgeMap = new HashMap<String, HashMap<String, Integer>>();
        _bottomLevelMap = new HashMap<String, Integer>();
    }

    public void addVertex(String id, Vertex vertex) {
        _vertexMap.put(id, vertex);
        _edgeMap.put(id, new HashMap<String, Integer>());
    }

    public Vertex getVertex(String id) {
        return _vertexMap.get(id);
    }

    // check if there exists a specified vertex.
    public boolean hasVertex(String id) {
        return _vertexMap.containsKey(id);
    }

    // get edge weight given vertex 1 to 2.
    public int getEdgeWeight(String from, String to) {
        return _edgeMap.get(from).get(to);
    }

    // check if there exists a directed edge from vertex 1 to 2.
    public boolean hasEdge(String from, String to) {
        return _edgeMap.containsKey(from) && _edgeMap.get(from).containsKey(to);
    }

    /**
     * Return the root vertex of the graph. Relies on the assumption that only the root node has no
     * incoming edges, and should only be called once the Graph has been fully constructed.
     * @return Root Vertex of the graph
     */
    public List<Vertex> getRoots() {
        // TODO: May need optimising in future.
        List<Vertex> roots = new ArrayList<Vertex>();
        for (Vertex v : _vertexMap.values()) {
            if (v.getIncomingVertices().size() == 0) {
                roots.add(v);
            }
        }
        return roots;
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

    /**
     * Method which calls getBottomLevel(String) with the id of the passed Vertex.
     * @param v The Vertex to get the bottom level for.
     * @return The bottom level of the passed vertex.
     */
    public int getBottomLevel(Vertex v) {
        return getBottomLevel(v.getId());
    }

    /**
     * Returns the bottom level for the vertex with the given id. Only calculates the value if required (could already
     * be stored in _bottomLevelMap), and if so it stores it in _bottomLevelMap.
     * @param vertexId ID of the vertex.
     * @return The bottom level of the vertex with ID vertexId.
     */
    public int getBottomLevel(String vertexId) {

        Vertex v = _vertexMap.get(vertexId);
        if (_bottomLevelMap.containsKey(vertexId)) {
            return _bottomLevelMap.get(vertexId);
        } else {
            int maxBottomLevel = 0;
            for (Vertex vChild : v.getOutgoingVertices()) {
                maxBottomLevel = Math.max(maxBottomLevel, getBottomLevel(vChild));
            }

            int botLevel = v.getCost() + maxBottomLevel;
            _bottomLevelMap.put(vertexId, botLevel);
            return botLevel;
        }

    }


    /* GETTERS */
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
