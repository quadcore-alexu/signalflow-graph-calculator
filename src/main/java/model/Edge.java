package model;

import interfaces.IEdge;
import interfaces.INode;

public class Edge implements IEdge {

    private int id;
    private INode end;
    private double gain;

    public Edge(int id, INode start, INode end, double gain) {
        this.id = id;
        start.addOutEdge(this);
        this.end = end;
        this.gain = gain;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public double getGain() {
        return gain;
    }

    @Override
    public void setGain(double gain) {
        this.gain = gain;
    }

    @Override
    public void setStartNode(INode start) {
        start.addOutEdge(this);
    }

    @Override
    public void setEndNode(INode end) {
        this.end = end;
    }


    @Override
    public INode getEndNode() {
        return end;
    }
}
