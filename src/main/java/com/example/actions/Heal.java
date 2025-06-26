package com.example.actions;

import com.example.entities.Entity;
import com.example.sessions.Session;

public class Heal extends Masic {
    public Heal(Session selectSession) {
        super("heal", "回復魔法を使う", "heal", selectSession);
    }
    @Override
    public boolean execute(String[] args) {
        action(player, monster);
        selectSession.stop();
        return true;
    }
    @Override
    public boolean action(Entity source, Entity target) {
       target.heal(20);
       return true;
    }
    @Override
    public String getActionLog() {
       return player.getName() + "は回復魔法を使った！";
    }
}
