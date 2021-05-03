package interfaces;

import model.Path;

import java.util.List;

public interface ISignalFlowGraph {

    List<Path> getPaths();
    void setStart(INode start);
}
