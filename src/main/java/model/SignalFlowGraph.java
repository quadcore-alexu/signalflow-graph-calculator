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

    @Override
    public HashMap<Integer, NonTouchingLoop> getNonTouchingLoops() {
        return null;
    }

    @Override
    public void addNode(INode node) {
        this.nodes.add(node);

    }

    @Override
    public INode getStart() {
        return start;
    }

    @Override
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
    public void calculatePathsNLoops() {

    }

}
