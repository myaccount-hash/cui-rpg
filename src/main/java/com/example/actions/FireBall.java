package com.example.actions;

import com.example.entities.Entity;
import com.example.sessions.Session;

public class FireBall extends Masic {
    public FireBall(Session selectSession) {
        super("fireball", "ファイアボールを使う", "fireball", selectSession);
    }

    @Override
    public boolean execute(String[] args) {
        monster.takeDamage(50);
        selectSession.stop();
        return true;
    }
    @Override
    public boolean action(Entity source, Entity target) {
       return false;
    }
    @Override
    public String getActionLog() {
       return player.getName() + "はファイアボールを使った！";
    }
}
