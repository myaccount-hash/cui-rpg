package com.example.actions;

import com.example.utils.Command;
import com.example.utils.Player;
import com.example.utils.SaveDataManager;
import com.example.sessions.BattleSession;

/**
 * バトルアクションの抽象クラス
 * Commandを継承してバトル専用の機能を提供
 */
public abstract class BattleAction extends Command {
    
    protected BattleSession battleSession;
    
    /**
     * バトルアクションを作成
     * @param name アクション名
     * @param description アクションの説明
     * @param usage 使用方法
     * @param battleSession バトルセッション
     */
    public BattleAction(String name, String description, String usage, BattleSession battleSession) {
        super(name, description, usage);
        this.battleSession = battleSession;
    }
    
    /**
     * バトルアクションを実行
     * @param args 引数
     * @return 実行結果
     */
    @Override
    public boolean execute(String[] args) {
        Player player = SaveDataManager.loadPlayer();
        
        // ダメージ計算
        int damage = calculateDamage(player);
        
        // バトルセッションでアクションを実行
        return battleSession.executeBattleAction(damage, getActionMessage());
    }
    
    /**
     * ダメージを計算（サブクラスで実装）
     * @param player プレイヤー
     * @return ダメージ値
     */
    protected abstract int calculateDamage(Player player);
    
    /**
     * アクションメッセージを取得（サブクラスで実装）
     * @return アクションメッセージ
     */
    protected abstract String getActionMessage();
    
    /**
     * バトルセッションを取得
     * @return バトルセッション
     */
    public BattleSession getBattleSession() {
        return battleSession;
    }
    
    /**
     * バトルセッションを設定
     * @param battleSession バトルセッション
     */
    public void setBattleSession(BattleSession battleSession) {
        this.battleSession = battleSession;
    }
} 