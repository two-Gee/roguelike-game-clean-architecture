package com.example.domain;

import java.util.Objects;

public final class Position {
    private final int X_POS;
    private final int Y_POS;

    public Position(int X_POS, int Y_POS) {
        if(X_POS < 0 || Y_POS < 0) {
            throw new IllegalArgumentException("Position coordinates cannot be negative: " +
                    "x = " + X_POS + ", y = " + Y_POS);
        }
        this.X_POS = X_POS;
        this.Y_POS = Y_POS;
    }

    public int getX_POS() {
        return this.X_POS;
    }


    public int getY_POS() {
        return this.Y_POS;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return getX_POS() == position.getX_POS() && getY_POS() == position.getY_POS();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getX_POS(), getY_POS());
    }

    public boolean isAdjacent(Position position) {
        return Math.abs(this.X_POS - position.getX_POS()) <= 1 && this.Y_POS == position.getY_POS() ||
                Math.abs(this.Y_POS - position.getY_POS()) <= 1 && this.X_POS == position.getX_POS();
    }

    public Position getAdjacentPosition(Direction direction){
        switch (direction){
            case NORTH:
                return new Position(this.X_POS, this.Y_POS - 1);
            case SOUTH:
                return new Position(this.X_POS, this.Y_POS + 1);
            case EAST:
                return new Position(this.X_POS + 1, this.Y_POS);
            case WEST:
                return new Position(this.X_POS - 1, this.Y_POS);
        }
        return null;
    }
    public String toString(){
        return "x: " + this.X_POS + " y: " + this.Y_POS;
    }
}
