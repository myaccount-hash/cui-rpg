package com.example.masics;

import com.example.sessions.Session;

public class FireBall extends Masic {
    public FireBall(Session selectSession) {
        super("fireball", "ファイアボールを使う", "fireball", selectSession);
    }

    @Override
    public boolean execute(String[] args) {
        monster.takeDamage(50);
        selectSession.stop();
        battleSession.setLogText(player.getName() + "はファイアボールを使った！");
        return true;
    }
}
