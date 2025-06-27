package com.example.sessions;

import com.example.entities.Player;
import com.example.items.IronSword;
import com.example.items.IronArmor;
import com.example.items.Item;
import java.util.ArrayList;
import java.util.List;

public class ShopSession extends Session {
    private final Player player;
    private final List<ItemForSale> itemsForSale;

    public ShopSession(Player player, Session parentSession) {
        super("Shop", "ショップ", parentSession);
        this.player = player;
        this.itemsForSale = new ArrayList<>();
        itemsForSale.add(new ItemForSale(new IronSword(), 500));
        itemsForSale.add(new ItemForSale(new IronArmor(), 400));
        itemsForSale.add(new ItemForSale(new com.example.items.HealPotion(), 100));
        itemsForSale.add(new ItemForSale(new com.example.items.DragonSword(), 2000));

        List<Command> itemCommands = new ArrayList<>();
        for (ItemForSale itemForSale : itemsForSale) {
            itemCommands.add(new Command(itemForSale.item.getName(), itemForSale.item.getDescription() + " (" + itemForSale.price + "G)", itemForSale.item.getName()) {
                @Override
                public String getName() {
                    int count = getItemCount(itemForSale.item);
                    return itemForSale.item.getName() + "(" + count + ")";
                }
                @Override
                public boolean execute(String[] args) {
                    new ItemPurchaseSession(player, itemForSale.item, itemForSale.price, ShopSession.this).run();
                    setDisplayText(buildShopInfo());
                    return true;
                }
            });
        }
        addCommands(itemCommands);
        addCommand(new Command("sell", "アイテムを売却する", "sell") {
            @Override
            public boolean execute(String[] args) {
                new SellItemListSession(player, ShopSession.this).run();
                setDisplayText(buildShopInfo());
                return true;
            }
        });
        addCommand(new QuitCommand());
        setDisplayText(buildShopInfo());
    }

    private String buildShopInfo() {
        StringBuilder sb = new StringBuilder();
        sb.append("【ショップ】\n");
        sb.append("所持金: ").append(player.getGold()).append("G\n");
        sb.append("---------------------\n");
        for (ItemForSale item : itemsForSale) {
            sb.append(item.item.getName()).append(": ")
              .append(item.item.getDescription()).append(" (")
              .append(item.price).append("G)\n");
        }
        return sb.toString();
    }

    private static class ItemForSale {
        Item item;
        int price;
        ItemForSale(Item item, int price) {
            this.item = item;
            this.price = price;
        }
    }

    @Override
    protected void afterCommandExecuted() {
        setDisplayText(buildShopInfo());
    }

    // アイテム購入確認用セッション
    class ItemPurchaseSession extends Session {
        public ItemPurchaseSession(Player player, Item item, int price, Session parentSession) {
            super("ItemPurchase", "アイテム購入", parentSession);
            setDisplayText(buildItemDetail(item, price, player));

            addCommand(new Command("buy", "購入する", "buy") {
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
            addCommand(new QuitCommand());
        }

        private String buildItemDetail(Item item, int price, Player player) {
            return String.format("【アイテム詳細】\n名前: %s\n説明: %s\n価格: %dG\n所持金: %dG", item.getName(), item.getDescription(), price, player.getGold());
        }

        @Override
        protected void afterCommandExecuted() {
            stop();
        }
    }

    // 売却用アイテム一覧セッション
    class SellItemListSession extends Session {
        public SellItemListSession(Player player, Session parentSession) {
            super("SellItemList", "売却アイテム一覧", parentSession);
            List<Command> itemCommands = new ArrayList<>();
            for (Item item : player.getItems()) {
                int sellPrice = getSellPrice(item);
                int count = getItemCount(item);
                String displayName = item.getName();
                if (count > 1) {
                    displayName += "(" + count + ")";
                }
                itemCommands.add(new Command(displayName, item.getDescription() + " (" + sellPrice + "Gで売却)", item.getName()) {
                    @Override
                    public boolean execute(String[] args) {
                        new SellItemSession(player, item, sellPrice, SellItemListSession.this).run();
                        setDisplayText(buildSellListInfo(player));
                        return true;
                    }
                });
            }
            addCommands(itemCommands);
            addCommand(new QuitCommand());
            setDisplayText(buildSellListInfo(player));
        }

        private String buildSellListInfo(Player player) {
            StringBuilder sb = new StringBuilder();
            sb.append("【売却アイテム一覧】\n");
            sb.append("所持金: ").append(player.getGold()).append("G\n");
            sb.append("---------------------\n");
            List<Item> alreadyListed = new ArrayList<>();
            for (Item item : player.getItems()) {
                if (alreadyListed.stream().anyMatch(i -> i.getClass().equals(item.getClass()))) continue;
                int sellPrice = getSellPrice(item);
                int count = getItemCount(item);
                sb.append(item.getName()).append(": ")
                  .append(item.getDescription()).append(" (")
                  .append(sellPrice).append("Gで売却, 所持: ").append(count).append(")\n");
                alreadyListed.add(item);
            }
            return sb.toString();
        }
    }

    // 売却確認セッション
    class SellItemSession extends Session {
        public SellItemSession(Player player, Item item, int sellPrice, Session parentSession) {
            super("SellItem", "アイテム売却", parentSession);
            setDisplayText(buildItemDetail(item, sellPrice, player));

            addCommand(new Command("sell", "売却する", "sell") {
                @Override
                public boolean execute(String[] args) {
                    player.addGold(sellPrice);
                    player.getItems().remove(item);
                    showMessage(item.getName() + "を" + sellPrice + "Gで売却しました！");
                    stop();
                    return true;
                }
            });
            addCommand(new QuitCommand());
        }

        private String buildItemDetail(Item item, int sellPrice, Player player) {
            return String.format("【アイテム詳細】\n名前: %s\n説明: %s\n売却価格: %dG\n所持金: %dG", item.getName(), item.getDescription(), sellPrice, player.getGold());
        }

        @Override
        protected void afterCommandExecuted() {
            stop();
        }
    }

    // 購入価格から売却価格を計算（半額、切り捨て）
    private int getSellPrice(Item item) {
        for (ItemForSale ifs : itemsForSale) {
            if (ifs.item.getClass().equals(item.getClass())) {
                return ifs.price / 2;
            }
        }
        return 0;
    }

    // 指定アイテムの所持数を返す
    private int getItemCount(Item item) {
        int count = 0;
        for (Item i : player.getItems()) {
            if (i.getClass().equals(item.getClass())) {
                count++;
            }
        }
        return count;
    }
} 