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
    private HashMap<Integer, Vertex> _idVertexMap;
    private HashMap<String, Vertex> _labelVertexMap;
    private HashMap<Integer, HashMap<Integer, Integer>> _edgeMap;

    // Stores the bottom level for each vertex
    private HashMap<Integer, Integer> _bottomLevelMap;

    public static Graph graphInstance;

    public Graph(String name) {
        Graph.graphInstance = this; // lol graph is only made once so this is only assigned once. should really be refactored
        _name = name;
        _idVertexMap = new HashMap<Integer, Vertex>();
        _labelVertexMap = new HashMap<String, Vertex>();
        _edgeMap = new HashMap<Integer, HashMap<Integer, Integer>>();
        _bottomLevelMap = new HashMap<Integer, Integer>();
    }

    public void addVertex(Vertex vertex) {
        _idVertexMap.put(vertex.getId(), vertex);
        _labelVertexMap.put(vertex.getLabel(), vertex);
        _edgeMap.put(vertex.getId(), new HashMap<Integer, Integer>());
    }

    public Vertex getVertex(int id) {
        return _idVertexMap.get(id);
    }

    public Vertex getVertex(String label) {
        return _labelVertexMap.get(label);
    }

    public int getEdgeWeight(int from, int to) {
        return _edgeMap.get(from).get(to);
    }

    // check if there exists a directed edge from vertex 1 to 2.
    public boolean hasEdge(int from, int to) {
        return _edgeMap.containsKey(from) && _edgeMap.get(from).containsKey(to);
    }

    /**
     * Return the root vertex of the graph. Relies on the assumption that only the root node has no
     * incoming edges, and should only be called once the Graph has been fully constructed.
     * @return Root Vertex of the graph
     */
    public List<Integer> getRoots() {
        // TODO: May need optimising in future.
        List<Integer> roots = new ArrayList<Integer>();
        for (Vertex v : _idVertexMap.values()) {
            if (v.getIncomingVertices().size() == 0) {
                roots.add(v.getId());
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
    public void addEdge(int fromId, int toId, int weight) {
        if (!_edgeMap.containsKey(fromId)) {
            throw new RuntimeException("No vertex with id '" + fromId + "' exists in this graph.");
        } else if (!_edgeMap.containsKey(toId)) {
            throw new RuntimeException("No vertex with id '" + toId + "' exists in this graph.");
        }
        _edgeMap.get(fromId).put(toId, weight);
        _idVertexMap.get(fromId).addOutgoingVertex(_idVertexMap.get(toId));
        _idVertexMap.get(toId).addIncomingVertex(_idVertexMap.get(fromId));
    }

    /**
     * Alias for Graph#addEdge(int fromId, int toId, int weight)
     * @param fromLabel Label of vertex at tail of edge
     * @param toLabel Label of vertex at head of edge
     * @param weight Weight of the edge
     */
    public void addEdge(String fromLabel, String toLabel, int weight) {
        addEdge(_labelVertexMap.get(fromLabel).getId(), _labelVertexMap.get(toLabel).getId(), weight);
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
    public int getBottomLevel(int vertexId) {

        Vertex v = _idVertexMap.get(vertexId);
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
        return new ArrayList<Vertex>(_idVertexMap.values());
    }

    public String getName() {
        return _name;
    }

}