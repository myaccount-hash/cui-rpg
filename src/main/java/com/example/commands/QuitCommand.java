package com.example.commands;

import com.example.sessions.Session;

public class QuitCommand extends Command {
  Session session;

  public QuitCommand(Session session) {
    super("quit", "セッションを終了します");
    this.session = session;
  }

  @Override
  public boolean execute() {
    session.stop();
    return true;
  }
}
