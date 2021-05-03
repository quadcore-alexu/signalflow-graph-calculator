package model;

import interfaces.IEdge;
import interfaces.INode;

import java.util.ArrayList;
import java.util.List;

public class NodeVisitor {

    List<Path> paths = new ArrayList<>(); //list to hold all forward paths

    List<INode> currentNodes = new ArrayList<>(); //list to hold current nodes on which we passed (open nodes)
    List<IEdge> currentEdges = new ArrayList<>(); //same as above but for edges, to get gain

    /**
     * used when closing a node
     */
    private void removeLastNode() {
        if (this.currentNodes.size() > 0)
            this.currentNodes.remove(this.currentNodes.size() - 1);
    }

    /**
     * used when closing a node
     */
    private void removeLastEdge() {
        if (this.currentEdges.size() > 0)
            this.currentEdges.remove(this.currentEdges.size() - 1);
    }

    /**
     * main method for getting all forward paths
     *
     * @param node to visit
     */
    protected void visit(INode node) {

        ((Node) node).setOpen(true); //open node
        this.currentNodes.add(node); //add to current path

        if (node.getOutEdges().isEmpty()) { //this is the output node. i.e. end of path
            Path path = new Path();

            //add all nodes in path
            for (INode pathNode : this.currentNodes) {
                path.addNode(pathNode);
            }

            //add all edges in path
            for (IEdge pathEdge : this.currentEdges) {
                path.addEdge(pathEdge);
            }

            this.paths.add(path);

        } else { //not end of path

            for (IEdge outEdge : node.getOutEdges()) { //loop on children

                if (!((Node) outEdge.getEndNode()).isOpen()) { //to avoid cycle
                    this.currentEdges.add(outEdge); //add edge to path
                    this.visit(outEdge.getEndNode()); //recursively visit the child
                }

            }

        }

        ((Node) node).setOpen(false); //close node
        this.removeLastEdge(); //remove edge from current path
        this.removeLastNode(); //remove node from current path
    }

    protected List<Path> getPaths() {
        return paths;
    }
}
