package com.example.actions;
import com.example.sessions.Session;
import com.example.actions.Command;

public class QuitCommand extends Command {
   Session session;
   public QuitCommand(Session session) {
     super("quit", "セッションを終了します", "quit");
     this.session = session;
   }

   @Override
   public boolean execute(String[] args) {
      session.stop();
     return true;
   }
 }