package com.example.sessions;

import com.example.commands.HelpCommand;
import com.example.commands.QuitCommand;

public class BattleActionSelectionSession extends Session {
    private BattleSession parentSession;
    
    public BattleActionSelectionSession(Session parentSession) {
        super("アクション選択", "攻撃方法を選択してください", parentSession);
        this.parentSession = (BattleSession) parentSession;
    }

    
    @Override
    protected void initializeCommands() {
        addCommand(new com.example.commands.Command("sword", "剣で攻撃する", "sword", this) {
            @Override
            public boolean execute(String[] args) {
                parentSession.getMonster().takeDamage(20);
                parentSession.setLogText("剣で攻撃！ ");
                parentSession.refreshDisplay();
                BattleActionSelectionSession.this.stop();
                return true;
            }
        });
        
        addCommand(new QuitCommand(this::stop));
        setDisplayText("攻撃方法を選択してください");
    }
}