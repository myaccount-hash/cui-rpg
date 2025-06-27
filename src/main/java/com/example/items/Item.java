package com.example.items;

import com.example.entities.Player;
import java.util.List;

public abstract class Item {
   public String name;
   public String description;
   public Item(String name, String description){
      this.name = name;
      this.description = description;
   }

   public String getDescription() {
      return description;
   }
   public String getName() {
      return name;
   }

   // --- アイテムアクションインターフェース ---
   public interface ItemAction {
      String getName();
      String getLabel();
      boolean execute(Player player, Item item);
   }

   // デフォルトはアクションなし
   public List<ItemAction> getActions() {
      return List.of();
   }
}
