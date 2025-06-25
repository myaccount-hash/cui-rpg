package com.example.masics;

import com.example.commands.Command;
import com.example.sessions.BattleSession;
import com.example.sessions.Session;
import com.example.utils.Player;
import com.example.monsters.Monster;

public abstract class Masic extends Command {
    protected Session selectSession;
    protected BattleSession battleSession;
    protected Player player;
    protected Monster monster;
    public Masic(String name, String description, String commandName, Session selectSession) {
        super(name, description, commandName, selectSession);
        this.selectSession = selectSession;
        this.battleSession = (BattleSession)selectSession.getParentSession();
        this.player = battleSession.getPlayer();
        this.monster = battleSession.getMonster();
    }
}
