package com.example.sessions;
import com.example.actions.*;

public class BattleActionSelectionSession extends Session {

   public BattleActionSelectionSession(Session parentSession) {
        super("アクション選択", "アクション選択", parentSession);
        BattleSession battleSession = (BattleSession) parentSession;
        this.displayText = parentSession.getDisplayText();
        
        // プレイヤーの使用可能アクションを動的に追加
        for (Action action : battleSession.getPlayer().getAvailableActions()) {
            if (action instanceof Heal) {
                action.setTarget(battleSession.getPlayer());
            } else {
                action.setTarget(battleSession.getMonster());
            }
            addCommand(action);
        }
        
        addCommand(new QuitCommand());  
        refreshDisplay();
   }

   @Override
   public void setDisplayText(String text) { parentSession.setDisplayText(text); }

   @Override
   public void showMessage(String text) { parentSession.showMessage(text); }

   @Override
   protected void afterCommandExecuted() {
        stop();
   }
}