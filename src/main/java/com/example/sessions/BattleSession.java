package com.example.sessions;

import com.example.commands.Command;
import com.example.commands.PlayerStatusCommand;
import com.example.commands.QuitCommand;
import com.example.commands.SaveCommand;
import com.example.monsters.Monster;
import com.example.utils.*;

/**
 * バトルセッションクラス
 */
public class BattleSession extends Session {
    
    /**
     * バトルの状態を表す列挙型
     */
    public enum BattleState {
        ONGOING, PLAYER_VICTORY, PLAYER_DEFEAT
    }
    
    protected Monster monster;
    protected Player player;
    
    public BattleSession(String name, String description, Monster monster, Session parentSession) {
        super(name, description, parentSession);
        this.monster = monster;
        this.player = SaveDataManager.loadPlayer();
    }
    
    /**
     * モンスターを取得
     * @return モンスター
     */
    public Monster getMonster() {
        return monster;
    }
    
    /**
     * プレイヤーを取得
     * @return プレイヤー
     */
    public Player getPlayer() {
        return player;
    }
    
    @Override
    protected void initializeCommands() {
        addCommand(new PlayerActionSelection());
        addCommand(new SaveCommand(player));
        addCommand(new QuitCommand(this::stop));
        addCommand(new PlayerStatusCommand(player));
        setDisplayText(getBattleStartMessage());
    }
    

    
    /**
     * バトル開始メッセージを取得
     * @return バトル開始メッセージ
     */
    public String getBattleStartMessage() {
        return monster.getIcon() + "\n\n" +
               "名前: " + monster.getName() + "\n" +
               "HP: " + monster.getHp() + "/" + monster.getMaxHp() + "\n" +
               "攻撃力: " + monster.getAttack() + "\n\n" +
               "HP: " + player.getHp() + "/" + player.getMaxHp() + "\n";
    }
    
    /**
     * 現在のバトル状態を取得
     * @return バトル状態
     */
    public BattleState getBattleState() {
        if (player.getHp() <= 0) {
            return BattleState.PLAYER_DEFEAT;
        }
        if (monster.getHp() <= 0) {
            return BattleState.PLAYER_VICTORY;
        }
        return BattleState.ONGOING;
    }
    
    /**
     * バトル状態をチェック
     * @return バトル可能かどうか
     */
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
    
    /**
     * ダメージを適用
     * @param damage ダメージ値
     */
    public void applyDamageToMonster(int damage) {
        int newHp = Math.max(0, monster.getHp() - damage);
        monster.setHp(newHp);
    }
    
    /**
     * プレイヤーにダメージを適用
     * @param damage ダメージ値
     */
    public void applyDamageToPlayer(int damage) {
        int newHp = Math.max(0, player.getHp() - damage);
        player.setHp(newHp);
    }
    
    /**
     * バトルアクションの結果をログに記録
     * @param actionMessage アクションメッセージ
     * @param damage ダメージ値
     */
    public void logBattleAction(String actionMessage, int damage) {
        String logMessage = actionMessage + monster.getName() + "に" + damage + "ダメージを与えました。";
        setLogText(logMessage);
    }
    
    /**
     * バトルアクションを実行
     * @param damage ダメージ値
     * @param actionMessage アクションメッセージ
     * @return 実行結果
     */
    public boolean executeBattleAction(int damage, String actionMessage) {
        // バトル状態チェック
        if (!checkBattleState()) {
            return false;
        }
        
        // ダメージ適用
        applyDamageToMonster(damage);
        
        // 結果をログに記録
        logBattleAction(actionMessage, damage);
        
        // バトル終了チェック
        handleBattleEnd();
        
        return true;
    }
    
    /**
     * モンスターの攻撃を実行
     */
    public void executeMonsterAction() {
        BattleState state = getBattleState();
        
        // バトルが継続中でない場合は攻撃しない
        if (state != BattleState.ONGOING) {
            return;
        }
        
        int monsterDamage = monster.getAttack();
        applyDamageToPlayer(monsterDamage);
        
        String monsterAttackLog = monster.getName() + "の攻撃！" + monsterDamage + "ダメージを受けました。";
        setLogText(monsterAttackLog);
        refreshDisplay();
        
        // バトル終了チェック
        handleBattleEnd();
    }
    
    /**
     * バトル終了処理
     */
    private void handleBattleEnd() {
        BattleState state = getBattleState();
        
        switch (state) {
            case PLAYER_VICTORY:
                setLogText(monster.getName() + "を倒しました！");
                // TODO: 経験値やアイテム獲得処理
                return; // 画面更新は setLogText で行われる
            case PLAYER_DEFEAT:
                setLogText("あなたは倒されました...");
                // TODO: ゲームオーバー処理
                return; // 画面更新は setLogText で行われる
            case ONGOING:
                // バトル継続の場合のみ画面更新
                setDisplayText(getBattleStartMessage());
                break;
        }
    }
    
    /**
     * 攻撃コマンド
     */
    private class PlayerActionSelection extends Command {
        public PlayerActionSelection() {
            super("attack", "攻撃方法を選択する", "attack", BattleSession.this);
        }
        
        @Override
        public boolean execute(String[] args) {
            // バトル状態チェック
            if (!checkBattleState()) {
                return false;
            }
            
            // アクション選択セッションを開始
            BattleActionSelectionSession actionSession = 
            new BattleActionSelectionSession(BattleSession.this);
            actionSession.start();
            
            // モンスターの攻撃を実行（プレイヤーアクション後）
            executeMonsterAction();
            
            return true;
        }
    }
}