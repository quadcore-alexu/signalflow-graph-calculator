package model;

import interfaces.IEdge;
import interfaces.INode;

public class Edge implements IEdge {

    private int id;
    private final INode end;
    private double gain;

    public Edge(int id, INode start, INode end, double gain) {
        this.id = id;
        start.addOutEdge(this);
        this.end = end;
        this.gain = gain;
    }

    @Override
    public int getId() {
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
    public INode getEndNode() {
        return end;
    }
}
