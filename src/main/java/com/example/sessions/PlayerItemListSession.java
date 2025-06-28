package com.example.sessions;

import com.example.entities.Player;
import com.example.items.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import com.example.actions.*;
import com.example.commands.Command;
import com.example.commands.QuitCommand;


/*
 * Playerの所有するItemの一覧を表示するセッション。
 * メニューからItemを選ぶとItemActionSessionに推移しItemActionを選択できる。
 */
public class PlayerItemListSession extends Session {
  private final Player player;
  private List<Command> itemCommands = new ArrayList<>();

  public PlayerItemListSession(Player player, Session parentSession) {
    super("ItemList", "所持アイテム一覧", parentSession);
    this.player = player;

    updateMenu();


    setDisplayText(player.getInfoText());
  }

  @Override
  protected void afterCommandExecuted() {
    setDisplayText(player.getInfoText());
    updateMenu();
  }

  @Override
  protected void updateMenu() {
    this.commands.clear();
    this.commandNames.clear();
    itemCommands = new ArrayList<>();
    // アイテムを個数ごとに集計
    Map<String, ItemCount> itemCountMap = new LinkedHashMap<>();
    for (Item item : player.getItems()) {
      if (item != null) {  // nullチェックを追加
        String key = item.getName();
        if (!itemCountMap.containsKey(key)) {
          itemCountMap.put(key, new ItemCount(item, 1));
        } else {
          itemCountMap.get(key).count++;
        }
      }
    }
    // アイテムをコマンドとして追加
    for (ItemCount ic : itemCountMap.values()) {
      String displayName = ic.item.getName();
      if (ic.count > 1) {
        displayName += "(" + ic.count + ")";
      }
      addCommand(
          new Command(displayName, ic.item.getDescription()) {
            @Override
            public boolean execute(String[] args) {
              new ItemActionSession(player, ic.item, PlayerItemListSession.this).run();
              return true;
            }
          });
    }
    addCommand(new QuitCommand(this));
  }

  // アイテム個数管理用クラス
  private static class ItemCount {
    Item item;
    int count;
    ItemCount(Item item, int count) {
      this.item = item;
      this.count = count;
    }
  }
}
