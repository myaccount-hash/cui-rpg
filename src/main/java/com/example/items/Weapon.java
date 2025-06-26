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
}
