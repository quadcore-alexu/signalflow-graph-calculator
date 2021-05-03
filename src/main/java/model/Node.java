package model;

import interfaces.IEdge;
import interfaces.INode;

import java.util.ArrayList;
import java.util.List;

public class Node implements INode {

    private final List<IEdge> outEdges = new ArrayList<>();
    private int id;

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

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }
}
