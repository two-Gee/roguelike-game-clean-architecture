package com.example.application.playerService;

import com.example.application.DungeonRenderer;
import com.example.application.stores.MonsterStore;
import com.example.domain.Direction;
import com.example.domain.Dungeon;
import com.example.domain.Player;
import com.example.domain.Position;

public class PlayerService {

    private PlayerAttack playerAttack;
    private PlayerMovement playerMovement;

    public PlayerService(Player player, Dungeon dungeon, MonsterStore monsterStore, DungeonRenderer dungeonRenderer) {
        playerAttack = new PlayerAttack(player, monsterStore, dungeonRenderer);
        playerMovement = new PlayerMovement(player, dungeon);
    }

    public void playerAttackMonster(Position position) {
        playerAttack.attackMonster(position);
    }

    public void movePlayer(Direction direction) {
        playerMovement.moveInDirection(direction);
    }



}
