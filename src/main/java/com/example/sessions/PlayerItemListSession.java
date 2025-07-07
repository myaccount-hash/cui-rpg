package com.example.sessions;

import com.example.commands.Command;
import com.example.commands.QuitCommand;
import com.example.entities.IEntity;
import com.example.entities.ItemBox.IItemCount;

/*
 * Playerの所有するItemの一覧を表示するセッション。
 * メニューからItemを選ぶとCommandSessionに推移しCommandを選択できる。
 */
public class PlayerItemListSession extends Session {

  public PlayerItemListSession(Session parentSession, IEntity sessionOwner) {
    super("ItemList", "所持アイテム一覧", parentSession, sessionOwner);
    updateMenu();
    setDisplayText(sessionOwner.getInfoText());
  }

  @Override
  protected void afterCommandExecuted() {
    setDisplayText(sessionOwner.getInfoText());
    updateMenu();
  }

  @Override
  protected void updateMenu() {
    this.commands.clear();
    this.commandNames.clear();

    // ItemBoxに集計処理を委譲
    for (IItemCount IItemCount : sessionOwner.getItemBox().getItemCounts().values()) {
      addCommand(
          new Command(
              IItemCount.getDisplayName(), IItemCount.IItem.getDescription(), sessionOwner) {
            @Override
            public boolean execute() {
              new IItemCommandSession(IItemCount.IItem, PlayerItemListSession.this, sessionOwner)
                  .run();
              return true;
            }
          });
    }

    addCommand(new QuitCommand(this, sessionOwner));
  }
}
