package com.example.sessions;

import java.util.ArrayList;
import java.util.List;

import com.example.commands.Command;
import com.example.commands.QuitCommand;
import com.example.entities.Entity;
import com.example.items.DragonSword;
import com.example.items.HealPotion;
import com.example.items.IronArmor;
import com.example.items.IronSword;
import com.example.items.Item;

public class ShopSession extends Session {
  private final List<Item> itemsForSale;

  public ShopSession(Session parentSession, Entity sessionOwner) {
    super("Shop", "ショップ", parentSession, sessionOwner);
    this.itemsForSale = initializeItems();

    setDisplayText(buildShopInfo());
  }

  private List<Item> initializeItems() {
    List<Item> items = new ArrayList<>();
    // TODO: 店主のエンティティを作り，アイテムの受け渡しで実装．
    items.add(new IronSword(sessionOwner));
    items.add(new IronArmor(sessionOwner));
    items.add(new HealPotion(sessionOwner));
    items.add(new DragonSword(sessionOwner));
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
              new ItemPurchaseSession(sessionOwner, item, ShopSession.this).run();
              return true;
            }
          });
    }

    addCommand(new QuitCommand(this));
  }

  private String buildShopInfo() {
    StringBuilder sb = new StringBuilder();
    sb.append("【ショップ】\n所持金: ").append(sessionOwner.getGold()).append("G\n");
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
    public ItemPurchaseSession(Entity sessionOwner, Item item, Session parentSession) {
      super("ItemPurchase", "アイテム購入", parentSession, sessionOwner);
      setDisplayText(buildItemDetail(item));

      addCommand(
          new Command("buy", "購入する") {
            @Override
            public boolean execute() {
              if (sessionOwner.getGold() < item.getPrice()) {
                showMessage("お金が足りません．");
                return false;
              }
              sessionOwner.subtractGold(item.getPrice());
              sessionOwner.addItem(item);
              showMessage(item.getName() + "を購入しました！");
              stop();
              return true;
            }
          });
      addCommand(
          new Command("error", "意図的にエラーを発生させる") {
            @Override
            public boolean execute() {
              throw new RuntimeException("デバッグ用: 意図的に発生させたエラー");
            }
          });
      addCommand(new QuitCommand(this));
    }

    private String buildItemDetail(Item item) {
      return String.format(
          "【アイテム詳細】\n名前: %s\n説明: %s\n価格: %dG\n所持金: %dG",
          item.getName(), item.getDescription(), item.getPrice(), sessionOwner.getGold());
    }

    @Override
    protected void afterCommandExecuted() {
      stop();
    }
  }

  // アイテム所持数取得
  private int getItemCount(Item item) {
    return (int)
        sessionOwner.getItems().stream().filter(i -> i.getClass().equals(item.getClass())).count();
  }
}
