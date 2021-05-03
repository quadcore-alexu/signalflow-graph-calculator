package model.tests;
import Jama.Matrix;
import interfaces.INode;
import model.Edge;
import model.GraphCalculator;
import model.Node;
import model.Path;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class AdjMatrixTest {
    static double[][] arr;
    static GraphCalculator graphCalculator;
    @BeforeAll
    static void setUp() {
        List<INode> nodes=new ArrayList<>();
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
        new Edge(fourth, first, -6); //loop
        new Edge(fourth, third, -4); //loop
        nodes.add(first);
        nodes.add(second);
        nodes.add(third);
        nodes.add(fourth);
        //List<Path> paths = null;


        graphCalculator=new GraphCalculator(nodes,null);


    }

    @Test
    void getAdjMatrix() {

        graphCalculator.constructAugmentedAdjMatrix();
        arr=graphCalculator.getAugmentedAdjMatrix().getArray();
        double a1[]={1,5,0,6};
        double a2[]={-1,1,0,0};
        double a3[]={0,-2,1,4};
        double a4[]={0,0,-3,1};

        assertArrayEquals(a1,arr[0]);
        assertArrayEquals(a2,arr[1]);
        assertArrayEquals(a3,arr[2]);
        assertArrayEquals(a4,arr[3]);
        assertEquals(114,(int)graphCalculator.getDelta());
    }

}
