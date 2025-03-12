package com.example.application;

import com.example.application.Factories.MonsterFactory;
import com.example.domain.Monster.Monster;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MonsterStore {
    private Map<UUID, Monster> monsters;

    public MonsterStore(Map<UUID, Monster> monsters) {
        this.monsters = monsters;
    }

    public List<Monster> findByRoomNumber(int roomNumber) {
        return monsters.values().stream()
                .filter(monster -> monster.getRoomNumber() ==(roomNumber))
                .toList();
    }
    public boolean remove(UUID id){
        return monsters.remove(id) != null;
    }
    public List<Monster> getMonsters() {
        return monsters.values().stream().toList();
    }
}
