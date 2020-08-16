package org.example.constructionsitesimulator;

public class AdvanceCommand implements UserCommand {
    private final int num;

    public AdvanceCommand(final int num) {
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    @Override
    public String toString() {
        return "advance " + this.num;
    }
}
