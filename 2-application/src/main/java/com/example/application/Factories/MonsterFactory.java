package com.example.application.Factories;

import com.example.domain.Monster.Monster;
import com.example.domain.Monster.MonsterTypes;

public class MonsterFactory {
    public static Monster createMonster(MonsterTypes type){
        return switch (type) {
            case GOBLIN -> new Monster("Goblin", 10, 5, 5);
            case ORC -> new Monster("Orc", 20, 10, 5);
            case TROLL -> new Monster("Troll", 15, 8, 5);
            default -> throw new IllegalArgumentException();
        };
    }

    public static Monster createMonsterRandom(){
        int random = (int)(Math.random() * MonsterTypes.values().length);
        return createMonster(MonsterTypes.values()[random]);
    }
}
