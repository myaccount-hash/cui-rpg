package com.example.items;

import com.example.entities.Player;

/* 
 * アイテムの抽象クラス。装備, 消費等のアクションは内部クラスでItemActionとして記述する。
 */ 
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

   /* 
    * アイテムに対し行うことができるアクション。サブクラスでも内部クラスとして記述。装備の脱着・消費等。
    */ 
   public static abstract class ItemAction extends com.example.actions.Action {
      public ItemAction(String name, String description, String commandName, com.example.entities.Entity source, com.example.entities.Entity target) {
         super(name, description, commandName, source, target);
      }
      public abstract boolean execute(Player player, Item item);
      public abstract String getLabel();
   }

   protected abstract java.util.List<ItemAction> createActions();

   public final java.util.List<ItemAction> getActions() {
      return createActions();
   }
}
