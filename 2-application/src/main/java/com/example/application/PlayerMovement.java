package com.example.application;

import com.example.domain.Direction;
import com.example.domain.Dungeon;
import com.example.domain.Monster.Monster;
import com.example.domain.Player;
import com.example.domain.Position;
import com.example.domain.map.DungeonRoom;

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
        Position newPosition = null;
        switch (direction){
            case NORTH:
                newPosition = new Position(player.getPosition().getxPos(), player.getPosition().getyPos() - 1);
                break;
            case SOUTH:
                newPosition = new Position(player.getPosition().getxPos(), player.getPosition().getyPos() + 1);
                break;
            case EAST:
                newPosition = new Position(player.getPosition().getxPos() + 1, player.getPosition().getyPos());
                break;
            case WEST:
                newPosition = new Position(player.getPosition().getxPos() - 1, player.getPosition().getyPos());
                break;
        }

            if(dungeon.isWalkable(newPosition)){
                for(Monster monster : monsters){
                    if (monster.isAtPosition(newPosition)){
                        player.attack(monster);
                        return;
                    }
                }
                player.move(newPosition);
            }

        }


}
