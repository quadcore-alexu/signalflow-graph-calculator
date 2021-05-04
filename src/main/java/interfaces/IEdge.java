package interfaces;

public interface IEdge {
    int getID();
    double getGain();
    void setGain(double gain);
    void setStartNode(INode startNode);
    void setEndNode(INode endNode);
    INode getEndNode();
}
