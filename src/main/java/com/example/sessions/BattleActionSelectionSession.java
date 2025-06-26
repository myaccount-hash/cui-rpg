package com.example.sessions;
import com.example.actions.*;
import com.example.entities.Monster;
import com.example.entities.Player;


public class BattleActionSelectionSession extends Session {
    private final BattleSession battleSession;
    
    public BattleActionSelectionSession(BattleSession battleSession) {
        super("アクション選択", "アクション選択", battleSession);
        this.battleSession = battleSession;
        this.displayText = battleSession.getDisplayText();
        
        // アクションコマンドを追加
        addBattleActions();
    }
    
    private void addBattleActions() {
        Player player = battleSession.getPlayer();
        Monster monster = battleSession.getMonster();
        
        addCommand(new SwordAttack(player, monster));
        addCommand(new FireBall(player, monster));
        addCommand(new Heal(player, monster));
    }
    
    @Override
    public void setDisplayText(String text) { 
        battleSession.setDisplayText(text); 
    }
    
    @Override
    public void showMessage(String text) { 
        battleSession.showMessage(text); 
    }
    
    @Override
    protected void afterCommandExecuted() {
        stop();
    }
}