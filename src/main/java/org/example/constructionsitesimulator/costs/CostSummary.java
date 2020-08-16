package org.example.constructionsitesimulator.costs;

import org.example.constructionsitesimulator.models.Simulator;

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

    public void printCostSummary() {
        System.out.println("The costs for this land clearing operation were:");
        System.out.println("Item                               Quantity      Cost");
        System.out.println(String.format("communication overhead                  %d       %d", communicationOverhead.getQuantity(), communicationOverhead.getCost()));
        System.out.println(String.format("fuel usage                              %d       %d", fuelUsage.getQuantity(), fuelUsage.getCost()));
        System.out.println(String.format("uncleared squares                       %d       %d", unclearedSquaresCost.getQuantity(), unclearedSquaresCost.getCost()));
        System.out.println(String.format("destruction of protected tree           %d       %d", protectedTreePenalty.getQuantity(), protectedTreePenalty.getCost()));
        System.out.println(String.format("paint damage to bulldozer               %d       %d", damageRepair.getQuantity(), damageRepair.getCost()));
        System.out.println("----");
        System.out.println(String.format("Total                                            %d", totalCost));
    }

    private int calculateTotalCost() {
        return communicationOverhead.getCost() + fuelUsage.getCost() + unclearedSquaresCost.getCost() + protectedTreePenalty.getCost() + damageRepair.getCost();
    }
}
