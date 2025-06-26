package com.example.actions;

import com.example.commands.Command;
import com.example.entities.Monster;
import com.example.entities.Player;
import com.example.sessions.BattleSession;
import com.example.sessions.Session;

public abstract class Action extends Command {
    protected Session selectSession;
    protected BattleSession battleSession;
    protected Player player;
    protected Monster monster;
    public Action(String name, String description, String commandName, Session selectSession) {
        super(name, description, commandName, selectSession);
        this.selectSession = selectSession;
        this.battleSession = (BattleSession)selectSession.getParentSession();
        this.player = battleSession.getPlayer();
        this.monster = battleSession.getMonster();
    }
}
