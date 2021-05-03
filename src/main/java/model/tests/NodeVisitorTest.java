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

        INode start = new Node();
        INode second = new Node();
        INode third = new Node();
        INode fourth = new Node();

        graph.setStart(start);
        graph.setEnd(fourth);

        new Edge(start, second, 5);
        new Edge(second, third, 6);
        new Edge(third, fourth, 8);
        new Edge(second, fourth, 10); //another path
        new Edge(fourth, second, 8); //loop
        new Edge(fourth, fourth, 8); //self loop
        new Edge(fourth, third, 8); //loop


        graph.update();
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

        INode y1 = new Node();
        INode y2 = new Node();
        INode y3 = new Node();
        INode y4 = new Node();
        INode y5 = new Node();
        INode y6 = new Node();

        graph.setStart(y1);
        graph.setEnd(y5);

        new Edge(y1, y2, 1);
        new Edge(y2, y3, 5);
        new Edge(y3, y4, 10);
        new Edge(y4, y5, 2);
        new Edge(y5, y2, -1);
        new Edge(y5, y4, -2);
        new Edge(y4, y3, -1);
        new Edge(y2, y6, 10);
        new Edge(y6, y5, 2);
        new Edge(y6, y6, -1);

        graph.update();

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

}
