package interfaces;

import model.Path;

import java.util.HashMap;

public interface IDeltaCalculator {
    double getDelta();
    double getDelta(int i);
    double getTransferFunction();
    HashMap<Path , Double> getDeltas();
}
