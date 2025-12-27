package com.example.sessions;

import com.example.commands.Command;
import com.example.commands.QuitCommand;
import com.example.entities.Entity;
import com.example.entities.Entity.ItemBox.ItemCount;

/*
 * Playerの所有するItemの一覧を表示するセッション．
 * メニューからItemを選ぶとCommandSessionに推移しCommandを選択できる．
 */
public class PlayerItemListSession extends Session {

  public PlayerItemListSession(Session parentSession, Entity sessionOwner) {
    super("ItemList", "所持アイテム一覧", parentSession, sessionOwner);
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
    for (ItemCount itemCount : sessionOwner.getItemBox().getItemCounts().values()) {
      addCommand(
          new Command(itemCount.getDisplayName(), itemCount.item.getDescription()) {
            @Override
            public boolean execute() {
              new ItemCommandSession(itemCount.item, PlayerItemListSession.this, sessionOwner)
                  .run();
              return true;
            }
          });
    }

    addCommand(new QuitCommand(this));
  }
}
