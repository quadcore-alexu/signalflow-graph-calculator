package model;

import Jama.Matrix;
import interfaces.IEdge;
import interfaces.IGraphCalculator;
import interfaces.INode;

import java.util.*;

public class GraphCalculator implements IGraphCalculator {
    private final List<INode> nodes;
    private final List<Path> paths;
    private final HashMap<Path, Double> deltas = new HashMap<>();
    private Matrix augmentedAdjMatrix;

    public GraphCalculator(List<INode> nodes, List<Path> paths) {
        this.nodes = nodes;
        this.paths = paths;
        this.augmentedAdjMatrix = this.constructAugmentedAdjMatrix();
        computeDeltas();

    }
    //to do --> function to return list of delta i's

    /*
     DELTA = determinant of (I-AdjMatrix)
     */
    @Override
    public double getDelta() {
        return augmentedAdjMatrix.det();
    }


    private void computeDeltas() {
        int[] sliceIndices;
        Matrix delta;
        double value;
        for (Path path : paths) {
            sliceIndices = removeForwardPath(path);
            if (sliceIndices == null || sliceIndices.length == 0) value = 1;
            else {
                delta = augmentedAdjMatrix.getMatrix(sliceIndices, sliceIndices);
                value = delta.det();
            }
            deltas.put(path, value);
        }

    }
    /*
    for delta i's
    zero based index
     */

    /**
     *
     * @param i index
     *
     * @return value of delta i
     */

    @Override
    public double getDelta(int i) {

        int[] sliceIndices = removeForwardPath(paths.get(i));
        if (sliceIndices == null || sliceIndices.length == 0) return 1;
        Matrix delta = augmentedAdjMatrix.getMatrix(sliceIndices, sliceIndices);
        return delta.det();
    }

    @Override
    public double getTransferFunction() {
        Iterator<Map.Entry<Path, Double>> entrySet = deltas.entrySet().iterator();
        double numerator = 0;
        while (entrySet.hasNext()) {
            Map.Entry<Path, Double> entry = entrySet.next();
            numerator += entry.getKey().getGain() * entry.getValue();
        }
        double denominator = getDelta();
        return numerator / denominator;
    }

    /**
     * get all delta i's
     * @return hashmap between paths and their deltas
     */

    @Override
    public HashMap<Path, Double> getDeltas() {
        return deltas;
    }

    /*
    construct adjacency matrix (using out edges)
     */
    private Matrix constructAdjacencyMatrix() {
        int size = nodes.size();
        System.out.println(size);
        double[][] adjMatrix = new double[size][size];
        for (INode node : nodes) {
            int currentNodeId = node.getId();
            List<IEdge> edges = node.getOutEdges();
            for (IEdge edge : edges) {
                int endNodeID = edge.getEndNode().getId();
                adjMatrix[endNodeID][currentNodeId] = edge.getGain();
            }

        }
        System.out.println(Arrays.deepToString(adjMatrix));
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
