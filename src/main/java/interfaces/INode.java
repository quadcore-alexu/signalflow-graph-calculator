package interfaces;

import model.NodeVisitor;

import java.util.List;

public interface INode {
    void addOutEdge(IEdge outEdge);
    List<IEdge> getOutEdges();
    void acceptVisitor(NodeVisitor v);
}
