package graph;

import java.util.ArrayList;
import java.util.List;

public class Vertex {

    private String _id;
    private ArrayList<Edge> incomingEdges;
    private ArrayList<Edge> outgoingEdges;
    private ArrayList<Vertex> incomingVertices;
    private ArrayList<Vertex> outgoingVertices;

    private int _startTime;
    private int _processorNumber;
    private int _cost;

    public Vertex(String id, int cost) {

        incomingEdges = new ArrayList<Edge>();
        outgoingEdges = new ArrayList<Edge>();
        incomingVertices = new ArrayList<Vertex>();
        outgoingVertices = new ArrayList<Vertex>();
        _id = id;
        _cost = cost;

    }

    public void addIncomingEdge(Edge incomingEdge){
        incomingEdges.add(incomingEdge);
    }

    public void addOutgoingEdge(Edge outgoingEdge){
        outgoingEdges.add(outgoingEdge);
    }
    public void addIncomingVertex(Vertex incomingVertex){
        incomingVertices.add(incomingVertex);
    }

    public void addOutgoingVertex(Vertex outgoingVertex){
        outgoingVertices.add(outgoingVertex);
    }


    /* GETTERS & SETTERS */

    public String get_id() {
        return _id;
    }

    public ArrayList<Edge> getIncomingEdges() {
        return incomingEdges;
    }

    public ArrayList<Edge> getOutgoingEdges() {
        return outgoingEdges;
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
