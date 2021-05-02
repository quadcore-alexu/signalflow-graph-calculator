package Model;

import java.util.ArrayList;
import java.util.List;

public class SubGraph {

    private final List<Node> nodes = new ArrayList<>();
    private final List<Edge> edges = new ArrayList<>();

    protected double getGain() {
        //todo
        return 0;
    }

    protected boolean touches(SubGraph other) {
        //todo
        return true;
    }

    protected void addNode(Node node) {
        this.nodes.add(node);
    }

    protected void addEdge(Edge edge) {
        this.edges.add(edge);
    }

}
