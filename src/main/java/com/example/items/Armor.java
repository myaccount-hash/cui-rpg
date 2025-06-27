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
   public java.util.List<Item.ItemAction> getActions() {
       return java.util.List.of(new EquipAction(), new UnequipAction());
   }

   public static class EquipAction extends Item.ItemAction {
       public EquipAction() {
           super("装備", "装備する", "equip", null, null);
       }
       public String getName() { return "equip"; }
       public String getLabel() { return "装備する"; }
       public boolean execute(Player player, Item item) {
           player.setArmor((Armor)item);
           return true;
       }
       @Override
       public boolean execute(String[] args) {
           // 空実装
           return true;
       }
   }
   public static class UnequipAction extends Item.ItemAction {
       public UnequipAction() {
           super("外す", "外す", "unequip", null, null);
       }
       public String getName() { return "unequip"; }
       public String getLabel() { return "外す"; }
       public boolean execute(Player player, Item item) {
           player.setArmor(new NoArmor());
           return true;
       }
       @Override
       public boolean execute(String[] args) {
           // 空実装
           return true;
       }
   }
}
