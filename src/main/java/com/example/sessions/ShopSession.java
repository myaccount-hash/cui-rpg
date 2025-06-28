package com.example.sessions;

import com.example.entities.Player;
import com.example.items.*;
import java.util.ArrayList;
import java.util.List;
import com.example.actions.*;
import com.example.commands.Command;
import com.example.commands.QuitCommand;

public class ShopSession extends Session {
    private final Player player;
    private final List<ItemForSale> itemsForSale;

    public ShopSession(Player player, Session parentSession) {
        super("Shop", "ショップ", parentSession);
        this.player = player;
        this.itemsForSale = initializeItems();
        
        // アイテム購入コマンド作成
        updateMenu();    
        setDisplayText(buildShopInfo());
    }

    private List<ItemForSale> initializeItems() {
        List<ItemForSale> items = new ArrayList<>();
        items.add(new ItemForSale(new IronSword(), 500));
        items.add(new ItemForSale(new IronArmor(), 400));
        items.add(new ItemForSale(new HealPotion(), 100));
        items.add(new ItemForSale(new DragonSword(), 2000));
        return items;
    }
    @Override
    protected void updateMenu() {
        this.commands.clear();
        this.commandNames.clear();
        
        // アイテム購入コマンド作成
        for (ItemForSale itemForSale : itemsForSale) {
            addCommand(new Command(itemForSale.item.getName(), 
                                  itemForSale.item.getDescription() + " (" + itemForSale.price + "G)"
                                  ) {
                @Override
                public String getName() {
                    int count = getItemCount(itemForSale.item);
                    return itemForSale.item.getName() + "(" + count + ")";
                }
                
                @Override
                public boolean execute(String[] args) {
                    new ItemPurchaseSession(player, itemForSale.item, itemForSale.price, ShopSession.this).run();
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
        for (ItemForSale item : itemsForSale) {
            sb.append(item.item.getName()).append(": ")
              .append(item.item.getDescription()).append(" (")
              .append(item.price).append("G)\n");
        }
        return sb.toString();
    }

    private static class ItemForSale {
        final Item item;
        final int price;
        ItemForSale(Item item, int price) {
            this.item = item;
            this.price = price;
        }
    }

    @Override
    protected void afterCommandExecuted() {
        setDisplayText(buildShopInfo());
    }

    // 購入確認セッション
    class ItemPurchaseSession extends Session {
        public ItemPurchaseSession(Player player, Item item, int price, Session parentSession) {
            super("ItemPurchase", "アイテム購入", parentSession);
            setDisplayText(buildItemDetail(item, price));

            addCommand(new Command("buy", "購入する") {
                @Override
                public boolean execute(String[] args) {
                    if (player.getGold() < price) {
                        showMessage("お金が足りません。");
                        return false;
                    }
                    player.subtractGold(price);
                    player.addItem(item);
                    showMessage(item.getName() + "を購入しました！");
                    stop();
                    return true;
                }
            });
            addCommand(new QuitCommand(this));
        }

        private String buildItemDetail(Item item, int price) {
            return String.format("【アイテム詳細】\n名前: %s\n説明: %s\n価格: %dG\n所持金: %dG", 
                               item.getName(), item.getDescription(), price, player.getGold());
        }

        @Override
        protected void afterCommandExecuted() {
            stop();
        }
    }

    // アイテム所持数取得
    private int getItemCount(Item item) {
        return (int) player.getItems().stream()
                          .filter(i -> i.getClass().equals(item.getClass()))
                          .count();
    }
}