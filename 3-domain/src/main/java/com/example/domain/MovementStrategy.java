package com.example.domain;

import com.example.domain.monster.Monster;

public interface MovementStrategy {
    Position getNextPosition(Monster monster, Player player);
}