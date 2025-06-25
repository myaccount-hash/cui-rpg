package com.example.masics;

import com.example.sessions.Session;

public class Heal extends Masic {
    public Heal(Session selectSession) {
        super("heal", "回復魔法を使う", "heal", selectSession);
    }
    @Override
    public boolean execute(String[] args) {
        player.heal(20);
        selectSession.stop();
        battleSession.setLogText(player.getName() + "は回復魔法を使った！");
        return true;
    }
}
