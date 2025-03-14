package com.example.domain;

import com.example.domain.Dungeon;
import com.example.domain.Monster.Monster;
import com.example.domain.Player;
import com.example.domain.Position;

public interface MovementStrategy {
    Position getNextPosition(Monster monster, Player player);
}