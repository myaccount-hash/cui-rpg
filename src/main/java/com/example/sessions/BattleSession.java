package com.example.sessions;

import com.example.commands.Command;
import com.example.commands.PlayerStatusCommand;
import com.example.commands.QuitCommand;
import com.example.commands.SaveCommand;
import com.example.monsters.Monster;
import com.example.utils.*;

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
    
    public String getBattleStartMessage() {
        return monster.getIcon() + "\n\n" +
               "名前: " + monster.getName() + "\n" +
               "HP: " + monster.getHp() + "/" + monster.getMaxHp() + "\n" +
               "攻撃力: " + monster.getAttack() + "\n\n" +
               "HP: " + player.getHp() + "/" + player.getMaxHp() + "\n";
    }
    
    public BattleState getBattleState() {
        if (player.getHp() <= 0) return BattleState.PLAYER_DEFEAT;
        if (monster.getHp() <= 0) return BattleState.PLAYER_VICTORY;
        return BattleState.ONGOING;
    }
    
    public boolean checkBattleState() {
        BattleState state = getBattleState();
        switch (state) {
            case PLAYER_DEFEAT:
                setLogText("あなたは既に倒れています。");
                return false;
            case PLAYER_VICTORY:
                setLogText(monster.getName() + "は既に倒れています。");
                return false;
            case ONGOING:
                return true;
            default:
                return false;
        }
    }

    public void logBattleAction(String actionMessage, int damage) {
        setLogText(actionMessage + monster.getName() + "に" + damage + "ダメージを与えました。");
    }
    
    
    public void executeMonsterAction() {
        if (getBattleState() != BattleState.ONGOING) return;
        
        int monsterDamage = monster.getAttack();
        setLogText(monster.getName() + "の攻撃！" + monsterDamage + "ダメージを受けました。");
        refreshDisplay();
        handleBattleEnd();
    }
    
    private void handleBattleEnd() {
        BattleState state = getBattleState();
        switch (state) {
            case PLAYER_VICTORY:
                setLogText(monster.getName() + "を倒しました！");
                return;
            case PLAYER_DEFEAT:
                setLogText("あなたは倒されました...");
                return;
            case ONGOING:
                setDisplayText(getBattleStartMessage());
                break;
        }
    }
    
    private class PlayerActionSelection extends Command {
        public PlayerActionSelection() {
            super("attack", "攻撃方法を選択する", "attack", BattleSession.this);
        }
        
        @Override
        public boolean execute(String[] args) {
            if (!checkBattleState()) return false;
            
            BattleActionSelectionSession actionSession = 
                new BattleActionSelectionSession(BattleSession.this);
            actionSession.start();
            executeMonsterAction();
            return true;
        }
    }
}