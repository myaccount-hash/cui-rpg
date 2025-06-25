package com.example.sessions;

import com.example.commands.Command;
import com.example.commands.QuitCommand;
import com.example.commands.PlayerStatusCommand;
import com.example.utils.SaveDataManager;
import com.example.monsters.Dragon;

public class MainSession extends Session {
    
    public MainSession(Session parentSession) {
        super("Main", "メイン対話型CUIプログラム", parentSession);
        running = true;
        addCommand(new NewSessionCommand());
        addCommand(new DragonBattleCommand());
        addCommand(new PlayerStatusCommand(SaveDataManager.loadPlayer()));
        addCommand(new QuitCommand(this::stop));
        
        // 初期表示テキストを設定
        setDisplayText("ゲームを開始しました。");
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
    

    
    /**
     * 新しい対話セッションを開始するコマンド（内部クラス）
     */
    private class NewSessionCommand extends Command {
        
        public NewSessionCommand() {
            super("new", "新しい対話セッションを開始します", "new");
        }
        
        @Override
        public boolean execute(String[] args) {
            new SampleSession(MainSession.this);
            return true;
        }
    }
    
    /**
     * ドラゴンバトルセッションを開始するコマンド（内部クラス）
     */
    private class DragonBattleCommand extends Command {
        
        public DragonBattleCommand() {
            super("dragon", "ドラゴンバトルセッションを開始します", "dragon");
        }
        
        @Override
        public boolean execute(String[] args) {
            
            new BattleSession("DragonBattle", "ドラゴンバトルセッション", new Dragon(), MainSession.this);
            
            return true;
        }
    }
} 