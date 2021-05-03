package model;

import interfaces.IEdge;
import interfaces.INode;

import java.util.ArrayList;
import java.util.List;

public class SubGraph {

    private final List<INode> nodes = new ArrayList<>();
    private final List<IEdge> edges = new ArrayList<>();

    public double getGain() {
        double gain = 1;
        for (IEdge edge : this.edges) {
            gain *= edge.getGain();
        }
        return gain;
    }

    protected void addNode(INode node) {
        this.nodes.add(node);
    }

    protected void addEdge(IEdge edge) {
        this.edges.add(edge);
    }

    public List<INode> getNodes() {
        return nodes;
    }
}
