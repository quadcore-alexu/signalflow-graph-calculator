package model;

import java.util.ArrayList;
import java.util.List;

public class NonTouchingLoop {
    List<List<Loop>> nonTouchingLoops;

    public NonTouchingLoop() {
        nonTouchingLoops = new ArrayList<>();
    }

    public void addCombination(List<Loop> combination) {
        nonTouchingLoops.add(combination);
    }

    public List<List<Loop>> getNonTouchingLoops() {
        return nonTouchingLoops;
    }


}
