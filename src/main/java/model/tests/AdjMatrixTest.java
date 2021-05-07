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
    static DeltaCalculator graphCalculator;

    @BeforeAll
    static void setUp() {
        List<INode> nodes = new ArrayList<>();
        INode first = new Node(0);
        INode second = new Node(1);
        INode third = new Node(2);
        INode fourth = new Node(3);
        new Edge(0, first, second, 1);
        new Edge(1, second, third, 2);
        new Edge(2, third, fourth, 3);
        new Edge(3, second, first, -5);
        new Edge(4, fourth, first, -6);
        new Edge(5, fourth, third, -4);
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
        graphCalculator = new DeltaCalculator(nodes, paths);


    }


    @Test
    void getAdjMatrix() {

        graphCalculator.constructAugmentedAdjMatrix();
        arr = graphCalculator.getAugmentedAdjMatrix().getArray();
        double[] a1 = {1, 5, 0, 6};
        double[] a2 = {-1, 1, 0, 0};
        double[] a3 = {0, -2, 1, 4};
        double[] a4 = {0, 0, -3, 1};

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

        INode y1 = new Node(0);
        INode y2 = new Node(1);
        INode y3 = new Node(2);
        INode y4 = new Node(3);
        INode y5 = new Node(4);
        INode y6 = new Node(5);
        INode y7 = new Node(6);
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

        new Edge(0, y1, y2, 1);
        new Edge(1, y2, y3, 5);
        new Edge(2, y3, y4, 10);
        new Edge(3, y4, y5, 2);
        new Edge(4, y5, y2, -1);
        new Edge(5, y5, y4, -2);
        new Edge(6, y4, y3, -1);
        new Edge(7, y2, y7, 10);
        new Edge(8, y7, y5, 2);
        new Edge(9, y5, y6, 1);
        new Edge(10, y7, y7, -1);

        graph.calculatePathsNLoops();

        List<Path> paths = graph.getPaths();
        assertEquals(2, paths.size());
        double[][] a = {{1, 0, 0, 0, 0, 0, 0}, {-1, 1, 0, 0, 1, 0, 0}, {0, -5, 1, 1, 0, 0, 0}, {0, 0, -10, 1, 2, 0, 0},
                {0, 0, 0, -2, 1, 0, -2}, {0, 0, 0, 0, -1, 1, 0}, {0, -10, 0, 0, 0, 0, 2}, {0, -10, 0, 0, 0, 0, 2}};
        DeltaCalculator g2 = new DeltaCalculator(nodes, graph.getPaths());
        double[][] arr = g2.constructAugmentedAdjMatrix().getArray();

        for (int i = 0; i < arr.length; i++)
            assertArrayEquals(a[i], arr[i]);
        assertEquals(450, (int) g2.getDelta());
        //int sliceIndices[] = {2, 3};
        //assertArrayEquals(sliceIndices,g2.removeForwardPath(paths.get(1)));
        assertEquals(11, (int) g2.getDelta(1));
        //assertArrayEquals(new int[] {6},g2.removeForwardPath(paths.get(0)));
        assertEquals(2, (int) g2.getDelta(0));


    }

}
