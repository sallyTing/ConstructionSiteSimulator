package org.example.constructionsitesimulator.models;

import org.example.constructionsitesimulator.exceptions.OutOfMapException;
import org.example.constructionsitesimulator.exceptions.ProtectedTreePenaltyException;

import java.util.List;

public class ConstructionSite {
    private final List<List<SquareBlock>> map;
    private final int width;
    private final int height;
    private final Bulldozer bulldozer;


    public ConstructionSite(final List<List<SquareBlock>> map, final Bulldozer b) {
        this.map = map;
        this.width = map.get(0).size();
        this.height = map.size();
        this.bulldozer = b;
    }

    public Bulldozer getBulldozer() {
        return this.bulldozer;
    }

    public List<List<SquareBlock>> getMap() {
        return this.map;
    }


    public SquareBlock getTargetGrid(int x, int y) {
        return this.map.get(y).get(x);
    }

    public boolean outOfMap(int x, int y) {
        return x < 0 || x >= this.width || y < 0 || y >= this.height;
    }

    public void print() {
        for (List<SquareBlock> row : this.map) {
            for (SquareBlock cell : row) {
                System.out.print(cell.toString() + " ");
            }
            System.out.println("");
        }
    }

    public ConstructionSite moveLeft() {
        return new ConstructionSite(map, bulldozer.turnLeft());
    }

    public ConstructionSite moveRight() {
        return new ConstructionSite(map, bulldozer.turnRight());
    }

    public ConstructionSite moveForward(int num) throws OutOfMapException, ProtectedTreePenaltyException {
        List<List<SquareBlock>> newGrids = this.map;
        Bulldozer newBulldozer = this.bulldozer;
        int moreFuelUnit;
        int moreDamage;
        for (int i = 0; i < num; i++) {
            Bulldozer nextB = newBulldozer.moveForward();
            if (outOfMap(nextB.getX(), nextB.getY())) {
                throw new OutOfMapException(new ConstructionSite(newGrids, newBulldozer));
            }
            SquareBlock nextGrid = getTargetGrid(nextB.getX(), nextB.getY());
            if (nextGrid == SquareBlock.PROTECTED_TREE) {
                throw new ProtectedTreePenaltyException(new ConstructionSite(newGrids, newBulldozer.flagPenalty()));
            }

            newGrids.get(nextB.getY()).set(nextB.getX(), SquareBlock.CLEARED_LAND);

            moreFuelUnit = (nextGrid == SquareBlock.ROCKY_GROUND || nextGrid == SquareBlock.TREE) ? 2 : 1;
            moreDamage = (nextGrid == SquareBlock.TREE && i < num - 1) ? 1 : 0;
            newBulldozer = nextB.updateUsage(moreFuelUnit, moreDamage);
        }
        return new ConstructionSite(newGrids, newBulldozer);

    }

    public int getUnclearedSquares() {
        return (int) this.map.stream()
                .flatMap(row -> row.stream().filter(grid -> grid != SquareBlock.CLEARED_LAND && grid != SquareBlock.PROTECTED_TREE)).count();
    }
}
