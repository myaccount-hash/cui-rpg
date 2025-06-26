package com.example.items;

import com.example.entities.Player;
import java.util.List;

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

   @Override
   public List<ItemAction> getActions() {
       return List.of(new EquipAction(), new UnequipAction());
   }

   public static class EquipAction implements ItemAction {
       public String getName() { return "equip"; }
       public String getLabel() { return "装備する"; }
       public boolean execute(Player player, Item item) {
           player.setWeapon((Weapon)item);
           return true;
       }
   }
   public static class UnequipAction implements ItemAction {
       public String getName() { return "unequip"; }
       public String getLabel() { return "外す"; }
       public boolean execute(Player player, Item item) {
           player.setWeapon(new NoWeapon());
           return true;
       }
   }
}
