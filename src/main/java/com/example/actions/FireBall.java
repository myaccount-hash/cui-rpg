package com.example.actions;

import com.example.utils.Player;
import com.example.sessions.BattleSession;

/**
 * ファイアボール魔法攻撃アクション
 */
public class FireBall extends BattleAction {
    
    private static final int BASE_DAMAGE = 25;
    
    public FireBall(BattleSession battleSession) {
        super("fireball", "ファイアボールで攻撃する", "fireball", battleSession);
    }
    
    @Override
    protected int calculateDamage(Player player) {
        return BASE_DAMAGE + (player.getAttack() / 2); // 魔法は攻撃力の半分
    }
    
    @Override
    protected String getActionMessage() {
        return "ファイアボール！";
    }
} 