package Interfaces;

import java.util.List;

public interface INode {
    public void addOutEdge(IEdge outEdge);
    public List<IEdge> getOutEdges();
}
