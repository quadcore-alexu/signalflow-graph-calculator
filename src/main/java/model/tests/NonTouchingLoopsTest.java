package model.tests;
import interfaces.INode;
import model.*;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NonTouchingLoopsTest {
    SignalFlowGraph graph = new SignalFlowGraph();

    @Test
    void Test() {
        INode start = new Node(0);
        INode second = new Node(1);
        INode third = new Node(2);
        INode fourth = new Node(3);

        graph.setStart(start);
        graph.setEnd(fourth);

        List<INode> nodes = new ArrayList<>();
        nodes.add(start);
        nodes.add(second);
        nodes.add(third);
        nodes.add(fourth);
        graph.setNodes(nodes);

        new Edge(0, start, second, 5);
        new Edge(1, second, third, 6);
        new Edge(2, third, fourth, 8);
        new Edge(3, second, fourth, 10);
        new Edge(4, fourth, third, 8);
        new Edge(5, second, start, 6);


        graph.update();
        NonTouchingLoopsCalculator calculator = new NonTouchingLoopsCalculator(graph.getLoops());
        NonTouchingLoop nonTouchingLoop = calculator.getTwoNonTouchingLoops();
        List<List<Loop>> loops = nonTouchingLoop.getNonTouchingLoops();
        int sumOfGain = 0;
        for(List<Loop> loop : loops){
            sumOfGain += loop.get(0).getGain() + loop.get(1).getGain();
        }
        assertEquals(94, sumOfGain);
    }
    @Test
    void Test2() {

        INode start = new Node(0);
        INode second = new Node(1);
        INode third = new Node(2);
        INode fourth = new Node(3);
        INode fifth = new Node(4);
        INode sixth = new Node(5);
        INode seventh = new Node(6);

        graph.setStart(start);
        graph.setEnd(seventh);

        List<INode> nodes = new ArrayList<>();
        nodes.add(start);
        nodes.add(second);
        nodes.add(third);
        nodes.add(fourth);
        nodes.add(fifth);
        nodes.add(sixth);
        nodes.add(seventh);
        graph.setNodes(nodes);

        new Edge(0, start, second, 1);
        new Edge(1, second, seventh, 10);
        new Edge(2, second, third, 5);
        new Edge(3, third, fourth, 10);
        new Edge(4, third, second, -1);
        new Edge(5, fourth, fifth, 2);
        new Edge(6, fifth, fourth, -2);
        new Edge(7, fifth, second, -1);
        new Edge(8, fifth, sixth, 1);
        new Edge(9, seventh, seventh, -1);


        graph.update();
        HashMap<Integer,NonTouchingLoop> nonTouchingLoop = graph.getNonTouchingLoops();
        assertEquals(4, nonTouchingLoop.get(2).getNonTouchingLoops().size());
        assertEquals(1, nonTouchingLoop.get(3).getNonTouchingLoops().size());
    }

    @Test
    void Test3(){

        INode start = new Node(0);
        INode second = new Node(1);
        INode third = new Node(2);
        INode fourth = new Node(3);
        INode fifth = new Node(4);
        INode sixth = new Node(5);
        INode seventh = new Node(6);
        INode eighth  = new Node(7);
        INode ninth = new Node(8);

        graph.setStart(start);
        graph.setEnd(ninth);

        List<INode> nodes = new ArrayList<>();
        nodes.add(start);
        nodes.add(second);
        nodes.add(third);
        nodes.add(fourth);
        nodes.add(fifth);
        nodes.add(sixth);
        nodes.add(seventh);
        nodes.add(eighth);
        nodes.add(ninth);
        graph.setNodes(nodes);

        new Edge(0, start, second, 1);
        new Edge(1, second, eighth, 10);
        new Edge(2, second, third, 3);
        new Edge(3, second, start, -2);
        new Edge(4, third, fourth, 1);
        new Edge(5, fourth, fifth, 2);
        new Edge(6, fifth, fourth, -3);
        new Edge(13, third, third, -3);
        new Edge(8, fifth, sixth, 4);
        new Edge(9,sixth,seventh,5);
        new Edge(10, seventh, ninth, 1);

        new Edge(11, eighth, eighth, -1);
        new Edge(12,ninth,seventh,-5);
        new Edge(11, eighth, fifth, 2);
        graph.update();
        HashMap<Integer,NonTouchingLoop> nonTouchingLoop = graph.getNonTouchingLoops();
        assertEquals(10, nonTouchingLoop.get(2).getNonTouchingLoops().size());
        assertEquals(10, nonTouchingLoop.get(3).getNonTouchingLoops().size());
        assertEquals(5 , nonTouchingLoop.get(4).getNonTouchingLoops().size());
        assertEquals(1 , nonTouchingLoop.get(5).getNonTouchingLoops().size());
    }
}
