package com.example.masics;

import com.example.commands.Command;
import com.example.sessions.BattleSession;
import com.example.sessions.Session;

public class FireBall extends Command {
    private Session selectSession;
    private BattleSession battleSession;
    public FireBall(Session selectSession) {
        super("fireball", "ファイアボールを使う", "fireball", selectSession);
        this.selectSession = selectSession;
        this.battleSession = (BattleSession)selectSession.getParentSession();
    }

    @Override
    public boolean execute(String[] args) {
        battleSession.getMonster().takeDamage(50);
        selectSession.stop();
        battleSession.setLogText(battleSession.getMonster().getName() + "にファイアボールを使った！");
        return true;
    }
}
