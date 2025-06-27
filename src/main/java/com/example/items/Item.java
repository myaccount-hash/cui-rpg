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

   // --- アイテムアクション ---
   public static abstract class ItemAction extends com.example.actions.Action {
      public ItemAction(String name, String description, String commandName, com.example.entities.Entity source, com.example.entities.Entity target) {
         super(name, description, commandName, source, target);
      }
      public abstract boolean execute(Player player, Item item);
      public abstract String getLabel();
   }

   // デフォルトはアクションなし
   public java.util.List<ItemAction> getActions() {
      return java.util.List.of();
   }
}
