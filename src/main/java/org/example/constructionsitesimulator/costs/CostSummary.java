package org.example.constructionsitesimulator.costs;

import org.example.constructionsitesimulator.models.Simulator;

import java.util.List;

public class CostSummary {
    private final CostItem communicationOverhead;
    private final CostItem fuelUsage;
    private final CostItem unclearedSquaresCost;
    private final CostItem protectedTreePenalty;
    private final CostItem damageRepair;
    private final int totalCost;

    public CostSummary(Simulator simulator) {
        this.communicationOverhead = new CommunicationOverhead(simulator.getCommands());
        this.fuelUsage = new FuelUsage(simulator.getConstructionSite().getBulldozer().getFuelUnit());
        this.unclearedSquaresCost = new UnclearedSquareCost(simulator.getConstructionSite().getUnclearedSquares());
        this.protectedTreePenalty = new ProtectedTreePenalty(simulator.getConstructionSite().getBulldozer().getPenaltyFlag());
        this.damageRepair = new DamageRepair(simulator.getConstructionSite().getBulldozer().getDamageNum());
        this.totalCost = calculateTotalCost();
    }

    public CostItem getCommunicationOverhead() {
        return communicationOverhead;
    }

    public CostItem getFuelUsage() {
        return fuelUsage;
    }

    public CostItem getUnclearedSquaresCost() {
        return unclearedSquaresCost;
    }

    public CostItem getProtectedTreePenalty() {
        return protectedTreePenalty;
    }

    public CostItem getDamageRepair() {
        return damageRepair;
    }

    public int getTotalCost() {
        return totalCost;
    }

    private int calculateTotalCost() {
        return communicationOverhead.getCost() + fuelUsage.getCost() + unclearedSquaresCost.getCost() + protectedTreePenalty.getCost() + damageRepair.getCost();
    }
}
