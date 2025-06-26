package com.example.items;

public abstract class Armor extends Item{
   protected int defense;
   public Armor(String name, String discription, int defense){
      super(name, discription);
      this.defense = defense;
   }
   public int getDefense() {
      return defense;
   }
}
