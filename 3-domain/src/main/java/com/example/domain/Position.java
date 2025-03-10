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
}
