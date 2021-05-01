package Interfaces;

import java.util.List;

public interface INode {
    public int getTime();
    public void acceptVisitor();
    public void addOutEdge(IEdge outEdge);
    public List<IEdge> getOutEdges();
}
