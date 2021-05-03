package model;

import interfaces.IEdge;
import interfaces.INode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NodeVisitor {

    INode dest;

    List<Path> paths = new ArrayList<>(); //list to hold all forward paths
    List<Loop> loops = new ArrayList<>(); //list to hold all forward paths

    Map<INode, Boolean> flags = new HashMap<>();
    List<INode> currentNodes = new ArrayList<>(); //list to hold current nodes on which we passed (open nodes)
    List<IEdge> currentEdges = new ArrayList<>(); //same as above but for edges, to get gain

    public NodeVisitor(INode dest) {
        this.dest = dest;
    }

    /**
     * used to create a new path by giving it all the current nodes and edges and adding it to paths
     */
    private void createPath() {
        Path path = new Path();

        //add all nodes in path
        for (INode pathNode : this.currentNodes) {
            path.addNode(pathNode);
        }

        //add all edges in path
        for (IEdge pathEdge : this.currentEdges) {
            path.addEdge(pathEdge);
        }

        paths.add(path);
    }

    private void createLoop(INode endNode) {
        Loop loop = new Loop();
        //todo
        loops.add(loop);
    }

    /**
     * main method for getting all forward paths
     *
     * @param node to visit
     */
    protected void visit(Node node) {

        boolean firstTime = false; //assume not first time to visit node

        if (!flags.containsKey(node)) { //if it is first time
            firstTime = true;
            flags.put(node, true); //open node
        } else flags.replace(node, true); //open node

        currentNodes.add(node); //add to current path

        if (node.equals(dest)) createPath(); //this is the output node. i.e. end of path

        for (IEdge outEdge : node.getOutEdges()) { //loop on children

            if (!isOpen(outEdge.getEndNode())) { //if node is open, avoid cycle
                currentEdges.add(outEdge); //add edge to path
                outEdge.getEndNode().acceptVisitor(this); //recursively vis+it the child
            } else if (firstTime) { //open and for the first time
                createLoop(outEdge.getEndNode()); //create new loop
            }

        }


        flags.replace(node, false); //close node
        removeLastEdge(); //remove edge from current path
        removeLastNode(); //remove node from current path
    }

    /**
     * used when closing a node
     */
    private void removeLastNode() {
        if (currentNodes.size() > 0)
            currentNodes.remove(currentNodes.size() - 1);
    }

    /**
     * used when closing a node
     */
    private void removeLastEdge() {
        if (currentEdges.size() > 0)
            currentEdges.remove(currentEdges.size() - 1);
    }

    protected List<Path> getPaths() {
        return paths;
    }

    protected List<Loop> getLoops() {
        return loops;
    }

    private boolean isOpen(INode node) {
        if (flags.containsKey(node))
            return flags.get(node);
        return false; //first time
    }

}
