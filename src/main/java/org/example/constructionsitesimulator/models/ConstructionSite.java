package org.example.constructionsitesimulator.models;

import org.example.constructionsitesimulator.exceptions.OutOfMapException;
import org.example.constructionsitesimulator.exceptions.ProtectedTreePenaltyException;

import java.util.List;
import java.util.stream.Collectors;

public class ConstructionSite {
    private final List<List<SquareBlock>> constructionMap;
    private final int width;
    private final int height;
    private final Bulldozer bulldozer;


    public ConstructionSite(final List<List<SquareBlock>> constructionMap, final Bulldozer b) {
        this.constructionMap = constructionMap;
        this.width = constructionMap.get(0).size();
        this.height = constructionMap.size();
        this.bulldozer = b;
    }

    public Bulldozer getBulldozer() {
        return bulldozer;
    }

    public List<List<SquareBlock>> getConstructionMap() {
        return constructionMap;
    }

    public List<String> generateMap() {
        return constructionMap.stream()
                .map(row -> row.stream().map(SquareBlock::toString).collect(Collectors.joining(" ")))
                .collect(Collectors.toList());
    }

    public SquareBlock getTargetSquare(int x, int y) {
        return constructionMap.get(y).get(x);
    }

    public boolean outOfMap(int x, int y) {
        return x < 0 || x >= width || y < 0 || y >= height;
    }

    public ConstructionSite moveLeft() {
        return new ConstructionSite(constructionMap, bulldozer.turnLeft());
    }

    public ConstructionSite moveRight() {
        return new ConstructionSite(constructionMap, bulldozer.turnRight());
    }

    public ConstructionSite moveForward(int num) throws OutOfMapException, ProtectedTreePenaltyException {
        List<List<SquareBlock>> newGrids = constructionMap;
        Bulldozer newBulldozer = bulldozer;
        int moreFuelUnit;
        int moreDamage;
        for (int i = 0; i < num; i++) {
            Bulldozer nextB = newBulldozer.moveForward();
            if (outOfMap(nextB.getX(), nextB.getY())) {
                throw new OutOfMapException(new ConstructionSite(newGrids, newBulldozer));
            }
            SquareBlock nextGrid = getTargetSquare(nextB.getX(), nextB.getY());
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
        return (int) constructionMap.stream()
                .flatMap(row -> row.stream().filter(grid -> grid != SquareBlock.CLEARED_LAND && grid != SquareBlock.PROTECTED_TREE)).count();
    }
}
