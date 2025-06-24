package com.example.actions;

import com.example.sessions.BattleSession;
import com.example.sessions.BattleActionSelectionSession;

/**
 * 剣攻撃アクション
 */
public class SwordAttack extends BattleAction {
    
    private BattleSession battleSession;
    private BattleActionSelectionSession actionSession;
    
    public SwordAttack(BattleSession battleSession, BattleActionSelectionSession actionSession) {
        super("sword", "剣で攻撃する", "sword");
        this.battleSession = battleSession;
        this.actionSession = actionSession;
    }
    
    @Override
    public boolean execute(String[] args) {
        // バトル状態チェック
        if (!battleSession.checkBattleState()) {
            return false;
        }
        
        // ダメージ計算（例：10-20のランダムダメージ）
        int damage = 10 + (int)(Math.random() * 11);
        
        // モンスターにダメージを適用
        battleSession.applyDamageToMonster(damage);
        
        // 親セッションにログを設定
        String logMessage = "剣で攻撃！" + battleSession.getMonster().getName() + 
                           "に" + damage + "ダメージを与えました。";
        actionSession.setParentLog(logMessage);
        
        // バトル終了チェック
        handleBattleEnd();
        
        return true;
    }
    
    /**
     * バトル終了チェック
     */
    private void handleBattleEnd() {
        BattleSession.BattleState state = battleSession.getBattleState();
        
        if (state == BattleSession.BattleState.PLAYER_VICTORY) {
            actionSession.setParentLog(battleSession.getMonster().getName() + "を倒しました！");
        }
        // 敗北時の処理は executeMonsterAction() で行われる
    }
}