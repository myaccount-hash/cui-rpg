package com.example.commands;

import com.example.utils.Player;
import com.example.sessions.PlayerStatusSession;

/**
 * プレイヤーデータを表示するコマンド
 */
public class PlayerStatusCommand extends Command {
    
    private final Player player;
    
    public PlayerStatusCommand(Player player) {
        super("status", "プレイヤーデータを表示する", "status");
        this.player = player;
    }
    
    @Override
    public boolean execute(String[] args) {
        System.out.println("=== プレイヤー情報 ===");
        System.out.println("名前: " + player.getName());
        System.out.println("HP: " + player.getHp() + "/" + player.getMaxHp());
        System.out.println("攻撃力: " + player.getAttack());
        
        // プレイヤーデータ確認セッションを開始
        PlayerStatusSession session = new PlayerStatusSession(null);
        session.start();
        
        return true;
    }
} 