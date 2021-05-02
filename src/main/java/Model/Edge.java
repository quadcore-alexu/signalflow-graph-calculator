package Model;

import Interfaces.IEdge;
import Interfaces.INode;

public class Edge implements IEdge {

    private INode start, end;
    private double gain;


    @Override
    public double getGain() {
        return gain;
    }

    @Override
    public void setGain(double gain) {
        this.gain = gain;
    }

    @Override
    public void setStartNode(INode startNode) {
        this.start = startNode;
    }

    @Override
    public void setEndNode(INode endNode) {
        this.end = endNode;
    }

    @Override
    public INode getStartNode() {
        return start;
    }

    @Override
    public INode getEndNode() {
        return end;
    }
}
