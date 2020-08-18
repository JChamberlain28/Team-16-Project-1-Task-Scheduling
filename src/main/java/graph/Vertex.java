package graph;

import java.util.ArrayList;
import java.util.List;

/*
 * Vertex objects to store inside graph object.
 * These represent the vertex objects of the input file graph and are used in the algorithm.
 * */
public class Vertex {

    // Fields of the vertex object
    private String _id;
    private ArrayList<Vertex> incomingVertices;
    private ArrayList<Vertex> outgoingVertices;

    private int _startTime;
    private int _processorNumber;
    private int _cost;

    public Vertex(String id, int cost) {
        incomingVertices = new ArrayList<Vertex>();
        outgoingVertices = new ArrayList<Vertex>();
        _id = id;
        _cost = cost;
    }

    // Adding incoming and outgoing vertices, used for efficiency in the algorithm.
    public void addIncomingVertex(Vertex incomingVertex){
        incomingVertices.add(incomingVertex);
    }

    public void addOutgoingVertex(Vertex outgoingVertex){
        outgoingVertices.add(outgoingVertex);
    }


    //Getters and setters for vertex fields
    public String getId() {
        return _id;
    }

    public ArrayList<Vertex> getIncomingVertices() {
        return incomingVertices;
    }

    public ArrayList<Vertex> getOutgoingVertices() {
        return outgoingVertices;
    }

    public int getStartTime() {
        return _startTime;
    }

    public void setStartTime(int _startTime) {
        this._startTime = _startTime;
    }

    public int getProcessorNumber() {
        return _processorNumber;
    }

    public void setProcessorNumber(int _processorNumber) {
        this._processorNumber = _processorNumber;
    }

    public int getCost() {
        return _cost;
    }

}
