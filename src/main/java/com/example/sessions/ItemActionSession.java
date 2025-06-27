package com.example.sessions;

import com.example.entities.Player;
import com.example.items.Item;

// ItemActionSession - 簡素化版
public class ItemActionSession extends Session {
    public ItemActionSession(Player player, Item item, Session parentSession) {
        super("ItemAction", "アイテムアクション", parentSession);
        setDisplayText(buildItemDetail(item));
        
        // アクションコマンドを動的に追加
        item.getActions().forEach(action -> 
            addCommand(createActionCommand(action, player, item))
        );
    }
    
    private String buildItemDetail(Item item) {
        return String.format("【アイテム詳細】\n名前: %s\n説明: %s", 
                           item.getName(), item.getDescription());
    }
    
    private Command createActionCommand(Item.ItemAction action, Player player, Item item) {
        return new Command(action.getName(), action.getLabel(), action.getName()) {
            @Override
            public boolean execute(String[] args) {
                action.execute(player, item);
                setCommandLog(action.getLabel() + "を実行: " + item.getName());
                return true;
            }
        };
    }
    
    @Override
    protected void afterCommandExecuted() {
        stop();
    }
}