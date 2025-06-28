package com.example.sessions;

import com.example.commands.QuitCommand;
import com.example.core.*;
import com.example.entities.Player;
import com.example.items.*;
import java.util.ArrayList;
import java.util.List;

public class ShopSession extends Session {
  private final Player player;
  private final List<Item> itemsForSale;

  public ShopSession(Player player, Session parentSession) {
    super("Shop", "ショップ", parentSession);
    this.player = player;
    this.itemsForSale = initializeItems();

    updateMenu();
    setDisplayText(buildShopInfo());
  }

  private List<Item> initializeItems() {
    List<Item> items = new ArrayList<>();
    items.add(new IronSword());
    items.add(new IronArmor());
    items.add(new HealPotion());
    items.add(new DragonSword());
    return items;
  }

  @Override
  protected void updateMenu() {
    this.commands.clear();
    this.commandNames.clear();

    for (Item item : itemsForSale) {
      addCommand(
          new Command(item.getName(), item.getDescription() + " (" + item.getPrice() + "G)") {
            @Override
            public String getName() {
              int count = getItemCount(item);
              return item.getName() + "(" + count + ")";
            }

            @Override
            public boolean execute() {
              new ItemPurchaseSession(player, item, ShopSession.this).run();
              return true;
            }
          });
    }

    addCommand(new QuitCommand(this));
  }

  private String buildShopInfo() {
    StringBuilder sb = new StringBuilder();
    sb.append("【ショップ】\n所持金: ").append(player.getGold()).append("G\n");
    sb.append("---------------------\n");
    for (Item item : itemsForSale) {
      sb.append(item.getName())
          .append(": ")
          .append(item.getDescription())
          .append(" (")
          .append(item.getPrice())
          .append("G)\n");
    }
    return sb.toString();
  }

  @Override
  protected void afterCommandExecuted() {
    setDisplayText(buildShopInfo());
  }

  // 購入確認セッション
  class ItemPurchaseSession extends Session {
    public ItemPurchaseSession(Player player, Item item, Session parentSession) {
      super("ItemPurchase", "アイテム購入", parentSession);
      setDisplayText(buildItemDetail(item));

      addCommand(
          new Command("buy", "購入する") {
            @Override
            public boolean execute() {
              if (player.getGold() < item.getPrice()) {
                showMessage("お金が足りません。");
                return false;
              }
              player.subtractGold(item.getPrice());
              player.addItem(item);
              showMessage(item.getName() + "を購入しました！");
              stop();
              return true;
            }
          });
      addCommand(new QuitCommand(this));
    }

    private String buildItemDetail(Item item) {
      return String.format(
          "【アイテム詳細】\n名前: %s\n説明: %s\n価格: %dG\n所持金: %dG",
          item.getName(), item.getDescription(), item.getPrice(), player.getGold());
    }

    @Override
    protected void afterCommandExecuted() {
      stop();
    }
  }

  // アイテム所持数取得
  private int getItemCount(Item item) {
    return (int)
        player.getItems().stream().filter(i -> i.getClass().equals(item.getClass())).count();
  }
}
