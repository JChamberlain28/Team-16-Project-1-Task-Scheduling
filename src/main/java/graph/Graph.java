package graph;

import java.util.*;

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

    public Graph(String name) {
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

    public HashMap<Integer, Vertex> getIdVertexMap() {
        return _idVertexMap;
    }

    public HashMap<Integer, HashMap<Integer, Integer>> getEdgeMap() {
        return _edgeMap;
    }

    public void setEdgeMap(HashMap<Integer, HashMap<Integer, Integer>> edgeMap) {
        _edgeMap = edgeMap;
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
        if (weight != -1){ // dont add to vertices lists if its a virtual edge
            _idVertexMap.get(fromId).addOutgoingVertex(_idVertexMap.get(toId));
            _idVertexMap.get(toId).addIncomingVertex(_idVertexMap.get(fromId));
        }

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
    public int getBottomLevel(int vertexId) { //TODO: Consider accounting for virtual edges (not incl in bottom lvl calc

        Vertex v = _idVertexMap.get(vertexId);
        if (_bottomLevelMap.containsKey(vertexId)) {
            return _bottomLevelMap.get(vertexId);
        } else {
            int maxBottomLevel = 0;
            for (Vertex vChild : v.getOutgoingVertices()) {
                if ((_edgeMap.get(v.getId()).get(vChild.getId())) == -1){ // this is virtual edge, ignore this childs cost
                    maxBottomLevel = Math.max(maxBottomLevel, (getBottomLevel(vChild) - vChild.getCost()));
                } else {
                    maxBottomLevel = Math.max(maxBottomLevel, getBottomLevel(vChild));
                }
            }

            int botLevel = v.getCost() + maxBottomLevel;
            _bottomLevelMap.put(vertexId, botLevel);
            return botLevel;
        }

    }


    public List<HashSet<Integer>> buildVirtualEdges(){ // returns a list of hashsets for unit test purposes
        List<HashSet<Integer>> identicalList = new ArrayList<HashSet<Integer>>();

        for (int vId: _idVertexMap.keySet()){

            Vertex v = _idVertexMap.get(vId); // checking this is not identical to another task
            for (int vIdTwo: _idVertexMap.keySet()) {
                if (vId != vIdTwo){
                    Vertex y = _idVertexMap.get(vIdTwo);
                    boolean incomingVertMatch = ((v.getIncomingVertices().size() == y.getIncomingVertices().size()) &&
                            (v.getIncomingVertices().containsAll(y.getIncomingVertices())));


                    boolean outgoingVertMarch = ((v.getOutgoingVertices().size() == y.getOutgoingVertices().size()) &&
                            (v.getOutgoingVertices().containsAll(y.getOutgoingVertices())));


                    boolean incomingEdgeWeightSame = true;
                    boolean outgoingEdgeWeightSame = true;

                    if ((v.getIncomingVertices().size()) != (y.getIncomingVertices().size())) {
                        incomingEdgeWeightSame = false;
                    } else {
                        for (Vertex z: v.getIncomingVertices()){
                            incomingEdgeWeightSame = _edgeMap.get(z.getId()).get(v.getId()).equals(_edgeMap.get(z.getId()).get(y.getId()));
                            if (!incomingEdgeWeightSame){
                                break;
                            }
                        }
                    }




                    if ((v.getOutgoingVertices().size()) != (y.getOutgoingVertices().size())) {
                        outgoingEdgeWeightSame = false;
                    } else {
                        for (Vertex z: v.getOutgoingVertices()){
                            outgoingEdgeWeightSame = _edgeMap.get(v.getId()).get(z.getId()).equals(_edgeMap.get(y.getId()).get(z.getId()));
                            if (!outgoingEdgeWeightSame){
                                break;
                            }
                        }
                    }





                    if ((v.getCost() == y.getCost()) && incomingVertMatch && outgoingVertMarch &&
                            incomingEdgeWeightSame && outgoingEdgeWeightSame){
                        boolean stored = false;
                        for (HashSet<Integer> identSubList : identicalList){
                            if (identSubList.contains(v.getId())){
                                identSubList.add(y.getId());
                                stored = true;
                            } else if (identSubList.contains((y.getId()))) {
                                identSubList.add(v.getId());
                                stored = true;
                            }

                        }
                        if (!stored){
                            identicalList.add(new HashSet<Integer>(Arrays.asList(v.getId(), y.getId())));
                        }

                    }
                }
            }

        }

        for (HashSet<Integer> identSubList : identicalList){
            // add virtual edges
            for (int i=0; i < (identSubList.size()-1); i++){
                // TODO: edge map still contains old edges, but it is not queried for the incoming / outgoing vertices
                // of a vertex, so it shouldn't cause a problem
                List<Integer> list = new ArrayList(identSubList);
                _idVertexMap.get(list.get(i)).clearOutgoingVertices();
                _idVertexMap.get(list.get(i+1)).clearIncomingVertices();
                _idVertexMap.get(list.get(i)).addOutgoingVertex(_idVertexMap.get(list.get(i+1)));
                _idVertexMap.get(list.get(i+1)).addIncomingVertex(_idVertexMap.get(list.get(i)));

                addEdge(list.get(i), list.get(i+1), -1); // -1 weight is virtual edge
            }
        }

        return identicalList;
    }


    /* GETTERS */
    public List<Vertex> getVertices() {
        return new ArrayList<Vertex>(_idVertexMap.values());
    }

    public String getName() {
        return _name;
    }

}
