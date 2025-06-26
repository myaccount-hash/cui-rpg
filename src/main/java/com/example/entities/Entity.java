package com.example.entities;

import com.example.items.Weapon;
import com.example.items.Armor;

public abstract class Entity {
   private String name;
   private int hp;
   private int maxHp;
   private int attack;
   private int defence;
   private Weapon weapon;
   private Armor armor;
   
   
   protected Entity(String name, int hp, int attack, int defence) {
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
   public int getAttack() { return attack + weapon.getAttack(); }
   public int getDefence() { return defence + armor.getDefense(); }
   public Weapon getWeapon() { return weapon; }
   public Armor getArmor() { return armor; }
   protected void setName(String name) { this.name = name; }
   protected void setHp(int hp) { this.hp = hp; }
   protected void setMaxHp(int maxHp) { this.maxHp = maxHp; }
   protected void setAttack(int attack) { this.attack = attack; }
   public void setWeapon(Weapon weapon) { this.weapon = weapon; }
   public void setArmor(Armor armor) { this.armor = armor; }
   public void unequipWeapon() { this.weapon = new Weapon.NoWeapon(); }
   public void unequipArmor() { this.armor = new Armor.NoArmor(); }
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

  protected String getInfoText() {
      return "名前: " + getName() +
             "\nHP: " + getHp() + "/" + getMaxHp() +
             "\n攻撃力: " + getAttack() +
             "\n防御力: " + getDefence();
  }
}
