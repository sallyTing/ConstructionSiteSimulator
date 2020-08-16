package org.example.constructionsitesimulator.costs;

public class ProtectedTreePenalty extends CostItem {
    public ProtectedTreePenalty(final boolean penaltyFlag) {
        super(penaltyFlag ? 1 : 0, 10);
    }
}
