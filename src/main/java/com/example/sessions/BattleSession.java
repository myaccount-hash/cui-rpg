package com.example.sessions;

import com.example.core.Command;
import com.example.core.Session;
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
        addCommand(new Command("action", "アクションを選択", "action", this) {
            @Override
            public boolean execute(String[] args) {
                new BattleActionSelectionSession(BattleSession.this).run();
                return true;
            }
        });
        addCommand(new QuitCommand());
        setDisplayText(getBattleInfo());
    }
    
    @Override
    protected void afterCommandExecuted() {
        setDisplayText(getBattleInfo());
        executeMonsterAction();
        setDisplayText(getBattleInfo());
    }
    
    public Monster getMonster() { return monster; }
    public Player getPlayer() { return player; }
    
    private String getBattleInfo() {
        return monster.getIcon() + "\n" + monster.getInfoText() + "\n\n";
    } 

    private void executeMonsterAction() {
        int damage = monster.getAttack();
        player.takeDamage(damage);
        setLogText(monster.getName() + "の攻撃！" + damage + "ダメージを受けました。");
    }
}