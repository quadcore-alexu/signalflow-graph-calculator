package model.tests;

import interfaces.IDeltaCalculator;
import interfaces.INode;
import model.Edge;
import model.DeltaCalculator;
import model.Node;
import model.SignalFlowGraph;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FinalTest {
    @Test
    void test1()
    {
        SignalFlowGraph graph=new SignalFlowGraph();

       INode n0=new Node(0);
        INode n1=new Node(1);
        INode n2=new Node(2);
        INode n3=new Node(3);
        INode n4=new Node(4);
        new Edge(0, n0,n1,1);
        new Edge(1, n1,n2,2);
        new Edge(2, n2,n1,-5);
        new Edge(3, n2,n3,3);
        new Edge(4, n3,n2,-2);
        new Edge(5, n3,n3,-1);
        new Edge(6, n3,n4,4);
        List<INode> nodes=new ArrayList<>();
        nodes.add(n0);
        nodes.add(n1);
        nodes.add(n2);
        nodes.add(n3);
        nodes.add(n4);



        graph.setStart(n0);
        graph.setEnd(n4);

        graph.calculatePathsNLoops();
        IDeltaCalculator c=new DeltaCalculator(nodes,graph.getPaths());
        assertEquals(1,c.getDelta(0));
        //assertEquals(2,c.getDelta(1));
        assertEquals(28, c.getDelta());
        //assertEquals((double)0.9829059829059829,c.getTransferFunction());
    }



}
