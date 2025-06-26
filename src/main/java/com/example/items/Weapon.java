package com.example.items;

public abstract class Weapon extends Item{
   protected int attack;
   public Weapon(String name, String discription, int attack){
      super(name, discription);
      this.attack = attack;
   }
   public int getAttack() {
      return attack;
   }

   public static class NoWeapon extends Weapon {
       public NoWeapon() {
           super("素手", "何も装備していない", 0);
       }
   }
}
