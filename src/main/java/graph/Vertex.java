package graph;

import java.util.ArrayList;
import java.util.List;

public class Vertex {



    private String _id;
    private ArrayList<Edge> incomingEdges;
    private ArrayList<Edge> outgoingEdges;
    private ArrayList<Vertex> incomingVertices;
    private ArrayList<Vertex> outgoingVertices;
    private int _cost;



    public Vertex(String id, int cost){
       _id= id;
        incomingEdges= new ArrayList<Edge>();
       outgoingEdges= new ArrayList<Edge>();
       incomingVertices=  new ArrayList<Vertex>();
        outgoingVertices=  new ArrayList<Vertex>();
         _cost= cost;

    }

    public void addIncommingEdge(Edge incommingEdge){
        incomingEdges.add(incommingEdge);
    }

    public void addOutgoingEdge(Edge outgoingEdge){
        outgoingEdges.add(outgoingEdge);
    }
    public void addIncommingVertex(Vertex incommingVertex){
        incomingVertices.add(incommingVertex);
    }

    public void addOutgoingVertex(Vertex outgoingVertex){
    outgoingVertices.add(outgoingVertex);
    }

    //these are useful for input parsing
    private int _startTime;
    private int _processorNumber;




}
