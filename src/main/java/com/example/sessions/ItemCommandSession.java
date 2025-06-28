package com.example.sessions;

import com.example.commands.QuitCommand;
import com.example.core.*;
import com.example.entities.Player;

/*
 * 特定のItemに対して行うCommandを選ぶセッション。
 */
public class ItemCommandSession extends Session {
  public ItemCommandSession(Player player, Item item, Session parentSession) {
    super("Command", "アイテムアクション", parentSession);
    setDisplayText(buildItemDetail(item));

    // アイテムのアクションを直接追加
    for (var action : item.getCommands()) {
      action.setTarget(player);
      action.setSource(player);
      addCommand(action);
    }
    addCommand(new QuitCommand(this));
  }

  private String buildItemDetail(Item item) {
    return String.format("【アイテム詳細】\n名前: %s\n説明: %s", item.getName(), item.getDescription());
  }

  @Override
  protected void afterCommandExecuted() {
    stop();
  }
}
