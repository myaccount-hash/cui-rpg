package com.example.items;

import com.example.entities.Player;
import com.example.commands.Command;
import java.util.List;

/* 
 * アイテムの抽象クラス。装備, 消費等のアクションは内部クラスでCommandとして記述する。
 */ 
public abstract class Item {
   protected String name;
   protected String description;
   protected int price;
   public Item(String name, String description, int price){
      this.name = name;
      this.description = description;
      this.price =price;
   }

   public String getDescription() {
      return description;
   }
   public String getName() {
      return name;
   }


   protected abstract List<Command> createActions();

   public final List<Command> getActions() {
      return createActions();
   }
}
