package model.tests;

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
        graph.setEnd(fourth);

        new Edge(start, second, 5);
        new Edge(second, third, 6);
        new Edge(third, fourth, 8);
        new Edge(second, fourth, 10); //another path
        new Edge(fourth, second, 8); //loop
        new Edge(fourth, fourth, 8); //self loop
        new Edge(fourth, third, 8); //loop
    }

    @Test
    void visit() {

        graph.update();
        List<Path> paths = graph.getPaths();
        double sumOfGain = 0;

        for (Path path : paths) {
            sumOfGain += path.getGain();
        }

        assertEquals(240 + 50, sumOfGain);
    }

    //todo add tests from sheet
}
