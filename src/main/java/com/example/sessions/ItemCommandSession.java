package com.example.sessions;

import com.example.entities.Player;
import com.example.items.Item;
import com.example.commands.Command;
import com.example.commands.QuitCommand;
import com.example.entities.Entity;

/*
 * 特定のItemに対して行うCommandを選ぶセッション。
 */
public class ItemCommandSession extends Session {
  public ItemCommandSession(Player player, Item item, Session parentSession) {
    super("Command", "アイテムアクション", parentSession);
    setDisplayText(buildItemDetail(item));

    // プレイヤーからItemを取得し、Commandコマンドの項目リストを生成
    item.getActions().forEach(action -> addCommand(createActionCommand(action, player, item)));
    addCommand(new QuitCommand(this));
  }

  private String buildItemDetail(Item item) {
    return String.format("【アイテム詳細】\n名前: %s\n説明: %s", item.getName(), item.getDescription());
  }

  private Command createActionCommand(Command action, Player player, Item item) {
    return new Command(action.getName(), action.getDescription(), player, player) {
      @Override
      public boolean execute(String[] args) {
        action.execute(args);
        return true;
      }
    };
  }

  @Override
  protected void afterCommandExecuted() {
    stop();
  }
}
