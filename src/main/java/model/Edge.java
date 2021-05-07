package model;

import interfaces.IEdge;
import interfaces.INode;

public class Edge implements IEdge {

    private final INode end;
    private double gain;

    public Edge(INode start, INode end, double gain) {
        start.addOutEdge(this);
        this.end = end;
        this.gain = gain;
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
