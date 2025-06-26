package com.example.sessions;

import com.example.entities.Player;
import com.example.items.Item;
import java.util.List;
import com.example.commands.Command;

public class PlayerItemListSession extends Session {
    private final List<Item> items;
    private String detailText = "アイテムを選択してください。";

    public PlayerItemListSession(Player player, Session parentSession) {
        super("ItemList", "所持アイテム一覧", parentSession);
        this.items = player.getItems();
        running = true;
        // アイテムをコマンドとして追加
        for (int i = 0; i < items.size(); i++) {
            final int idx = i;
            addCommand(new Command(items.get(i).getName(), items.get(i).getDiscription(), items.get(i).getName()) {
                @Override
                public boolean execute(String[] args) {
                    Item item = items.get(idx);
                    detailText = buildItemDetail(item);
                    setDisplayText(detailText);
                    return true;
                }
            });
        }
        addCommand(new QuitCommand());
        setDisplayText(detailText);
        refreshDisplay();
        while (isRunning()) {
            String input = scanner.nextLine();
            if (isLogDisplaying()) {
                showLog();
                continue;
            }
            if (!input.trim().isEmpty()) {
                processInput(input.trim());
            }
        }
    }
    private String buildItemDetail(Item item) {
        return "【アイテム詳細】\n" +
               "名前: " + item.getName() + "\n" +
               "説明: " + item.getDiscription();
    }
} 