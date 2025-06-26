package com.example.actions;

import com.example.entities.Entity;

public class FireBall extends Action {
    public FireBall(Entity source, Entity target) {
        super("fireball", "ファイアボールを使う", "fireball", source, target);
    }

    @Override
    public boolean execute(String[] args) {
        int damage = target.getAttack() - source.getDefence();
        target.takeDamage(damage);
        setCommandLog(source.getName() + "はファイアボールを使った！");
        return true;
    }
}
