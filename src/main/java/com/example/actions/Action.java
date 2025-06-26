package com.example.actions;

import com.example.commands.Command;
import com.example.entities.Entity;
import com.example.entities.Monster;
import com.example.entities.Player;
import com.example.sessions.Session;

public abstract class Action extends Command { 
    protected Entity source;
    protected Entity target;

    public Action(String name, String description, String commandName, Entity source, Entity target) {
        super(name, description, commandName);
        this.source = source;
        this.target = target;
    }

    public abstract String getActionLog();
}
