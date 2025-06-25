package com.example.sessions;

import com.example.commands.Command;
import com.example.commands.PlayerStatusCommand;
import com.example.commands.QuitCommand;
import com.example.commands.SaveCommand;
import com.example.monsters.Monster;
import com.example.utils.*;
import java.util.ArrayList;

public class BattleSession extends Session {
    public enum BattleState { ONGOING, PLAYER_VICTORY, PLAYER_DEFEAT }
    
    protected Monster monster;
    protected Player player;
    
    public BattleSession(String name, String description, Monster monster, Session parentSession) {
        super(name, description, parentSession);
        this.monster = monster;
        this.player = SaveDataManager.loadPlayer();
    }
    
    public Monster getMonster() { return monster; }
    public Player getPlayer() { return player; }
    
    @Override
    protected void initializeCommands() {
        addCommand(new PlayerActionSelection());
        addCommand(new SaveCommand(player));
        addCommand(new QuitCommand(this::stop));
        addCommand(new PlayerStatusCommand(player));
        setDisplayText(getBattleStartMessage());
    }
    
    @Override
    public void start() {
        running = true;
        initializeCommands();
        
        while (running) {
            String input = scanner.nextLine();
            if (logDisplaying) { clearLog(); continue; }
            if (!input.trim().isEmpty()) processInput(input.trim());
            refreshDisplay();
            setLogText("攻撃！ ");
            executeMonsterAction();
        }
    }

    public String getBattleStartMessage() {
        return monster.getIcon() + "\n\n名前: " + monster.getName() + 
               "\nHP: " + monster.getHp() + "/" + monster.getMaxHp() + 
               "\n攻撃力: " + monster.getAttack() + 
               "\n\nHP: " + player.getHp() + "/" + player.getMaxHp() + "\n";
    }

    public void executeMonsterAction() {
        int monsterDamage = monster.getAttack();
        player.takeDamage(monsterDamage);
        setLogText(monster.getName() + "の攻撃！" + monsterDamage + "ダメージを受けました。");
        refreshDisplay();
    }

    private class PlayerActionSelection extends Command {
        public PlayerActionSelection() {
            super("attack", "攻撃方法を選択する", "attack", BattleSession.this);
        }
        
        @Override
        public boolean execute(String[] args) {
            new BattleActionSelectionSession(BattleSession.this).start();
            return true;
        }
    }

    public class BattleActionSelectionSession extends Session {
        private BattleSession parentSession;
        
        public BattleActionSelectionSession(Session parentSession) {
            super("アクション選択", "攻撃方法を選択してください", parentSession);
            this.parentSession = (BattleSession) parentSession;
            this.displayText = parentSession.displayText;
        }
        
        
        @Override
        protected void initializeCommands() {
            // 既存のメニューに追加（上書きではなく）
            addCommand(new Command("sword", "剣で攻撃する", "sword", this) {
                @Override
                public boolean execute(String[] args) {
                    parentSession.getMonster().takeDamage(20);
                    BattleActionSelectionSession.this.refreshDisplay();
                    BattleActionSelectionSession.this.stop();
                    return true;
                }
            });
            addCommand(new QuitCommand(this::stop));
        }
        
        @Override
        public void setDisplayText(String text) {
            parentSession.setDisplayText(text);
            return;
        }
        
        @Override
        public void setLogText(String text) {
            parentSession.setLogText(text);
            return;
        }
    }
}