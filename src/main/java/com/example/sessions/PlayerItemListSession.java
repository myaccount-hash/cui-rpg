package com.example.sessions;

import com.example.entities.Player;
import com.example.commands.QuitCommand;
import com.example.core.Command;
import com.example.core.Session;
import com.example.core.ItemBox.ItemCount;

/*
 * Playerの所有するItemの一覧を表示するセッション。
 * メニューからItemを選ぶとCommandSessionに推移しCommandを選択できる。
 */
public class PlayerItemListSession extends Session {
    private final Player player;

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
        
        // ItemBoxに集計処理を委譲
        for (ItemCount itemCount : player.itemBox.getItemCounts().values()) {
            addCommand(new Command(itemCount.getDisplayName(), itemCount.item.getDescription()) {
                @Override
                public boolean execute() {
                    new ItemCommandSession(player, itemCount.item, PlayerItemListSession.this).run();
                    return true;
                }
            });
        }
        
        addCommand(new QuitCommand(this));
    }
}