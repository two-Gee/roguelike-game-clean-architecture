package com.example.Monster;

public class MonsterFactory {
    public static Monster createMonster(MonsterTypes type){
        return switch (type) {
            case GOBLIN -> new Monster("Goblin", 10, 5, 5);
            case ORC -> new Monster("Orc", 20, 10, 10);
            case TROLL -> new Monster("Troll", 15, 8, 15);
            default -> null;
        };
    }

    public static Monster createMonsterRandom(){
        int random = (int)(Math.random() * MonsterTypes.values().length);
        return createMonster(MonsterTypes.values()[random]);
    }
}
