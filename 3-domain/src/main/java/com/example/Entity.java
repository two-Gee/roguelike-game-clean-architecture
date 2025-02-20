package com.example;

public class Entity{
    private Position position;

    public Entity() {
        this.position = new Position(0, 0);
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void move(Direction direction){
        switch(direction){
            case NORTH:
                this.position.setyPos(this.position.getyPos() - 1);
                break;
            case SOUTH:
                this.position.setyPos(this.position.getyPos() + 1);
                break;
            case EAST:
                this.position.setxPos(this.position.getxPos() + 1);
                break;
            case WEST:
                this.position.setxPos(this.position.getxPos() - 1);
                break;
        }
    }
}
