package model;

import interfaces.INode;

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

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (List<Loop> listOfLoops: nonTouchingLoops) {
            stringBuilder.append("{");
            for (Loop loop: listOfLoops) {
                stringBuilder.append("(");
                for (INode node : loop.getNodes()) {
                    stringBuilder.append("N").append(node.getId()).append(", ");
                }
                //remove last ", "
                stringBuilder.delete(stringBuilder.length() - 2, stringBuilder.length());
                stringBuilder.append(")");
            }
            stringBuilder.append("}");
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

}
