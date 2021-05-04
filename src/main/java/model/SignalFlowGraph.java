package model;

import interfaces.INode;
import interfaces.ISignalFlowGraph;

import java.util.ArrayList;
import java.util.List;

public class SignalFlowGraph implements ISignalFlowGraph {

    INode start, end;

    private List<Path> paths = new ArrayList<>();
    private List<Loop> loops = new ArrayList<>();

    /**
     * used to calculate loops and paths.
     * it is important to use it before getting loops or paths
     */
    @Override
    public void calculatePathsNLoops() {
        NodeVisitor nodeVisitor = new NodeVisitor(this.end);
        start.acceptVisitor(nodeVisitor);
        paths = nodeVisitor.getPaths();
        loops = nodeVisitor.getLoops();
    }

    /**
     * remember to update first
     * @return list of all paths
     */
    @Override
    public List<Path> getPaths() {
        return paths;
    }

    /**
     * remember to update first
     * @return list of all loops
     */
    @Override
    public List<Loop> getLoops() {
        return loops;
    }

    @Override
    public void setStart(INode start) {
        this.start = start;
    }

    @Override
    public void setEnd(INode end) {
        this.end = end;
    }

}
