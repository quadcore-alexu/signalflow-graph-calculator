package interfaces;

import model.Loop;
import model.NonTouchingLoop;
import model.Path;

import java.util.HashMap;
import java.util.List;

public interface IGraphCalculator {
    double getDelta();
    double getDelta(int i);
    double getTransferFunction();
    HashMap<Path , Double> getDeltas();
}
