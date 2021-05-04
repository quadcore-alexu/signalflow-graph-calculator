package model.tests;

import interfaces.INode;
import interfaces.ISignalFlowGraph;
import model.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;


class AdjMatrixTest {
    static double[][] arr;
    static GraphCalculator graphCalculator;

    @BeforeAll
    static void setUp() {
        List<INode> nodes = new ArrayList<>();
        INode first = new Node();
        first.setId(0);
        INode second = new Node();
        second.setId(1);
        INode third = new Node();
        third.setId(2);
        INode fourth = new Node();
        fourth.setId(3);
        new Edge(first, second, 1);
        new Edge(second, third, 2);
        new Edge(third, fourth, 3);
        new Edge(second, first, -5);
        new Edge(fourth, first, -6);
        new Edge(fourth, third, -4);
        Path p1 = new Path();
        p1.addNode(first);
        p1.addNode(second);
        p1.addNode(third);
        p1.addNode(fourth);
        nodes.add(first);
        nodes.add(second);
        nodes.add(third);
        nodes.add(fourth);
        List<Path> paths = new ArrayList<>();
        paths.add(p1);
        graphCalculator = new GraphCalculator(nodes, paths);


    }


    @Test
    void getAdjMatrix() {

        graphCalculator.constructAugmentedAdjMatrix();
        arr = graphCalculator.getAugmentedAdjMatrix().getArray();
        double a1[] = {1, 5, 0, 6};
        double a2[] = {-1, 1, 0, 0};
        double a3[] = {0, -2, 1, 4};
        double a4[] = {0, 0, -3, 1};

        assertArrayEquals(a1, arr[0]);
        assertArrayEquals(a2, arr[1]);
        assertArrayEquals(a3, arr[2]);
        assertArrayEquals(a4, arr[3]);
        assertEquals(114, (int) graphCalculator.getDelta());
        assertEquals(1, graphCalculator.getDelta(0));
    }

    @Test
    void testDeltaI() {
        ISignalFlowGraph graph = new SignalFlowGraph();

        INode y1 = new Node();
        y1.setId(0);
        INode y2 = new Node();
        y2.setId(1);
        INode y3 = new Node();
        y3.setId(2);
        INode y4 = new Node();
        y4.setId(3);
        INode y5 = new Node();
        y5.setId(4);
        INode y6 = new Node();
        y6.setId(5);
        INode y7 = new Node();
        y7.setId(6);
        List<INode> nodes = new ArrayList<>();
        nodes.add(y1);
        nodes.add(y2);
        nodes.add(y3);
        nodes.add(y4);
        nodes.add(y5);
        nodes.add(y6);
        nodes.add(y7);

        graph.setStart(y1);
        graph.setEnd(y6);

        new Edge(y1, y2, 1);
        new Edge(y2, y3, 5);
        new Edge(y3, y4, 10);
        new Edge(y4, y5, 2);
        new Edge(y5, y2, -1);
        new Edge(y5, y4, -2);
        new Edge(y4, y3, -1);
        new Edge(y2, y7, 10);
        new Edge(y7, y5, 2);
        new Edge(y5, y6, 1);
        new Edge(y7, y7, -1);

        graph.update();

        List<Path> paths = graph.getPaths();
        assertEquals(2, paths.size());
        double[][] a = {{1, 0, 0, 0, 0, 0, 0}, {-1, 1, 0, 0, 1, 0, 0}, {0, -5, 1, 1, 0, 0, 0}, {0, 0, -10, 1, 2, 0, 0},
                {0, 0, 0, -2, 1, 0, -2}, {0, 0, 0, 0, -1, 1, 0}, {0, -10, 0, 0, 0, 0, 2}, {0, -10, 0, 0, 0, 0, 2}};
        GraphCalculator g2 = new GraphCalculator(nodes, graph.getPaths());
        double[][] arr = g2.constructAugmentedAdjMatrix().getArray();

        for (int i = 0; i < arr.length; i++)
            assertArrayEquals(a[i], arr[i]);
        assertEquals(450, (int) g2.getDelta());
        int sliceIndices[] = {2, 3};
        //assertArrayEquals(sliceIndices,g2.removeForwardPath(paths.get(1)));
        assertEquals(11, (int) g2.getDelta(1));
        //assertArrayEquals(new int[] {6},g2.removeForwardPath(paths.get(0)));
        assertEquals(2, (int) g2.getDelta(0));


    }

}
