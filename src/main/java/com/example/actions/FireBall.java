package com.example.actions;

import com.example.entities.Entity;

public class FireBall extends Action {
    public FireBall(Entity source, Entity target) {
        super("fireball", "ファイアボールを使う", "fireball", source, target);
    }

    @Override
    public boolean execute(String[] args) {
        target.takeDamage(50);
        return true;
    }
    @Override
    public String getActionLog() {
       return source.getName() + "はファイアボールを使った！";
    }
}
