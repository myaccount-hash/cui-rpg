package com.example.actions;

import com.example.entities.Entity;

public class Heal extends Action {
    public Heal(Entity source, Entity target) {
        super("heal", "回復魔法を使う", "heal", source, target);
    }
    @Override
    public boolean execute(String[] args) {
        target.heal(20);
        return true;
    }
    @Override
    public String getActionLog() {
       return source.getName() + "は回復魔法を使った！";
    }
}
