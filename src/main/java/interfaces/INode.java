package interfaces;

import model.NodeVisitor;

import java.util.List;

public interface INode {
    int getId();
    void addOutEdge(IEdge outEdge);
    List<IEdge> getOutEdges();
    void acceptVisitor(NodeVisitor v);
    void setId(int id);
}
