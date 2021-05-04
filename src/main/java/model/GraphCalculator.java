package model;

import Jama.Matrix;
import interfaces.IEdge;
import interfaces.IGraphCalculator;
import interfaces.INode;

import java.util.ArrayList;
import java.util.List;

public class GraphCalculator implements IGraphCalculator {
    private final List<INode> nodes;
    private final List<Path> paths;
    private Matrix augmentedAdjMatrix;

    public GraphCalculator(List<INode> nodes, List<Path> paths) {
        this.nodes = nodes;
        this.paths = paths;

    }
    //to do --> function to return list of delta i's

    /*
     DELTA = determinant of (I-AdjMatrix)
     */
    @Override
    public double getDelta() {
        return augmentedAdjMatrix.det();
    }
    /*
    for delta i's
    zero based index
     */

    @Override
    public double getDelta(int i) {
        int[] sliceIndices = removeForwardPath(paths.get(i));
        if (sliceIndices == null || sliceIndices.length == 0) return 1;
        Matrix m = augmentedAdjMatrix.getMatrix(sliceIndices, sliceIndices);
        return m.det();
    }

    @Override
    public double getTransferFunction() {
        return 0;
    }

    /*
    construct adjacency matrix (using out edges)
     */
    private Matrix constructAdjacencyMatrix() {
        int size = nodes.size();
        double[][] adjMatrix = new double[size][size];
        for (INode node : nodes) {
            int currentNodeId = node.getId();
            List<IEdge> edges = node.getOutEdges();
            for (IEdge edge : edges) {
                int endNodeID = edge.getEndNode().getId();
                adjMatrix[endNodeID][currentNodeId] = edge.getGain();
            }

        }
        return new Matrix(adjMatrix);
    }

    /*
      AugmentedAdjMatrix= I-AdjMatrix
     */
    //should be private later
    public Matrix constructAugmentedAdjMatrix() {
        Matrix adjacencyMatrix = constructAdjacencyMatrix();
        int dimension = adjacencyMatrix.getRowDimension();
        Matrix identityMatrix = Matrix.identity(dimension, dimension);
        augmentedAdjMatrix = identityMatrix.minus(adjacencyMatrix);
        return augmentedAdjMatrix;
    }

    public Matrix getAugmentedAdjMatrix() {
        return augmentedAdjMatrix;
    }

    private int[] removeForwardPath(Path path) {
        if (path.getNodes().size() == augmentedAdjMatrix.getRowDimension()) return null;

        int index = 0;
        List<Integer> nodesToRemove = new ArrayList<>();
        for (INode node : path.getNodes()) {
            nodesToRemove.add(node.getId());
        }
        System.out.println(nodesToRemove.size());
        int size = nodes.size() - nodesToRemove.size();
        int[] sliceIndices = new int[size];
        for (int i = 0; i < nodes.size(); i++) {
            if (!nodesToRemove.contains(i)) {
                sliceIndices[index++] = i;
            }
        }


        return sliceIndices;

    }

}
