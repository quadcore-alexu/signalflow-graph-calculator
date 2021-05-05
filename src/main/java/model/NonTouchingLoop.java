package model;

import java.util.ArrayList;
import java.util.List;

public class NonTouchingLoop {
    List<List<Loop>> nonTouchingLoops;
    private final int n;

    public NonTouchingLoop(int n) {
        this.n = n;
        nonTouchingLoops = new ArrayList<>();
    }

    public void addCombination(List<Loop> combination) {
        nonTouchingLoops.add(combination);
    }

    public List<List<Loop>> getNonTouchingLoops() {
        return nonTouchingLoops;
    }


}
