package com.example.items;

import com.example.entities.Player;
import java.util.List;

public abstract class Item {
   public String name;
   public String discription;
   public Item(String name, String discription){
      this.name = name;
      this.discription = discription;
   }

   public String getDiscription() {
      return discription;
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
