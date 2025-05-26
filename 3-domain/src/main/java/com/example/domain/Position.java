package com.example.domain;

import java.util.Objects;

public final class Position {
    private final int xPos;
    private final int yPos;

    public Position(int xPos, int yPos) {
        if(xPos < 0 || yPos < 0) {
            throw new IllegalArgumentException("Position coordinates cannot be negative: " +
                    "xPos = " + xPos + ", yPos = " + yPos);
        }
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getxPos() {
        return this.xPos;
    }


    public int getyPos() {
        return this.yPos;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return getxPos() == position.getxPos() && getyPos() == position.getyPos();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getxPos(), getyPos());
    }

    public boolean isAdjacent(Position position) {
        return Math.abs(this.xPos - position.getxPos()) <= 1 && this.yPos == position.getyPos() ||
                Math.abs(this.yPos - position.getyPos()) <= 1 && this.xPos == position.getxPos();
    }

    public Position getAdjacentPosition(Direction direction){
        switch (direction){
            case NORTH:
                return new Position(this.xPos, this.yPos - 1);
            case SOUTH:
                return new Position(this.xPos, this.yPos + 1);
            case EAST:
                return new Position(this.xPos + 1, this.yPos);
            case WEST:
                return new Position(this.xPos - 1, this.yPos);
        }
        return null;
    }
    public String toString(){
        return "x: " + this.xPos + " y: " + this.yPos;
    }
}
