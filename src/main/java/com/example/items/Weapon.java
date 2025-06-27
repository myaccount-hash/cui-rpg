package com.example.items;

import com.example.entities.Player;
/*
 * 武器の抽象クラス。一部の攻撃を強化する。Entity.javaのweaponフィールドに脱着可能。
 */
public abstract class Weapon extends Item {
  protected int attack;

  public Weapon(String name, String description, int attack) {
    super(name, description);
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
  protected java.util.List<Item.ItemAction> createActions() {
    return java.util.List.of(new EquipAction(), new UnequipAction());
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

  public static class UnequipAction extends Item.ItemAction {
    public UnequipAction() {
      super("外す", "外す", "unequip", null, null);
    }

    public String getLabel() {
      return "外す";
    }

    public boolean execute(Player player, Item item) {
      player.setWeapon(new NoWeapon());
      setCommandLog(player.getName() + "は" + item.getName() + "を外した！");
      return true;
    }

    @Override
    public boolean execute(String[] args) {
      // セッションからプレイヤーを取得する必要がある場合の実装
      return true;
    }
  }
}
