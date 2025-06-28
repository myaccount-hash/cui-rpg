package com.example.sessions;

import com.example.entities.Dragon;
import com.example.entities.Player;
import com.example.actions.*;

/*
 * はじめに開始されるセッション。メインメニュー。
 */
public class MainSession extends Session {
  private final Player player;

  public MainSession(Session parentSession) {
    super("Main", "メイン対話型CUIプログラム", parentSession);
    this.player = new Player();

    addCommand(
        new Command("dragon", "ドラゴンバトルセッションを開始します", "dragon") {
          @Override
          public boolean execute(String[] args) {
            new BattleSession(
                    "DragonBattle", "ドラゴンバトルセッション", new Dragon(1), player, MainSession.this)
                .run();
            return true;
          }
        });
    addCommand(
        new Command("items", "所持アイテム一覧を表示", "items") {
          @Override
          public boolean execute(String[] args) {
            new PlayerItemListSession(player, MainSession.this).run();
            return true;
          }
        });
    addCommand(
        new Command("shop", "ショップに入る", "shop") {
          @Override
          public boolean execute(String[] args) {
            new ShopSession(player, MainSession.this).run();
            return true;
          }
        });
    addCommand(new QuitCommand(this));

    // 初期表示テキストを設定
    setDisplayText("ゲームを開始しました。");
  }

  public Player getPlayer() {
    return player;
  }
}
