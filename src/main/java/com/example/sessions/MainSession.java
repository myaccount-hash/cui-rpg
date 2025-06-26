package com.example.sessions;

import com.example.core.Command;
import com.example.core.Session;
import com.example.entities.Dragon;
import com.example.entities.Player;


public class MainSession extends Session {
    
    public MainSession(Session parentSession) {
        super("Main", "メイン対話型CUIプログラム", parentSession);
        addCommand(new NewSessionCommand());
        addCommand(new DragonBattleCommand());
        addCommand(new QuitCommand());
        addCommand(new Command("items", "所持アイテム一覧を表示", "items", this) {
            @Override
            public boolean execute(String[] args) {
                new PlayerItemListSession(new Player(), MainSession.this).run();
                return true;
            }
        });
        // 初期表示テキストを設定
        setDisplayText("ゲームを開始しました。");
        // ループは親クラスrun()に任せる
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
            new SampleSession(MainSession.this).run();
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
            
            new BattleSession("DragonBattle", "ドラゴンバトルセッション", new Dragon(), MainSession.this).run();
            
            return true;
        }
    }
} 