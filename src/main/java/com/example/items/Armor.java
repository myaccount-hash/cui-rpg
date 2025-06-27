package com.example.items;

import com.example.entities.Player;

// 防具の抽象クラス。一部の攻撃を強化する。Entity.javaのarmorフィールドに脱着可能。
public abstract class Armor extends Item {
  protected int defense;

  public Armor(String name, String description, int defense) {
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

    @Override
    public boolean equals(Object obj) {
      return obj instanceof NoArmor;
    }

    @Override
    public int hashCode() {
      return 0;
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

  public static class UnequipAction extends Item.ItemAction {
    public UnequipAction() {
      super("外す", "外す", "unequip", null, null);
    }

    public String getLabel() {
      return "外す";
    }

    public boolean execute(Player player, Item item) {
      // 外す時は現在の防具をアイテムリストに戻す
      if (!(player.getArmor() instanceof NoArmor)) {
        player.addItem(player.getArmor());
      }
      player.setArmor(new NoArmor());
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
