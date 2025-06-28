package com.example.items;

import com.example.entities.Player;

// 防具の抽象クラス。一部の攻撃を強化する。Entity.javaのarmorフィールドに脱着可能。
public abstract class Armor extends Item {
  protected int defense;

  public Armor(String name, String description, int price, int defense) {
    super(name, description, price);
    this.defense = defense;
  }

  public int getDefense() {
    return defense;
  }


  @Override
  protected java.util.List<Item.ItemAction> createActions() {
    return java.util.List.of(new EquipAction());
  }

  public static class EquipAction extends Item.ItemAction {
    public EquipAction() {
      super("装備", "装備する", "equip", null, null);
    }

    public String getLabel() {
      return "装備する";
    }

    public boolean execute(Player player, Item item) {
      if (item instanceof Armor) {
      player.setArmor((Armor) item);
        setCommandLog(player.getName() + "は" + item.getName() + "を装備した！");
      return true;
      }
      return false;
    }

    @Override
    public boolean execute(String[] args) {
      // セッションからプレイヤーを取得する必要がある場合の実装
      return true;
    }
  }
}
