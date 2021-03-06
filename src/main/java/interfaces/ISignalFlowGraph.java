package interfaces;

import model.Loop;
import model.NonTouchingLoop;
import model.Path;

import java.util.HashMap;
import java.util.List;

public interface ISignalFlowGraph {

    void addNode(INode node);

    void setStart(INode start);

    void setEnd(INode end);

    INode getStart();

    INode getEnd();

    List<INode> getNodes();

    void calculatePathsNLoops();

    List<Path> getPaths();

    List<Loop> getLoops();

    HashMap<Integer, NonTouchingLoop> getNonTouchingLoops();

    void setNodes(List<INode> nodes);
}
