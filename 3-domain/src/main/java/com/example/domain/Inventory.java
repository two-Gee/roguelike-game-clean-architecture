package com.example.domain;

import com.example.domain.Item.Consumables;
import com.example.domain.Item.Weapon;

import java.util.List;

public class Inventory {
    List<Consumables> consumables;
    int capacity;
    Weapon equippedWeapon;
    int coins;
}
