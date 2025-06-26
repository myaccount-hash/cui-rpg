package com.example.actions;

import com.example.sessions.Session;

public abstract class Masic extends Action {
    public Masic(String name, String description, String commandName, Session selectSession) {
        super(name, description, commandName, selectSession);
    }
}
