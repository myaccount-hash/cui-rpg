package com.example.sessions;

import com.example.entities.Dragon;
import com.example.entities.Player;


public class MainSession extends Session {
    
    public MainSession(Session parentSession) {
        super("Main", "メイン対話型CUIプログラム", parentSession);
        addCommand(new Session.Command("dragon", "ドラゴンバトルセッションを開始します", "dragon") {
            @Override
            public boolean execute(String[] args) {
                new BattleSession("DragonBattle", "ドラゴンバトルセッション", new Dragon(), MainSession.this).run();
                return true;
            }
        });
        addCommand(new Session.Command("items", "所持アイテム一覧を表示", "items") {
            @Override
            public boolean execute(String[] args) {
                new PlayerItemListSession(new Player(), MainSession.this).run();
                return true;
            }
        });
        addCommand(new QuitCommand());

        // 初期表示テキストを設定
        setDisplayText("ゲームを開始しました。");
    }
} 