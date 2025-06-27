package com.example.actions;

import com.example.entities.Entity;

public class FireBall extends Masic {
    public FireBall(Entity source, Entity target) {
        super("fireball", "ファイアボールを使う", "fireball", source, target, 25);
    }

    @Override
    protected boolean executeMagic(String[] args) {
        int damage = 30 + source.getAttack() - target.getDefence();
        target.takeDamage(damage);
        setCommandLog(source.getName() + "はファイアボールを使った！");
        return true;
    }
}
