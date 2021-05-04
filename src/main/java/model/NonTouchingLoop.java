package model;

import java.util.ArrayList;
import java.util.List;

public class NonTouchingLoop {
    List<List<Loop>> nonTouchingLoops;
    List<Double> gains;
    private final int n;

    public NonTouchingLoop(int n) {
        this.n = n;
        nonTouchingLoops = new ArrayList<>();
    }

    public void addCombination(List<Loop> combination) {
        nonTouchingLoops.add(combination);
        addGain(combination);
    }

    public List<List<Loop>> getNonTouchingLoops() {
        return nonTouchingLoops;
    }

    private void addGain(List<Loop> combination) {

    }

}
