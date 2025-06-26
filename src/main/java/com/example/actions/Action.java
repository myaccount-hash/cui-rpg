package com.example.actions;

import com.example.core.Command;
import com.example.entities.Entity;

public abstract class Action extends Command { 
    protected Entity source;
    protected Entity target;

    public Action(String name, String description, String commandName, Entity source, Entity target) {
        super(name, description, commandName);
        this.source = source;
        this.target = target;
    }
}
