package com.example.sessions;
import com.example.commands.QuitCommand;
import com.example.utils.Player;
import com.example.utils.SaveDataManager;

/**
 * プレイヤーデータ確認セッション
 */
public class PlayerStatusSession extends Session {
    
    private final Player player;
    
    public PlayerStatusSession(Session parentSession) {
        super("PlayerStatus", "プレイヤーデータ確認セッション", parentSession);
        this.player = SaveDataManager.loadPlayer();
        running = true;
        addCommand(new QuitCommand(this::stop));
        
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