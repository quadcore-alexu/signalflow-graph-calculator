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

    Map<INode, Flag> flags = new HashMap<>(); //map between node and its flags

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

    /**
     * used to create a new loop by giving it all the current nodes and edges and adding it to loops
     */
    private void createLoop(INode endNode) {
        Loop loop = new Loop();
        for (int i = currentNodes.indexOf(endNode); i < currentNodes.size(); i++) {
            loop.addNode(currentNodes.get(i));
            loop.addEdge(currentEdges.get(i));
        }
        loops.add(loop);
    }

    /**
     * main method for getting all forward paths
     *
     * @param node to visit
     */
    protected void visit(Node node) {

        //open node
        if (flags.containsKey(node)) flags.get(node).setOpen(true);
        else flags.put(node, new Flag());

        currentNodes.add(node); //add to current path

        if (node.equals(dest)) createPath(); //this is the output node. i.e. end of path

        //loop on children
        for (IEdge outEdge : node.getOutEdges()) {

            //enter only if child is not open, to avoid cycle
            if (!isOpen(outEdge.getEndNode())) {

                currentEdges.add(outEdge); //add edge to path
                outEdge.getEndNode().acceptVisitor(this); //recursively visit the child

            // if child is open and for the first time. i.e. was not closed before
            } else if (!wasClosed(outEdge.getEndNode())) {

                currentEdges.add(outEdge); //add edge to path to get loop
                createLoop(outEdge.getEndNode()); //create new loop
                removeLastEdge(); //remove edge after getting loop
            }

        } //END for loop


        flags.get(node).setOpen(false); //close node
        removeLastEdge(); //remove edge from current path
        removeLastNode(); //remove node from current path
    }

    protected List<Path> getPaths() {
        return paths;
    }

    protected List<Loop> getLoops() {
        return loops;
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

    /**
     * test if a certain node is currently open
     * @param node to test
     * @return true if node is open, false otherwise
     */
    private boolean isOpen(INode node) {
        if (flags.containsKey(node))
            return flags.get(node).isOpen();
        return false; //not yet visited
    }

    /**
     * test if a node was closed before at least once (complete visit). i.e. the node was opened then closed.
     * @param node to test
     * @return true if it was closed before, false otherwise
     */
    private boolean wasClosed(INode node) {
        if (flags.containsKey(node))
            return flags.get(node).wasClosed();
        return false; //not yet visited
    }

    /**
     * this class is to hold flags on each node
     */
    private static class Flag {
        private boolean isOpen = true;
        private boolean wasClosed = false;

        private boolean isOpen() {
            return isOpen;
        }

        private void setOpen(boolean open) {
            isOpen = open;
            if (!open) wasClosed = true; //set that this node was closed before
        }

        public boolean wasClosed() {
            return wasClosed;
        }
    }
}
