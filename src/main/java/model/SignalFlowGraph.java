package model;

import interfaces.INode;
import interfaces.ISignalFlowGraph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SignalFlowGraph implements ISignalFlowGraph {

    INode start, end;

    private List<Path> paths = new ArrayList<>();
    private List<Loop> loops = new ArrayList<>();
    private List<INode> nodes = new ArrayList<>();

    NonTouchingLoopsCalculator calculator ;

    public List<INode> getNodes() {
        return nodes;
    }

    /**
     * used to calculate loops and paths.
     * it is important to use it before getting loops or paths
     */

    public void update() {
        NodeVisitor nodeVisitor = new NodeVisitor(this.end);
        start.acceptVisitor(nodeVisitor);
        paths = nodeVisitor.getPaths();
        loops = nodeVisitor.getLoops();
        calculator = new NonTouchingLoopsCalculator(loops);
    }

    /**
     * remember to update first
     *
     * @return list of all paths
     */
    @Override
    public List<Path> getPaths() {
        return paths;
    }

    /**
     * remember to update first
     *
     * @return list of all loops
     */
    @Override
    public List<Loop> getLoops() {
        return loops;
    }

    /*
     * @return HashMap of all Groups of n non touching loops with key n
     */
    @Override
    public HashMap<Integer, NonTouchingLoop> getNonTouchingLoops() {
        int n = 2;
        HashMap<Integer, NonTouchingLoop> nonTouchingLoopHashMap = new HashMap<>();

        NonTouchingLoop nonTouchingLoop = calculator.getTwoNonTouchingLoops();

        while (nonTouchingLoop.getNonTouchingLoops().size() != 0)
        {
            nonTouchingLoopHashMap.put(n,nonTouchingLoop);
            n++;
            calculator.setUniquenessCheck(new HashMap<>());
            nonTouchingLoop = calculator.getNofNonTouchingLoops(n, nonTouchingLoopHashMap.get(n-1).getNonTouchingLoops());
        }
        return nonTouchingLoopHashMap;
    }


    @Override
    public void addNode(INode node) {
        this.nodes.add(node);
    }

    public INode getStart() {
        return start;
    }

    public INode getEnd() {
        return end;
    }

    @Override
    public void setStart(INode start) {
        this.start = start;
    }

    @Override
    public void setEnd(INode end) {
        this.end = end;
    }

    @Override
    public void setNodes(List<INode> nodes) {
        this.nodes = nodes;
    }
    @Override
    public void calculatePathsNLoops() {

    }

}
