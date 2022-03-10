package com.gumisie.containers;

public class Difficulty {
    private int rows;
    private int columns;
    private int colors;
    private int intValue;

    public Difficulty(int rows, int columns, int colors, int intValue){
        this.rows = rows;
        this.columns = columns;
        this.colors = colors;
        this.intValue = intValue;
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public int getColors() {
        return colors;
    }

    public int toInt() {
        return intValue;
    }

    public static Difficulty fromInt(int intValue){
        if (intValue == 0){
            return new Difficulty(12, 4, 4, intValue);
        } else if (intValue == 1) {
            return new Difficulty(11, 5, 6, intValue);
        } else if (intValue == 2) {
            return new Difficulty(10, 6, 8, intValue);
        } else {
            throw new RuntimeException("No difficulty defined for intValue " + intValue);
        }
    }

}
