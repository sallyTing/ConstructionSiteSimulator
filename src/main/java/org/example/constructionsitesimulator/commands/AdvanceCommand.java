package org.example.constructionsitesimulator.commands;

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
        return String.format("advance %d", num);
    }
}
