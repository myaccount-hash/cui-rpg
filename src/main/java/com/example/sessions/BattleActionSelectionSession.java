package com.example.sessions;
import com.example.actions.*;

public class BattleActionSelectionSession extends Session {
   
   public BattleActionSelectionSession(Session parentSession) {
        super("アクション選択", "アクション選択", parentSession);
        running = true;
        BattleSession battleSession = (BattleSession) parentSession;
        this.displayText = parentSession.displayText;
        addCommand(new SwordAttack(battleSession.getPlayer(), battleSession.getMonster()));
        addCommand(new FireBall(battleSession.getPlayer(), battleSession.getMonster()));
        addCommand(new Heal(battleSession.getPlayer(), battleSession.getMonster()));
        addCommand(new QuitCommand());  
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
                stop();
            }
        }

   }
   

   
   @Override
   public void setDisplayText(String text) { parentSession.setDisplayText(text); }
   
   @Override
   public void setLogText(String text) { parentSession.setLogText(text); }
}