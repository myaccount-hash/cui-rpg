package com.example.entities;

import com.example.items.Weapon;
import com.example.items.Armor;

public abstract class Entity {
   private String name;
   private int hp;
   private int maxHp;
   private int attack;
   private int defence;
   protected Weapon weapon;
   protected Armor armor;
   
   
   public Entity(String name, int hp, int attack, int defence) {
       this.name = name;
       this.hp = hp;
       this.maxHp = hp;
       this.attack = attack;
       this.defence = defence;
       this.weapon = new Weapon.NoWeapon();
       this.armor = new Armor.NoArmor();
   }
   public String getName() { return name; }
   public int getHp() { return hp; }
   public int getMaxHp() { return maxHp; }
   public int getAttack() { return attack; }
   public int getDefence() { return defence; }
   public Weapon getWeapon() { return weapon; }
   public Armor getArmor() { return armor; }
   public void setName(String name) { this.name = name; }
   public void setHp(int hp) { this.hp = hp; }
   public void setMaxHp(int maxHp) { this.maxHp = maxHp; }
   public void setAttack(int attack) { this.attack = attack; }
    /**
     * ダメージを受ける
     */
    public void takeDamage(int damage) {
      this.hp = Math.max(0, this.hp - damage);
  }
  
  /**
   * HPを回復する
   */
  public void heal(int amount) {
      this.hp = Math.min(this.maxHp, this.hp + amount);
  }
}
