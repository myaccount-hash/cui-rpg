package com.example.sessions;

import com.example.commands.QuitCommand;
import com.example.core.*;
import com.example.entities.Player;
import com.example.items.Item;
import com.example.utils.TargetUtils;

/*
 * 特定のItemに対して行うCommandを選ぶセッション。
 */
public class ItemCommandSession extends Session {
  public ItemCommandSession(Player player, Item item, Session parentSession) {
    super("Command", "アイテムアクション", parentSession);
    setDisplayText(buildItemDetail(item));

    // アイテムのアクションを適切なターゲット設定で追加
    var itemCommands = TargetUtils.getItemCommands(player, item.getCommands(player));
    for (Command action : itemCommands) {
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
