package interfaces;

import java.util.List;

public interface INode {
    void addOutEdge(IEdge outEdge);
    List<IEdge> getOutEdges();
}
