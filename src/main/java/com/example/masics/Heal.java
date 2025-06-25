package com.example.masics;

import com.example.commands.Command;
import com.example.sessions.BattleSession;
import com.example.sessions.Session;

public class Heal extends Command {
    private Session selectSession;
    private BattleSession battleSession;
    public Heal(Session selectSession) {
        super("heal", "回復魔法を使う", "heal", selectSession);
        this.selectSession = (Session)selectSession;
        this.battleSession = (BattleSession)selectSession.getParentSession();
    }
    @Override
    public boolean execute(String[] args) {
        battleSession.getPlayer().heal(20);
        selectSession.stop();
        battleSession.setLogText(battleSession.getPlayer().getName() + "は回復魔法を使った！");
        return true;
    }
}
