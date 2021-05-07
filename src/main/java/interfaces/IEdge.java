package interfaces;

public interface IEdge {
    int getId();
    double getGain();
    void setGain(double gain);
    INode getEndNode();
}
