package com.example.application.playerMovement;

import com.example.domain.Direction;
import com.example.domain.Dungeon;
import com.example.domain.monster.Monster;
import com.example.domain.Player;
import com.example.domain.Position;

import java.util.List;

public class PlayerMovement {
    private Dungeon dungeon;
    private List<Monster> monsters;
    private Player player;

    public PlayerMovement(Player player, Dungeon dungeon, List<Monster> monsters) {
        this.dungeon = dungeon;
        this.monsters = monsters;
        this.player = player;
    }

    public void moveInDirection(Direction direction){
        Position newPosition = player.getPosition().getAdjacentPosition(direction);
        if(dungeon.isWalkable(newPosition)){
            player.move(newPosition);
        }
        if(dungeon.getRoomForPosition(newPosition) == null){
            player.setRoomNumber(-1);
        }else if(dungeon.getRoomForPosition(newPosition).getRoomNumber() != player.getRoomNumber()){
            player.setRoomNumber(dungeon.getRoomForPosition(newPosition).getRoomNumber());
        }
    }
}
