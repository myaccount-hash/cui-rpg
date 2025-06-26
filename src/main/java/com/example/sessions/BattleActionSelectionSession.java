package com.example.sessions;
import com.example.actions.*;
import com.example.commands.QuitCommand;

public class BattleActionSelectionSession extends Session {
   private BattleSession parentSession;
   
   public BattleActionSelectionSession(Session parentSession) {
       super(null, null, parentSession);
       running = true;
       this.parentSession = (BattleSession) parentSession;
       this.displayText = parentSession.displayText;
       addCommand(new FireBall(BattleActionSelectionSession.this));
       addCommand(new Heal(BattleActionSelectionSession.this));
       addCommand(new QuitCommand(this::stop));
       refreshDisplay();
   
       while (isRunning()) {
           String input = scanner.nextLine();
           // ログ表示中の場合は次のログを表示
           if (isLogDisplaying()) {
               this.showLog();
               continue;
           }
           if (!input.trim().isEmpty()) {
               this.processInput(input.trim());
           }
       }

   }
   

   
   @Override
   public void setDisplayText(String text) { parentSession.setDisplayText(text); }
   
   @Override
   public void setLogText(String text) { parentSession.setLogText(text); }
}