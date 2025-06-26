package com.example.sessions;

import com.example.core.Command;
import com.example.core.Session;
import com.example.entities.Player;
import com.example.items.Item;
import java.util.List;


public class PlayerItemListSession extends Session {
    private final List<Item> items;
    private Player player;
    

    public PlayerItemListSession(Player player, Session parentSession) {
        super("ItemList", "所持アイテム一覧", parentSession);
        this.player = player;
        this.items = player.getItems();
        running = true;
        // アイテムをコマンドとして追加
        for (int i = 0; i < items.size(); i++) {
            final int idx = i;
            addCommand(new Command(items.get(i).getName(), items.get(i).getDiscription(), items.get(i).getName()) {
                @Override
                public boolean execute(String[] args) {
                    Item item = items.get(idx);
                    new ItemActionSession(player, item, PlayerItemListSession.this);
                    return true;
                }
            });
        }
        addCommand(new QuitCommand());
        setDisplayText(player.getInfoText());
        refreshDisplay();
        while (isRunning()) {
            String input = scanner.nextLine();
            if (isLogDisplaying()) {
                showLog();
                continue;
            }
            if (!input.trim().isEmpty()) {
                processInput(input.trim());
                setDisplayText(player.getInfoText());
                refreshDisplay();
            }
        }
        parentSession.refreshDisplay();
    }
} 