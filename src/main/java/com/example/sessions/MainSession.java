package com.example.sessions;

import com.example.entities.Dragon;
import com.example.entities.Player;
import com.example.commands.QuitCommand;
import com.example.core.*;

/*
 * はじめに開始されるセッション。メインメニュー。
 */
public class MainSession extends Session {
  private final Player player;

  public MainSession(Session parentSession) {
    super("Main", "メイン対話型CUIプログラム", parentSession);
    this.player = new Player();

    addCommand(
        new Command("dragon", "ドラゴンバトルセッションを開始します") {
          @Override
          public boolean execute() {
            new BattleSession(
                    "DragonBattle", "ドラゴンバトルセッション", new Dragon(1), player, MainSession.this)
                .run();
            return true;
          }
        });
    addCommand(
        new Command("items", "所持アイテム一覧を表示") {
          @Override
          public boolean execute() {
            new PlayerItemListSession(player, MainSession.this).run();
            return true;
          }
        });
    addCommand(
        new Command("shop", "ショップに入る") {
          @Override
          public boolean execute() {
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
