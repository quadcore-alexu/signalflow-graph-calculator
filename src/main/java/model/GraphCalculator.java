package model;

import Jama.Matrix;
import interfaces.IGraphCalculator;

import java.util.List;

public class GraphCalculator implements IGraphCalculator {
    private SubGraph subGraph;
    private List<Path> paths;
    private Matrix adjacencyMatrix;

    public GraphCalculator(SubGraph subGraph, List<Path> paths) {
        this.subGraph = subGraph;
        this.paths = paths;

    }


    @Override
    public double getDelta(int i) {
        return 0;
    }

    @Override
    public double getTransferFunction() {
        return 0;
    }
}
