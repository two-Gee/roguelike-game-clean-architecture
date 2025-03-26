package com.example.domain;

import com.example.domain.item.Weapon;


public class Player extends LivingEntity {
    private Weapon equippedWeapon;
    private final int maxHealth;

    public Player(int roomID, Position position, String name) {
        super(100, 10, roomID, position, name);
        this.maxHealth = 100;
    }

    public void equipWeapon(Weapon weapon) {
        if(equippedWeapon == null){
            weapon.setPickedUp(true);
            weapon.setPosition(null);
            weapon.setRoomNumber(-1);
            this.equippedWeapon = weapon;
            this.setAttack(this.getAttack() + weapon.getAttack());
        }
    }

    public Weapon unEquipWeapon() {
        Weapon weapon = equippedWeapon;
        if(equippedWeapon != null){
            equippedWeapon.setPickedUp(false);
            this.setAttack(this.getAttack() - equippedWeapon.getAttack());
            equippedWeapon = null;
        }
        return weapon;
    }

    public Weapon getEquippedWeapon() {
        return equippedWeapon;
    }
    public void heal(int health){
        int newHealth = this.getHealth() + health;
        this.setHealth(Math.min(newHealth, maxHealth));
    }
}
