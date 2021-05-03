package model;

import interfaces.INode;
import interfaces.ISignalFlowGraph;

import java.util.List;

public class SignalFlowGraph implements ISignalFlowGraph {

    Node start;

    @Override
    public List<Path> getPaths() {
        NodeVisitor nodeVisitor = new NodeVisitor();
        nodeVisitor.visit(start);
        return nodeVisitor.getPaths();
    }

    @Override
    public void setStart(INode start) {
        this.start = ((Node) start);
    }

}
