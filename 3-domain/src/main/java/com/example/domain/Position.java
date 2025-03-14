package com.example.domain;

public class Position {
    private int xPos;
    private int yPos;

    public Position(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getxPos() {
        return xPos;
    }


    public int getyPos() {
        return yPos;
    }



    public boolean equals(Position position) {
        return this.xPos == position.getxPos() && this.yPos == position.getyPos();
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
        return "x: " + xPos + " y: " + yPos;
    }
}
