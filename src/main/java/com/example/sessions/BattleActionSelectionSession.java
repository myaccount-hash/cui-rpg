package com.example.sessions;

import com.example.commands.HelpCommand;
import com.example.commands.QuitCommand;

public class BattleActionSelectionSession extends Session {
    private BattleSession parentSession;
    
    public BattleActionSelectionSession(Session parentSession) {
        super("アクション選択", "攻撃方法を選択してください", parentSession);
        this.parentSession = (BattleSession) parentSession;
    }
    
    public BattleSession getParentSession() { return parentSession; }
    public void setParentLog(String text) { parentSession.setLogText(text); }
    
    @Override
    protected void processInput(String input) {
        String commandName = null;
        String[] args = new String[0];
        
        if (input.matches("\\d+")) {
            int idx = Integer.parseInt(input) - 1;
            if (idx >= 0 && idx < commandOrder.size()) {
                commandName = commandOrder.get(idx);
            } else {
                setDisplayText("無効な番号です");
                return;
            }
        } else {
            String[] parts = input.split("\\s+");
            commandName = parts[0].toLowerCase();
            if (parts.length > 1) {
                args = new String[parts.length - 1];
                System.arraycopy(parts, 1, args, 0, parts.length - 1);
            }
        }
        
        if (commandManager.executeCommand(commandName, args)) {
            stop();
        }
    }
    
    @Override
    protected void initializeCommands() {
        addCommand(new com.example.commands.Command("sword", "剣で攻撃する", "sword", this) {
            @Override
            public boolean execute(String[] args) {
                parentSession.getMonster().takeDamage(20);
                return true;
            }
        });
        
        addCommand(new HelpCommand(commandManager));
        addCommand(new QuitCommand(this::stop));
        setDisplayText("攻撃方法を選択してください");
    }
}