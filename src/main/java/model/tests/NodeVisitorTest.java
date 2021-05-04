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
        INode start = new Node(1);
        graph.setStart(start);
        INode second = new Node(2);
        INode third = new Node(3);
        INode fourth = new Node(4);
        graph.setEnd(fourth);

        new Edge(1, start, second, 5);
        new Edge(2, second, third, 6);
        new Edge(3, third, fourth, 8);
        new Edge(4, second, fourth, 10); //another path
        new Edge(5, fourth, second, 8); //loop
        new Edge(6, fourth, fourth, 8); //self loop
        new Edge(7, fourth, third, 8); //loop
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
