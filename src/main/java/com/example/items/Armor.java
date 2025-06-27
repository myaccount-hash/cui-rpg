package com.example.items;

import com.example.entities.Player;
import java.util.List;

public abstract class Armor extends Item{
   protected int defense;
   public Armor(String name, String description, int defense){
      super(name, description);
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

   @Override
   public List<ItemAction> getActions() {
       return List.of(new EquipAction(), new UnequipAction());
   }

   public static class EquipAction implements ItemAction {
       public String getName() { return "equip"; }
       public String getLabel() { return "装備する"; }
       public boolean execute(Player player, Item item) {
           player.setArmor((Armor)item);
           return true;
       }
   }
   public static class UnequipAction implements ItemAction {
       public String getName() { return "unequip"; }
       public String getLabel() { return "外す"; }
       public boolean execute(Player player, Item item) {
           player.setArmor(new NoArmor());
           return true;
       }
   }
}
