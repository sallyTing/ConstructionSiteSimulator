package org.example.constructionsitesimulator.costs;

public class CostItem {
    private final int quantity;
    private final int unitCost;

    public CostItem(final int quantity, final int unitCost) {
        this.quantity = quantity;
        this.unitCost = unitCost;
    }

    public int getQuantity() {
        return quantity;
    }

    public int getCost() {
        return quantity * unitCost;
    }
}
