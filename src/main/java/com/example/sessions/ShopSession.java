package com.example.sessions;

import com.example.commands.Command;
import com.example.commands.QuitCommand;
import com.example.entities.IEntity;
import com.example.items.DragonSword;
import com.example.items.HealPotion;
import com.example.items.IItem;
import com.example.items.IronArmor;
import com.example.items.IronSword;
import java.util.ArrayList;
import java.util.List;

public class ShopSession extends Session {
  private final List<IItem> IItemsForSale;

  public ShopSession(Session parentSession, IEntity sessionOwner) {
    super("Shop", "ショップ", parentSession, sessionOwner);
    this.IItemsForSale = initializeItems();

    updateMenu();
    setDisplayText(buildShopInfo());
  }

  private List<IItem> initializeItems() {
    List<IItem> IItems = new ArrayList<>();
    // TODO: 店主のエンティティを作り、アイテムの受け渡しで実装。
    IItems.add(new IronSword(sessionOwner));
    IItems.add(new IronArmor(sessionOwner));
    IItems.add(new HealPotion(sessionOwner));
    IItems.add(new DragonSword(sessionOwner));
    return IItems;
  }

  @Override
  protected void updateMenu() {
    this.commands.clear();
    this.commandNames.clear();

    for (IItem IItem : IItemsForSale) {
      addCommand(
          new Command(
              IItem.getName(),
              IItem.getDescription() + " (" + IItem.getPrice() + "G)",
              sessionOwner) {
            @Override
            public String getName() {
              int count = getItemCount(IItem);
              return IItem.getName() + "(" + count + ")";
            }

            @Override
            public boolean execute() {
              new IItemPurchaseSession(sessionOwner, IItem, ShopSession.this).run();
              return true;
            }
          });
    }

    addCommand(new QuitCommand(this, sessionOwner));
  }

  private String buildShopInfo() {
    StringBuilder sb = new StringBuilder();
    sb.append("【ショップ】\n所持金: ").append(sessionOwner.getGold()).append("G\n");
    sb.append("---------------------\n");
    for (IItem IItem : IItemsForSale) {
      sb.append(IItem.getName())
          .append(": ")
          .append(IItem.getDescription())
          .append(" (")
          .append(IItem.getPrice())
          .append("G)\n");
    }
    return sb.toString();
  }

  @Override
  protected void afterCommandExecuted() {
    setDisplayText(buildShopInfo());
  }

  // 購入確認セッション
  class IItemPurchaseSession extends Session {
    public IItemPurchaseSession(IEntity sessionOwner, IItem IItem, Session parentSession) {
      super("ItemPurchase", "アイテム購入", parentSession, sessionOwner);
      setDisplayText(buildItemDetail(IItem));

      addCommand(
          new Command("buy", "購入する", sessionOwner) {
            @Override
            public boolean execute() {
              if (sessionOwner.getGold() < IItem.getPrice()) {
                showMessage("お金が足りません。");
                return false;
              }
              sessionOwner.subtractGold(IItem.getPrice());
              sessionOwner.addItem(IItem);
              showMessage(IItem.getName() + "を購入しました！");
              stop();
              return true;
            }
          });
      addCommand(new QuitCommand(this, sessionOwner));
    }

    private String buildItemDetail(IItem IItem) {
      return String.format(
          "【アイテム詳細】\n名前: %s\n説明: %s\n価格: %dG\n所持金: %dG",
          IItem.getName(), IItem.getDescription(), IItem.getPrice(), sessionOwner.getGold());
    }

    @Override
    protected void afterCommandExecuted() {
      stop();
    }
  }

  // アイテム所持数取得
  private int getItemCount(IItem IItem) {
    return (int)
        sessionOwner.getItems().stream().filter(i -> i.getClass().equals(IItem.getClass())).count();
  }
}
