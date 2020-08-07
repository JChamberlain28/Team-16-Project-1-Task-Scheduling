package graph;


public class Edge {


    private int _weight;
    private Vertex _startVertex;
    private Vertex _endVertex;
    private String _id;


    public Edge(Vertex from, Vertex to,int weight){
        _weight= weight;
        _startVertex=from;
        _endVertex=to;
      _id=(""+ from +" " +to ) ;
    }




}
