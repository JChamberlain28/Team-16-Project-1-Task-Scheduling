package graph;


public class Edge {

    private int _weight;
    private Vertex _startVertex;
    private Vertex _endVertex;
    private String _id;

    public Edge(Vertex from, Vertex to,int weight){
        _weight = weight;
        _startVertex = from;
        _endVertex = to;
        _id = "" + from + " " + to;
    }

    /* GETTERS & SETTERS*/

    public int getWeight() {
        return _weight;
    }

    public void setWeight(int _weight) {
        this._weight = _weight;
    }

    public Vertex getStartVertex() {
        return _startVertex;
    }

    public void setStartVertex(Vertex _startVertex) {
        this._startVertex = _startVertex;
    }

    public Vertex getEndVertex() {
        return _endVertex;
    }

    public void setEndVertex(Vertex _endVertex) {
        this._endVertex = _endVertex;
    }

    public String getId() {
        return _id;
    }

    public void setId(String _id) {
        this._id = _id;
    }
}
