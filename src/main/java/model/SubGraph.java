package model;

import interfaces.IEdge;
import interfaces.INode;

import java.util.ArrayList;
import java.util.List;

public class SubGraph {

    private final List<INode> nodes = new ArrayList<>();
    private final List<IEdge> edges = new ArrayList<>();

    private double gain = 1;

    public void addNode(INode node) {
        nodes.add(node);
    }

    protected void addEdge(IEdge edge) {
        gain *= edge.getGain();
        edges.add(edge);
    }

    public double getGain() {
        return gain;
    }

    public List<INode> getNodes() {
        return nodes;
    }

    public List<IEdge> getEdges() {
        return edges;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (INode node : nodes) {
            stringBuilder.append("N").append(node.getId()).append(", ");
        }

        //remove last ", "
        stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());

        return stringBuilder.toString();
    }
}
