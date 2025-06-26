package com.example.actions;

import com.example.entities.Entity;
import com.example.entities.Player;
import com.example.items.Weapon;

public class SwordAttack extends Action {
    public SwordAttack(Entity source, Entity target) {
        super("sword attack", "剣で攻撃", "swordattack", source, target);
    }

    @Override
    public boolean execute(String[] args) {
        int totalAttack = source.getAttack();
        if (source instanceof Player) {
            Weapon weapon = ((Player) source).getWeapon();
            if (weapon != null) {
                totalAttack += weapon.getAttack();
            }
        }
        target.takeDamage(totalAttack);
        setCommandLog(source.getName() + "は剣で攻撃した！");
        return true;
    }
}
