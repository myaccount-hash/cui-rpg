package com.example.actions;

import com.example.commands.Command;
import com.example.entities.Entity;
import com.example.entities.Monster;
import com.example.entities.Player;
import com.example.sessions.Session;

public abstract class Action extends Command {
    protected Session selectSession;
    protected Player player;
    protected Monster monster;
    public Action(String name, String description, String commandName) {
        super(name, description, commandName);
    }

    public abstract boolean action(Entity source, Entity target);
    public abstract String getActionLog();
}
