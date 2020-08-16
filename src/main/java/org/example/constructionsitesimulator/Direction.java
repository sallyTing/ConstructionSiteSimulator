package org.example.constructionsitesimulator;

public enum Direction {
    East {
        @Override
        Direction turnRight() {
            return South;
        }
        @Override
        Direction turnLeft() {
            return North;
        }
    },
    South {
        @Override
        Direction turnRight() {
            return West;
        }
        @Override
        Direction turnLeft() {
            return East;
        }
    },
    West {
        @Override
        Direction turnRight() {
            return North;
        }
        @Override
        Direction turnLeft() {
            return South;
        }
    },
    North {
        @Override
        Direction turnRight() {
            return East;
        }
        @Override
        Direction turnLeft() {
            return West;
        }
    };

    abstract Direction turnRight();
    abstract Direction turnLeft();
    }


