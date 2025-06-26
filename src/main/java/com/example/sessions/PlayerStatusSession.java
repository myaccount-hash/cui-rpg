package com.example.sessions;

import com.example.entities.Player;

/**
 * プレイヤーデータ確認セッション
 */
public class PlayerStatusSession extends Session {
    
    private final Player player;
    
    public PlayerStatusSession(Session parentSession) {
        super("PlayerStatus", "プレイヤーデータ確認セッション", parentSession);
        this.player = new Player();
        running = true;
        addCommand(new QuitCommand());
        
        // 初期表示テキストを設定
        setDisplayText(
                      "名前: " + player.getName() + "\n" +
                      "HP: " + player.getHp() + "/" + player.getMaxHp() + "\n" +
                      "攻撃力: " + player.getAttack());
        refreshDisplay();
        
        while (isRunning()) {
            String input = scanner.nextLine();
            // ログ表示中の場合は次のログを表示
            if (isLogDisplaying()) {
                showLog();
                continue;
            }
            if (!input.trim().isEmpty()) {
                processInput(input.trim());
            }
        }
    }


} 