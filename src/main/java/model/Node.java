package model;

import interfaces.IEdge;
import interfaces.INode;

import java.util.ArrayList;
import java.util.List;

public class Node implements INode {

    private final List<IEdge> outEdges = new ArrayList<>();

    private boolean isOpen = false;

    protected boolean isOpen() {
        return isOpen;
    }

    protected void setOpen(boolean open) {
        isOpen = open;
    }

    @Override
    public void addOutEdge(IEdge outEdge) {
        this.outEdges.add(outEdge);
    }

    @Override
    public List<IEdge> getOutEdges() {
        return this.outEdges;
    }

}
