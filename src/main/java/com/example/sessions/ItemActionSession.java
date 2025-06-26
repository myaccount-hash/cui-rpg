package com.example.sessions;

import com.example.core.Command;
import com.example.core.Session;
import com.example.entities.Player;
import com.example.items.Item;
import com.example.items.Weapon;
import com.example.items.Armor;

import java.util.List;

public class ItemActionSession extends Session {
    public ItemActionSession(Player player, Item item, Session parentSession) {
        super("ItemAction", "アイテムアクション", parentSession);
        setDisplayText(buildItemDetail(item));
        for (Item.ItemAction action : item.getActions()) {
            addCommand(new Command(action.getName(), action.getLabel(), action.getName()) {
                @Override
                public boolean execute(String[] args) {
                    action.execute(player, item);
                    setCommandLog(action.getLabel() + "を実行しました: " + item.getName());
                    return true;
                }
            });
        }
        addCommand(new QuitCommand()); // 戻る
        // ループは親クラスrun()に任せる
    }
    private String buildItemDetail(Item item) {
        return "【アイテム詳細】\n" +
               "名前: " + item.getName() + "\n" +
               "説明: " + item.getDiscription();
    }
    @Override
    protected void afterCommandExecuted() {
        stop();
    }
} 