package com.example.sessions;

import com.example.commands.Command;
import com.example.commands.PlayerStatusCommand;
import com.example.commands.QuitCommand;
import com.example.entities.Monster;
import com.example.entities.Player;

public class BattleSession extends Session {
    public enum BattleState { ONGOING, PLAYER_VICTORY, PLAYER_DEFEAT }
    
    protected Monster monster;
    protected Player player;
    
    public BattleSession(String name, String description, Monster monster, Session parentSession) {
        super(name, description, parentSession);
        this.monster = monster;
        this.player = new Player();
        running = true;
        
        addCommand(new Command("attack", "通常攻撃", "attack", this) {
            @Override
            public boolean execute(String[] args) {
                monster.takeDamage(20);
                setLogText(player.getName() + "は通常攻撃をした！");
                return true;
            }
        });
        addCommand(new Command("magic", "魔法を使う", "magic", this) {
            @Override
            public boolean execute(String[] args) {
                new BattleActionSelectionSession(BattleSession.this);
                return true;
            }
        });
        addCommand(new QuitCommand(this::stop));
        addCommand(new PlayerStatusCommand(player));
        
        runBattleLoop();
    }
    
    private void runBattleLoop() {
        setDisplayText(getBattleInfo());
        
        while (running) {
            String input = scanner.nextLine();
            if (logDisplaying) { 
                clearLog(); 
                continue; 
            }
            if (!input.trim().isEmpty()) {
                processInput(input.trim());
                executeMonsterAction();
                setDisplayText(getBattleInfo());
            }
        }
    }
    
    public Monster getMonster() { return monster; }
    public Player getPlayer() { return player; }
    
    private String getBattleInfo() {
        return monster.getIcon() + "\n\n名前: " + monster.getName() + 
               "\nHP: " + monster.getHp() + "/" + monster.getMaxHp() + 
               "\n攻撃力: " + monster.getAttack() + 
               "\n\nHP: " + player.getHp() + "/" + player.getMaxHp() + "\n";
    } 

    private void executeMonsterAction() {
        int damage = monster.getAttack();
        player.takeDamage(damage);
        setLogText(monster.getName() + "の攻撃！" + damage + "ダメージを受けました。");
    }
}