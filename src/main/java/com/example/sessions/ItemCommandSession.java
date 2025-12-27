package com.example.sessions;

import com.example.actions.Action;
import com.example.commands.ActionCommandAdapter;
import com.example.commands.QuitCommand;
import com.example.entities.Entity;
import com.example.items.Item;

/*
 * 特定のItemに対して行うCommandを選ぶセッション．
 */
public class ItemCommandSession extends Session {
  public ItemCommandSession(Item item, Session parentSession, Entity sessionOwner) {
    super("Command", "アイテムアクション", parentSession, sessionOwner);
    setDisplayText(buildItemDetail(item));

    // TODO:バトルセッション中でのターゲットを適切に選択
    var itemActions = item.getActions(sessionOwner);
    for (Action action : itemActions) {
      action.setTarget(sessionOwner);
      addCommand(new ActionCommandAdapter(action));
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
