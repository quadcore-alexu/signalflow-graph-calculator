package interfaces;

import model.Loop;
import model.Path;

import java.util.List;

public interface ISignalFlowGraph {

    void setStart(INode start);

    void setEnd(INode end);

    void update();

    List<Path> getPaths();

    List<Loop> getLoops();
}
