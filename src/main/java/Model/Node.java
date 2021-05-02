package Model;

import Interfaces.IEdge;
import Interfaces.INode;

import java.util.ArrayList;
import java.util.List;

public class Node implements INode {

    private final List<IEdge> outEdges = new ArrayList<>();

    private int startTime, endTime;

    protected int getStartTime() {
        return startTime;
    }

    protected void setStartTime(int startTime) {
        this.startTime = startTime;
    }

    protected int getEndTime() {
        return endTime;
    }

    protected void setEndTime(int endTime) {
        this.endTime = endTime;
    }

    @Override
    public void addOutEdge(IEdge outEdge) {
        this.outEdges.add(outEdge);
    }

    @Override
    public List<IEdge> getOutEdges() {
        return this.outEdges;
    }

    protected void acceptVisitor(NodeVisitor visitor) {
        //todo
    }

}
