package graph;

import java.util.HashMap;

public class Graph {


    private String _name;
    private int _noOfVertices;
    private HashMap<String, Vertex> hashVertices;
    private HashMap<String, Edge> hashEdges ;


    public String get_name() {
        return _name;
    }

    public int get_noOfVertices() {
        return _noOfVertices;
    }

    public HashMap<String, Vertex> getHashVertices() {
        return hashVertices;
    }

    public HashMap<String, Edge> getHashEdges() {
        return hashEdges;
    }

    public Graph(String name){
        _name=name;
        _noOfVertices = 0;
        hashVertices = new HashMap<String, Vertex>();
        hashEdges = new HashMap<String, Edge>();
    }




}
