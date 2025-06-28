package com.example.items;

import com.example.entities.Player;
import java.util.List;

public class HealPotion extends Item {
    public HealPotion() {
        super("ヒールポーション", "HPを50回復", 10);
    }

    @Override
    protected List<ItemAction> createActions() {
        return List.of(new UseAction());
    }

    public static class UseAction extends ItemAction {
        public UseAction() {
            super("使う", "HPを50回復する", "use", null, null);
        }

        @Override
        public boolean execute(Player player, Item item) {
            player.heal(50);
            setCommandLog(player.getName() + "はヒールポーションを使い、HPが50回復した！");
            // 消費アイテムなのでリストから削除（実際のセッションで削除処理を追加する必要あり）
            player.getItems().remove(item);
            return true;
        }

        @Override
        public String getLabel() {
            return "使う";
        }

        @Override
        public boolean execute(String[] args) {
            // 未使用
            return true;
        }
    }
} 