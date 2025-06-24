package com.example.sessions;

import com.example.commands.Command;
import com.example.commands.HelpCommand;
import com.example.commands.QuitCommand;
import com.example.commands.PlayerStatusCommand;
import com.example.utils.SaveDataManager;
import com.example.monsters.Dragon;

public class MainSession extends Session {
    
    public MainSession(Session parentSession) {
        super("Main", "メイン対話型CUIプログラム", parentSession);
    }
    
    @Override
    protected void initializeCommands() {
        addCommand(new NewSessionCommand());
        addCommand(new DragonBattleCommand());
        addCommand(new PlayerStatusCommand(SaveDataManager.loadPlayer()));
        addCommand(new HelpCommand(commandManager));
        addCommand(new QuitCommand(this::stop));
        
        // 初期表示テキストを設定
        setDisplayText("ゲームを開始しました。");
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
            SampleSession session = new SampleSession(MainSession.this);
            session.start();
            
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
            
            BattleSession session = new BattleSession("DragonBattle", "ドラゴンバトルセッション", new Dragon(), MainSession.this);
            session.start();
            
            return true;
        }
    }
} 