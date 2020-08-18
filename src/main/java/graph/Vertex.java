package graph;

import java.util.ArrayList;
import java.util.List;


public class Vertex {

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

    public void addIncomingVertex(Vertex incomingVertex){
        incomingVertices.add(incomingVertex);
    }

    public void addOutgoingVertex(Vertex outgoingVertex){
        outgoingVertices.add(outgoingVertex);
    }


    /* GETTERS & SETTERS */

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
