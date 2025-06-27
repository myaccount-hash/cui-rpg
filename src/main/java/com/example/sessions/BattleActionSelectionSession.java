package com.example.sessions;
import com.example.actions.*;

public class BattleActionSelectionSession extends Session {

   public BattleActionSelectionSession(Session parentSession) {
        super("アクション選択", "アクション選択", parentSession);
        BattleSession battleSession = (BattleSession) parentSession;
        this.displayText = parentSession.getDisplayText();
        
        // 基本アクションを追加
        addCommand(new NormalAttack(battleSession.getPlayer(), battleSession.getMonster()));
        addCommand(new FireBall(battleSession.getPlayer(), battleSession.getMonster()));
        addCommand(new Heal(battleSession.getPlayer(), battleSession.getPlayer()));
        
        // プレイヤーのスキルを動的に追加
        addCommands(battleSession.getPlayer().getSkills());
        
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