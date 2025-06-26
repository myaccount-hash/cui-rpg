package com.example.actions;

import com.example.entities.Entity;
import com.example.sessions.Session;

public abstract class Action extends Session.Command { 
    protected Entity source;
    protected Entity target;

    public Action(String name, String description, String commandName, Entity source, Entity target) {
        super(name, description, commandName);
        this.source = source;
        this.target = target;
    }
}
