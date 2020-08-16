package org.example.constructionsitesimulator;

public class Bulldozer {
    private final int x;
    private final int y;
    private final Direction facing;
    private final int fuelUnit;
    private final int damageNum;
    private final boolean treePenalty;


    public Bulldozer() {
        this.x = -1;
        this.y = 0;
        this.facing = Direction.East;
        this.damageNum = 0;
        this.fuelUnit = 0;
        this.treePenalty = false;
    }
    protected Bulldozer(int x, int y, Direction facing, final int fuelUnit, final int damageNum, final boolean treePenalty) {
        this.x = x;
        this.y = y;
        this.facing = facing;
        this.damageNum = damageNum;
        this.fuelUnit = fuelUnit;
        this.treePenalty = treePenalty;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Direction getFacing() { return facing; }

    public int getFuelUnit() {
        return fuelUnit;
    }

    public int getDamageNum() {
        return damageNum;
    }

    public boolean getPenaltyFlag() { return treePenalty; }

    protected Bulldozer turnLeft() {
        return updateDirection(facing.turnLeft());
    }

    protected Bulldozer turnRight() {
        return updateDirection(facing.turnRight());
    }

    protected Bulldozer moveForward() {
        switch (facing) {
            case East:
                return updatePlace(x + 1, y);
            case South:
                return updatePlace(x, y + 1);
            case West:
                return updatePlace(x - 1, y);
            case North:
                return updatePlace(x, y -1);
            default:
                return this;
        }
    }

    private Bulldozer updatePlace(int newX, int newY) {
        return new Bulldozer(newX, newY, facing, fuelUnit, damageNum, treePenalty);
    }

    private Bulldozer updateDirection(Direction newDirection) {
        return new Bulldozer(x, y, newDirection, fuelUnit, damageNum, treePenalty);

    }

    protected Bulldozer updateUsage(int moreFuel, int moreDamage) {
        return new Bulldozer(x, y, facing, fuelUnit + moreFuel, damageNum + moreDamage, treePenalty);
    }

    protected Bulldozer flagPenalty() {
        return new Bulldozer(x, y, facing, fuelUnit, damageNum, true);

    }

}
