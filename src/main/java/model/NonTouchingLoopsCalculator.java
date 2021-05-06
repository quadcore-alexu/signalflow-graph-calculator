package model;

import interfaces.INode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NonTouchingLoopsCalculator
{
    private final List<Loop> loops;
    private final int[][] loopsAdjMatrix ;

    private HashMap<Long,List<Loop>> uniquenessCheck;

    public NonTouchingLoopsCalculator(List<Loop> loops){
        this.loops = loops;
        loopsAdjMatrix = new int[loops.size()][loops.size()];
    }

    /*
     * @return Group of non Touching loops with group size n
     */
    public NonTouchingLoop getNofNonTouchingLoops(int n ,List<List<Loop>> previousStateLoops){
        NonTouchingLoop nonTouchingLoop = new NonTouchingLoop(n);

        for (List<Loop> loop : previousStateLoops) {
            for (Loop value : this.loops) {
                loopGroupIntersection(loop, value, nonTouchingLoop);
            }
        }
        return nonTouchingLoop;
    }

    /**
     * Method to detect Intersection between two lists of loops
     * and create combinations of loops
     */
    private void loopGroupIntersection(List<Loop> loopGroup, Loop loop, NonTouchingLoop nonTouchingLoop){
        if(loopGroup.contains(loop))
            return;
        for (Loop loopOfGroup : loopGroup)
        {
            //Check Two loops touching
            if(loopsAdjMatrix[loop.getId()][loopOfGroup.getId()] == 0)
                return;
        }

        List<Loop> newCombination = new ArrayList<>();
        newCombination.add(loop);
        newCombination.addAll(loopGroup);

        long uniqueID = 0;
        for (Loop value : newCombination) {
            uniqueID += value.getUniqueId();
        }
        //Add Combination, if it doesn't exist
        if(uniquenessCheck.get(uniqueID) == null)
        {
            uniquenessCheck.put(uniqueID,newCombination);
            nonTouchingLoop.addCombination(newCombination);
        }
    }

    /**
     * Method to detect all two non touching loops
     */
    public NonTouchingLoop getTwoNonTouchingLoops() {
        NonTouchingLoop groupOfTwo = new NonTouchingLoop(2);
        List<Loop> loopsGroup;

        for (int i = 0 ; i < loops.size(); i++){
            loops.get(i).setId(i);
            for (int j = i + 1 ; j < loops.size() ; j++)
            {
                //Add new Group of two non touching loops if there exists no loops intersection
                if(!loopIntersectionCheck(loops.get(i),loops.get(j)))
                {
                    loopsGroup = new ArrayList<>();
                    loopsGroup.add(loops.get(i));
                    loopsGroup.add(loops.get(j));
                    groupOfTwo.addCombination(loopsGroup);
                    //Add 1 in adjacency matrix between two non-touching loops
                    loopsAdjMatrix[i][j] = 1;
                    loopsAdjMatrix[j][i]=1;
                }
            }
        }
        return groupOfTwo;
    }

    /**
     * Check whether there is intersection between two loops or not
     */
    private boolean loopIntersectionCheck(Loop loop1, Loop loop2){
        for (INode node1 : loop1.getNodes()) {
            for (INode node2 : loop2.getNodes()) {
                if (node1 == node2)
                    return true;
            }
        }
        return false;
    }

    public void setUniquenessCheck(HashMap<Long, List<Loop>> uniquenessCheck) {
        this.uniquenessCheck = uniquenessCheck;
    }
}
