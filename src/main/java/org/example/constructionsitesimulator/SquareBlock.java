package org.example.constructionsitesimulator;

public enum SquareBlock {
    PLAIN_LAND("o"),
    ROCKY_GROUND("r"),
    TREE("t"),
    PROTECTED_TREE("T"),
    CLEARED_LAND("c");
    private final String marker;

    SquareBlock(String marker) {

        this.marker = marker;
    };

    @Override
    public String toString() {
        return marker;
    }

    public static SquareBlock ofMarker(String marker) throws IllegalArgumentException {
        for (SquareBlock block: values()) {
            if (block.marker.equals(marker))
                return block;
        }
        throw new IllegalArgumentException(String.format("Invalid square block: '%s'", marker));
    }

}
