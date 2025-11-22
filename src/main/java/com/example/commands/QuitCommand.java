package com.example.commands;

import com.example.entities.Entity;
import com.example.sessions.Session;

public class QuitCommand extends Command {
  Session session;

  public QuitCommand(Session session, Entity executor) {
    super("quit", "セッションを終了します", executor);
    this.session = session;
  }

  @Override
  public boolean execute() {
    session.stop();
    return true;
  }
}
