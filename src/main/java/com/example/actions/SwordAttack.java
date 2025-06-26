package com.example.actions;

import com.example.entities.Entity;

public class SwordAttack extends Action {
    public SwordAttack(Entity source, Entity target) {
        super("sword attack", "剣で攻撃", "swordattack", source, target);
    }

    @Override
    public boolean execute(String[] args) {
        int totalAttack = source.getAttack();
        if (source.getWeapon() != null) {
            totalAttack += source.getWeapon().getAttack();
        }
        totalAttack = totalAttack - target.getDefence();
        target.takeDamage(totalAttack);
        setCommandLog(source.getName() + "は剣で攻撃した！");
        return true;
    }
}
