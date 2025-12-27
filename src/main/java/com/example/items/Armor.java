package com.example.items;

import java.util.List;

import com.example.actions.Action;
import com.example.entities.Entity;

// 防具の抽象クラス．Entity.javaのarmorフィールドに脱着可能．
public abstract class Armor extends Item {
  protected int defense;

  public Armor(String name, String description, int price, Entity owner, int defense) {
    super(name, description, price, owner);
    this.defense = defense;
  }

  public int getDefense() {
    return defense;
  }

  @Override
  protected List<Action> createActions(Entity source) {
    return List.of(new EquipAction(source));
  }

  public class EquipAction extends Action {
    public EquipAction(Entity executor) {
      super("装備", "装備する", executor);
    }

    public String getLabel() {
      return "装備する";
    }
    @Override
    public boolean execute() {
      executor.setArmor((Armor) Armor.this);
      setCommandLog(executor.getName() + "は" + Armor.this.getName() + "を装備した！");
      return true;
    }
  }
}
