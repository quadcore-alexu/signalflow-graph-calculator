package model.tests;

import interfaces.INode;
import interfaces.ISignalFlowGraph;
import model.*;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NodeVisitorTest {

    @Test
    void simpleTest() {
        ISignalFlowGraph graph = new SignalFlowGraph();

        INode start = new Node(1);
        INode second = new Node(2);
        INode third = new Node(3);
        INode fourth = new Node(4);

        graph.setStart(start);
        graph.setEnd(fourth);

        new Edge(0, start, second, 5);
        new Edge(1, second, third, 6);
        new Edge(2, third, fourth, 8);
        new Edge(3, second, fourth, 10); //another path
        new Edge(4, fourth, second, 8); //loop
        new Edge(5, fourth, fourth, 8); //self loop
        new Edge(6, fourth, third, 8); //loop


        graph.calculatePathsNLoops();
        List<Path> paths = graph.getPaths();
        double sumOfGain = 0;

        for (Path path : paths) {
            sumOfGain += path.getGain();
        }

        assertEquals(240 + 50, sumOfGain);
    }

    /*
    question 3 in control, sheet4
     */
    @Test
    void test1() {
        ISignalFlowGraph graph = new SignalFlowGraph();

        INode y1 = new Node(1);
        INode y2 = new Node(2);
        INode y3 = new Node(3);
        INode y4 = new Node(4);
        INode y5 = new Node(5);
        INode y6 = new Node(6);

        graph.setStart(y1);
        graph.setEnd(y5);

        new Edge(0, y1, y2, 1);
        new Edge(1, y2, y3, 5);
        new Edge(2, y3, y4, 10);
        new Edge(3, y4, y5, 2);
        new Edge(4, y5, y2, -1);
        new Edge(5, y5, y4, -2);
        new Edge(6, y4, y3, -1);
        new Edge(7, y2, y6, 10);
        new Edge(8, y6, y5, 2);
        new Edge(9, y6, y6, -1);

        graph.calculatePathsNLoops();

        List<Path> paths = graph.getPaths();
        List<Loop> loops = graph.getLoops();

        double sumOfPaths = 0;
        for (Path p : paths) {
            sumOfPaths += p.getGain();
        }
        assertEquals(120, sumOfPaths);

        double sumOfLoops = 0;
        for (Loop l : loops) {
            sumOfLoops += l.getGain();
        }
        assertEquals(-135, sumOfLoops);
    }

    @Test
    void test2() {
        ISignalFlowGraph graph = new SignalFlowGraph();

        INode n1 = new Node(1);
        INode n2 = new Node(2);
        INode n3 = new Node(3);

        graph.setStart(n1);
        graph.setEnd(n3);

        new Edge(0, n1, n2, 1);
        new Edge(1, n2, n1, 1);
        new Edge(2, n2, n3, 1);
        new Edge(3, n3, n2, 1);
        new Edge(4, n1, n3, 1);
        new Edge(5, n3, n1, 1);

        graph.calculatePathsNLoops();

        assertEquals(2, graph.getPaths().size());
        assertEquals(5, graph.getLoops().size());
    }
}
