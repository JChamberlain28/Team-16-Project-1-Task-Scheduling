package graph;

import java.util.ArrayList;
import java.util.List;

/*
 * Vertex objects to store inside graph object.
 * These represent the vertex objects of the input file graph and are used in the algorithm.
 * */
public class Vertex {

    private static int currId = 0;

    private int _id;  // uniquely identifies every vertex
    private String _label;  // human readable label for every vertex (parsed from .dot input)
    private ArrayList<Vertex> incomingVertices;
    private ArrayList<Vertex> outgoingVertices;

    private int _startTime;
    private int _processorNumber;
    private int _cost;

    public Vertex(String label, int cost) {
        incomingVertices = new ArrayList<Vertex>();
        outgoingVertices = new ArrayList<Vertex>();
        _label = label;
        _id = currId++;
        _cost = cost;
    }

    // Adding incoming and outgoing vertices, used for efficiency in the algorithm.
    public void addIncomingVertex(Vertex incomingVertex){
        incomingVertices.add(incomingVertex);
    }

    public void addOutgoingVertex(Vertex outgoingVertex){
        outgoingVertices.add(outgoingVertex);
    }


    public int getId() {
        return _id;
    }

    public String getLabel() {
        return _label;
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
