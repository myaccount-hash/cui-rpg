package com.example.sessions;

import com.example.commands.Command;
import com.example.commands.QuitCommand;
import com.example.entities.Dragon;
import com.example.entities.Entity;
import com.example.entities.Player;

/*
 * はじめに開始されるセッション．メインメニュー．
 */
public class MainSession extends Session {
  private final Player player;

  public MainSession(Session parentSession, Entity sessionOwner) {
    super("Main", "メイン対話型CUIプログラム", parentSession, sessionOwner);
    this.player = new Player();

    addCommand(
        new Command("dragon", "ドラゴンバトルセッションを開始します") {
          @Override
          public boolean execute() {
            new BattleSession(
                    "DragonBattle", "ドラゴンバトルセッション", new Dragon(1), MainSession.this, sessionOwner)
                .run();
            return true;
          }
        });
    addCommand(
        new Command("items", "所持アイテム一覧を表示") {
          @Override
          public boolean execute() {
            new PlayerItemListSession(MainSession.this, sessionOwner).run();
            return true;
          }
        });
    addCommand(
        new Command("shop", "ショップに入る") {
          @Override
          public boolean execute() {
            new ShopSession(MainSession.this, sessionOwner).run();
            return true;
          }
        });
    addCommand(
        new Command("error", "意図的にエラーを発生させる") {
          @Override
          public boolean execute() {
            throw new RuntimeException("デバッグ用: 意図的に発生させたエラー");
          }
        });
    addCommand(new QuitCommand(this));

    // 初期表示テキストを設定
    setDisplayText("ゲームを開始しました．");
  }

  public Player getPlayer() {
    return player;
  }
}
