package model.tests;

import interfaces.IEdge;
import interfaces.INode;
import interfaces.ISignalFlowGraph;
import model.Edge;
import model.Node;
import model.Path;
import model.SignalFlowGraph;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class NodeVisitorTest {

    static ISignalFlowGraph graph = new SignalFlowGraph();

    @BeforeAll
    static void setUp() {
        INode start = new Node();
        graph.setStart(start);
        INode second = new Node();
        INode third = new Node();
        INode fourth = new Node();

        IEdge e1 = new Edge(start, second, 5);
        IEdge e2 = new Edge(second, third, 6);
        IEdge e3 = new Edge(third, fourth, 8);
        IEdge e4 = new Edge(second, fourth, 10);
    }

    @Test
    void visit() {

        List<Path> paths = graph.getPaths();
        double sumOfGain = 0;

        for (Path path : paths) {
            sumOfGain += path.getGain();
        }

        assertEquals(sumOfGain, 240 + 50);
    }
}
