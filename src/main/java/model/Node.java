package model;

import interfaces.IEdge;
import interfaces.INode;

import java.util.ArrayList;
import java.util.List;

public class Node implements INode {

    private final List<IEdge> outEdges = new ArrayList<>();

    @Override
    public void addOutEdge(IEdge outEdge) {
        outEdges.add(outEdge);
    }

    @Override
    public List<IEdge> getOutEdges() {
        return outEdges;
    }

    @Override
    public void acceptVisitor(NodeVisitor v) {
        v.visit(this);
    }
}
