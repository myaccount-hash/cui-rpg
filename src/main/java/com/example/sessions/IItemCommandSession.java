package com.example.sessions;

import com.example.commands.ICommand;
import com.example.commands.QuitCommand;
import com.example.entities.IEntity;
import com.example.items.IItem;

/*
 * 特定のItemに対して行うCommandを選ぶセッション。
 */
public class IItemCommandSession extends Session {
  public IItemCommandSession(IItem IItem, Session parentSession, IEntity sessionOwner) {
    super("Command", "アイテムアクション", parentSession, sessionOwner);
    setDisplayText(buildItemDetail(IItem));

    // TODO:バトルセッション中でのターゲットを適切に選択
    var IItemCommands = IItem.getCommands(sessionOwner);
    for (ICommand action : IItemCommands) {
      action.setTarget(sessionOwner);
      addCommand(action);
    }
    addCommand(new QuitCommand(this, sessionOwner));
  }

  private String buildItemDetail(IItem IItem) {
    return String.format("【アイテム詳細】\n名前: %s\n説明: %s", IItem.getName(), IItem.getDescription());
  }

  @Override
  protected void afterCommandExecuted() {
    stop();
  }
}
