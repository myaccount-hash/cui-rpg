package com.example.sessions;

import com.example.commands.Command;
import com.example.commands.PlayerStatusCommand;
import com.example.commands.QuitCommand;
import com.example.commands.SaveCommand;
import com.example.monsters.Monster;
import com.example.utils.*;
import com.example.masics.*;


public class BattleSession extends Session {
    public enum BattleState { ONGOING, PLAYER_VICTORY, PLAYER_DEFEAT }
    
    protected Monster monster;
    protected Player player;
    
    public BattleSession(String name, String description, Monster monster, Session parentSession) {
        super(name, description, parentSession);
        this.monster = monster;
        this.player = SaveDataManager.loadPlayer();
        running = true;
        addCommand(new Command("attack", "通常攻撃", "attack", this) {
            @Override
            public boolean execute(String[] args) {
                BattleSession.this.getMonster().takeDamage(20);
                BattleSession.this.setLogText(player.getName() + "は通常攻撃をした！");
                return true;
            }
        });
        addCommand(new MasicSelection());
        addCommand(new SaveCommand(player));
        addCommand(new QuitCommand(this::stop));
        addCommand(new PlayerStatusCommand(player));
        setDisplayText(getBattleStartMessage());
        
        while (running) {
            String input = scanner.nextLine();
            if (logDisplaying) { 
                clearLog(); 
                continue; 
            }
            if (!input.trim().isEmpty()) {
                processInput(input.trim());
                setDisplayText(getBattleStartMessage());
                refreshDisplay();
            }
            executeMonsterAction();
            setDisplayText(getBattleStartMessage());
        }
    }
    
    public Monster getMonster() { return monster; }
    public Player getPlayer() { return player; }

    
    
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

    private class MasicSelection extends Command {
        public MasicSelection() {
            super("magic", "魔法を使う", "magic", BattleSession.this);
        }
        
        @Override
        public boolean execute(String[] args) {
            new BattleActionSelectionSession(BattleSession.this);
            return true;
        }
    }

    public class BattleActionSelectionSession extends Session {
        private BattleSession parentSession;
        
        public BattleActionSelectionSession(Session parentSession) {
            super(null, null, parentSession);
            this.parentSession = (BattleSession) parentSession;
            this.displayText = parentSession.displayText;
            addCommand(new FireBall(BattleActionSelectionSession.this));
            addCommand(new Heal(BattleActionSelectionSession.this));
            addCommand(new QuitCommand(this::stop));
        }
        
        
        
        @Override
        public void setDisplayText(String text) { parentSession.setDisplayText(text); }
        
        @Override
        public void setLogText(String text) { parentSession.setLogText(text); }
    }
}