package model;

import interfaces.INode;
import interfaces.ISignalFlowGraph;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class SignalFlowGraph implements ISignalFlowGraph {

    INode start, end;

    private List<Path> paths = new ArrayList<>();
    private List<Loop> loops = new ArrayList<>();
    private List<INode> nodes = new ArrayList<>();

    private int[][] loopsAdjMatrix ;
    SubGraph loopsGraph = new SubGraph();

    public List<INode> getNodes() {
        return nodes;
    }


    /**
     * used to calculate loops and paths.
     * it is important to use it before getting loops or paths
     */

    public void update() {
        NodeVisitor nodeVisitor = new NodeVisitor(this.end);
        start.acceptVisitor(nodeVisitor);
        paths = nodeVisitor.getPaths();
        loops = nodeVisitor.getLoops();
        loopsAdjMatrix = new int[loops.size()][loops.size()];
    }

    /**
     * remember to update first
     *
     * @return list of all paths
     */
    @Override
    public List<Path> getPaths() {
        return paths;
    }

    /**
     * remember to update first
     *
     * @return list of all loops
     */
    @Override
    public List<Loop> getLoops() {
        return loops;
    }

    /*
     * @return list of all Groups of n non touching loops
     */
    @Override
    public HashMap<Integer, NonTouchingLoop> getNonTouchingLoops() {
        int i = 2;
        HashMap<Integer, NonTouchingLoop> nonTouchingLoopHashMap = new HashMap<>();
        NonTouchingLoop nonTouchingLoop = getTwoNonTouchingLoops();
        while (nonTouchingLoop != null) {
            nonTouchingLoop = new NonTouchingLoop(i) ;
            nonTouchingLoopHashMap.put(i,nonTouchingLoop);
            i++;
            //nonTouchingLoop = getNofNonTouchingLoops(i, nonTouchingLoopHashMap.get(i-1).getNonTouchingLoops());
        }
        return nonTouchingLoopHashMap;
    }

    public HashMap<Integer,NonTouchingLoop> getNofNonTouchingLoops() {
        NonTouchingLoop nonTouchingLoop = getTwoNonTouchingLoops();
        HashMap<Integer, NonTouchingLoop> nonTouchingLoopHashMap = new HashMap<>();
        nonTouchingLoopHashMap.put(2,nonTouchingLoop);
        nonTouchingLoopHashMap.put(3,new NonTouchingLoop(3));

        SignalFlowGraph signalFlowGraph = constructGraph();
        System.out.println(checkCompleteGraph(signalFlowGraph));
        /*signalFlowGraph.setStart(loopsGraph.getNodes().get(0));
        signalFlowGraph.setEnd(loopsGraph.getNodes().get(getNodes().size()-1));
        List<Loop> tempList;
        System.out.println(Arrays.deepToString(loopsAdjMatrix));
        System.out.println(loopsGraph.getNodes().size());*/
        signalFlowGraph.update();
        List<Loop> graphLoops = signalFlowGraph.getLoops();
        for (Loop l: graphLoops)
        {
            for (INode n :l.getNodes())
            {
                System.out.print(" " + n.getId());
            }
            System.out.println();
        }
        System.out.println("graphhhhh" + graphLoops.size());

       /* for(Loop loop : graphLoops){
            for (INode node : loop.getNodes())
                System.out.println(node.getID());
            if(loop.getNodes().size() == 3){
                tempList = new ArrayList<>();
                for (INode node : loop.getNodes())
                {
                    tempList.add(this.loops.get(node.getID()));
                }
                nonTouchingLoopHashMap.get(3).addCombination(tempList);
            }
            else if (loop.getNodes().size() > 3){

            }
        }*/
        return nonTouchingLoopHashMap;
    }
    private boolean completeGraphCheck(Loop loop)
    {
        List<INode> nodes = loop.getNodes();
        for (int i = 0 ; i < nodes.size() ; i++){
            if(nodes.get(i).getOutEdges().size() < loop.getNodes().size() - 1 )
                return false;
            for (int j = i + 1 ; j < nodes.size() ; j ++){
                if(loopsAdjMatrix[nodes.get(i).getId()][nodes.get(j).getId()] == 0)
                    return false;
            }
        }
        return true;
    }

    private boolean checkCompleteGraph(SignalFlowGraph sfg)
    {
        List<INode> nodes=sfg.getNodes();
        for (INode node : nodes)
        {
            if(node.getOutEdges().size()!=nodes.size()-1)
                return false;
        }
        return true;


    }

    /*
     * @return Group of n non Touching loops
     */
//    private NonTouchingLoop getNofNonTouchingLoops(int n ,List<List<Loop>> loops){
//        NonTouchingLoop nonTouchingLoop = new NonTouchingLoop(n);
//
//        for(int i = 0 ; i < loops.size() ; i++)
//        {
//            for(int j = i + 1 ; j < loops.size() ; j++ )
//            {
//                List<List<Loop>> output = loopGroupIntersection(loops.get(i),loops.get(j),n);
//                for(List<Loop> list : output){
//                    nonTouchingLoop.addCombination(list);
//                }
//            }
//
//        }
//        return nonTouchingLoop;
//    }

    /**
     * Method to detect Intersection between two lists of loops
     * and create combinations of loops
     * @return List of non touching loops combinations
     */
    private List<List<Loop>> loopGroupIntersection(List<Loop> loops1, List<Loop> loops2, int n){
        int count;
        List<List<Loop>> nonTouchingGroup = new ArrayList<>();
        for(Loop loop : loops1){
            count = 0;
            List<Loop> tempList = new ArrayList<>();
            tempList.add(loop);
            for (Loop loop1 : loops2){
                if( loopsAdjMatrix[loop.getId()][loop1.getId()] == 1 ){
                    count++;
                    tempList.add(loop1);
                }
            }
            if (count == n) {
                nonTouchingGroup.add(tempList);
            }
        }
        return nonTouchingGroup;
    }

    /**
     * Method to detect all two non touching loops
     */

    public NonTouchingLoop getTwoNonTouchingLoops() {
        NonTouchingLoop groupOfTwo = new NonTouchingLoop(2);
        List<Loop> loopsGroup;

        for(int i = 0 ; i < nodes.size() ; i++){
            INode node = new Node(i);
            loopsGraph.addNode(node);
        }
        int id = 0;
        for (int i = 0 ; i < loops.size(); i++){
            loops.get(i).setId(i);
            for (int j = i + 1 ; j < loops.size() ; j++)
            {
                if(!loopIntersectionCheck(loops.get(i),loops.get(j))){
                    loopsGroup = new ArrayList<>();
                    loopsGroup.add(loops.get(i));
                    loopsGroup.add(loops.get(j));
                    groupOfTwo.addCombination(loopsGroup);
                    //Add Edge between two non-touching loops
                    loopsAdjMatrix[i][j] = 1;
                    loopsAdjMatrix[j][i]=1;
                    new Edge(id , nodes.get(i),nodes.get(j),1);
                    new Edge(id + 1 , nodes.get(j),nodes.get(i),1);
                    id++;
                }
            }
        }
        return groupOfTwo;
    }

    public SignalFlowGraph constructGraph(){
        List<INode> newNodes=new ArrayList<>();
        for (int i=0;i< loopsAdjMatrix.length;i++)
            newNodes.add(new Node(i));
        for (int i=0;i<loopsAdjMatrix.length;i++)
        {

            for(int j=0;j< loopsAdjMatrix.length;j++)
            {
                if(loopsAdjMatrix[i][j]==1)
                {
                    new Edge(i,newNodes.get(i),newNodes.get(j),i);

                }

            }
        }
        SignalFlowGraph sgf=new SignalFlowGraph();
        sgf.setStart(newNodes.get(0));
        sgf.setEnd(newNodes.get(newNodes.size()-1));

        return sgf;

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
//        int size = loops.size();
//        double[][] loopsIntersections = new double[size][size];
//
//        List<Integer>[] nodes = new List[this.nodes.size()];
//        for (int i = 0 ; i < nodes.length ; i++){
//            nodes[i] = new ArrayList<>();
//        }
//        HashMap<Integer,Loop> loopsId = new HashMap<>();
//        int id = 0;
//        for(Loop loop : this.loops)
//        {
//            loopsId.put(id,loop);
//            for (INode node : loop.getNodes())
//            {
//                List<Integer> list = nodes[node.getId()];
//                if ( list.size() > 0)
//                {
//                    for(int i = 0 ; i < list.size() ;i++)
//                    {
//                        loopsIntersections[id][list.get(i)] = 1;
//                        loopsIntersections[list.get(i)][id] = 1;
//                    }
//                }
//                nodes[node.getId()].add(id);
//            }
//            id++;
//        }
//        NonTouchingLoop nonTouchingLoop = new NonTouchingLoop(2);
//        SubGraph graph = new SubGraph();
//        for(int i = 0 ; i < size ; i++){
//            INode node = new Node();
//            node.setId(i);
//            graph.addNode(node);
//        }
//        for (int i = 0 ; i < size ; i++){
//            for (int j = 0 ; j < size ; j++){
//                if( i < j && loopsIntersections[i][j] == 0){
//                    new Edge(graph.getNodes().get(i),graph.getNodes().get(j),1);
//                    new Edge(graph.getNodes().get(j),graph.getNodes().get(i),1);
//                    List<Loop> combination = new ArrayList<>();
//                    combination.add(loopsId.get(i));
//                    combination.add(loopsId.get(j));
//                    nonTouchingLoop.addCombination(combination);
//                }
//            }
//        }
//        SignalFlowGraph signalFlowGraph = new SignalFlowGraph();
//        signalFlowGraph.set();
//        return nonTouchingLoop;
//    }

    @Override
    public void addNode(INode node) {
        this.nodes.add(node);

    }


    public INode getStart() {
        return start;
    }

    public INode getEnd() {
        return end;
    }

    @Override
    public void setStart(INode start) {
        this.start = start;
    }

    @Override
    public void setEnd(INode end) {
        this.end = end;
    }

    @Override
    public void setNodes(List<INode> nodes) {
        this.nodes = nodes;
    }
    @Override
    public void calculatePathsNLoops() {

    }

}
