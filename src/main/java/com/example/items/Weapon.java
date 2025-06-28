package com.example.items;

import com.example.entities.Player;
/*
 * 武器の抽象クラス。一部の攻撃を強化する。Entity.javaのweaponフィールドに脱着可能。
 */
public abstract class Weapon extends Item {
  protected int attack;

  public Weapon(String name, String description, int price, int attack) {
    super(name, description, price);
    this.attack = attack;
  }

  public int getAttack() {
    return attack;
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
      if (item instanceof Weapon) {
        player.setWeapon((Weapon) item);
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
