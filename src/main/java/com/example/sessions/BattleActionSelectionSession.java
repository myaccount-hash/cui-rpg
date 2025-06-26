package com.example.sessions;
import com.example.actions.*;
import com.example.core.Session;

public class BattleActionSelectionSession extends Session {
   
   public BattleActionSelectionSession(Session parentSession) {
        super("アクション選択", "アクション選択", parentSession);
        BattleSession battleSession = (BattleSession) parentSession;
        this.displayText = parentSession.getDisplayText();
        addCommand(new SwordAttack(battleSession.getPlayer(), battleSession.getMonster()));
        addCommand(new FireBall(battleSession.getPlayer(), battleSession.getMonster()));
        addCommand(new Heal(battleSession.getPlayer(), battleSession.getMonster()));
        addCommand(new QuitCommand());  
        refreshDisplay();
   }

   @Override
   public void setDisplayText(String text) { parentSession.setDisplayText(text); }
   
   @Override
   public void setLogText(String text) { parentSession.setLogText(text); }
   
   @Override
   protected void afterCommandExecuted() {
       stop();
   }
}