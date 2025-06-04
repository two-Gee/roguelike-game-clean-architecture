package com.example.application.playerService;

import com.example.domain.Direction;
import com.example.domain.Dungeon;
import com.example.domain.Player;
import com.example.domain.Position;

public class PlayerMovement {
    private Dungeon dungeon;
    private Player player;

    public PlayerMovement(Player player, Dungeon dungeon) {
        this.dungeon = dungeon;
        this.player = player;
    }

    public void moveInDirection(Direction direction){
        Position newPosition = player.getPosition().getAdjacentPosition(direction);
        if(dungeon.isWalkable(newPosition)){
            player.move(newPosition);

            if(dungeon.getRoomForPosition(newPosition) == null){
                player.setRoomNumber(-1);
            }else if(dungeon.getRoomForPosition(newPosition).getRoomNumber() != player.getRoomNumber()){
                player.setRoomNumber(dungeon.getRoomForPosition(newPosition).getRoomNumber());
            }
        }
    }
}
