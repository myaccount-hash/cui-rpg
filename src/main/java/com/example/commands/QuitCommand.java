package com.example.commands;

import com.example.entities.IEntity;
import com.example.sessions.Session;

public class QuitCommand extends Command {
  Session session;

  public QuitCommand(Session session, IEntity executor) {
    super("quit", "セッションを終了します", executor);
    this.session = session;
  }

  @Override
  public boolean execute() {
    session.stop();
    return true;
  }
}
