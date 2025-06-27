package com.example.sessions;

import com.example.entities.Player;
import com.example.items.Item;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;

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
    addCommands(itemCommands);
    addCommand(new QuitCommand());

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
      String key = item.getName();
      if (!itemCountMap.containsKey(key)) {
        itemCountMap.put(key, new ItemCount(item, 1));
      } else {
        itemCountMap.get(key).count++;
      }
    }
    // アイテムをコマンドとして追加
    for (ItemCount ic : itemCountMap.values()) {
      String displayName = ic.item.getName();
      if (ic.count > 1) {
        displayName += "(" + ic.count + ")";
      }
      itemCommands.add(
          new Command(displayName, ic.item.getDescription(), ic.item.getName()) {
            @Override
            public boolean execute(String[] args) {
              new ItemActionSession(player, ic.item, PlayerItemListSession.this).run();
              return true;
            }
          });
    }
    // 現在装備中の武器・防具の脱着コマンドを追加
    if (!(player.getWeapon() instanceof com.example.items.Weapon.NoWeapon)) {
      itemCommands.add(new Command(player.getWeapon().getName() + "(装備中)を外す", "現在装備中の武器を外します", "unequip_weapon") {
        @Override
        public boolean execute(String[] args) {
          new com.example.items.Weapon.UnequipAction().execute(player, player.getWeapon());
          showMessage("武器を外しました。");
          return true;
        }
      });
    }
    if (!(player.getArmor() instanceof com.example.items.Armor.NoArmor)) {
      itemCommands.add(new Command(player.getArmor().getName() + "(装備中)を外す", "現在装備中の防具を外します", "unequip_armor") {
        @Override
        public boolean execute(String[] args) {
          new com.example.items.Armor.UnequipAction().execute(player, player.getArmor());
          showMessage("防具を外しました。");
          return true;
        }
      });
    }
    // 一括でコマンドを追加
    addCommands(itemCommands);
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
