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

   public static class NoArmor extends Armor {
       public NoArmor() {
           super("裸", "何も装備していない", 0);
       }
   }
}
