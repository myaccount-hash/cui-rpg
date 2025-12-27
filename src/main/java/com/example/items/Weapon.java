package com.example.items;

import java.util.List;

import com.example.actions.Action;
import com.example.entities.Entity;

/*
 * 武器の抽象クラス．一部の攻撃を強化する．Entity.javaのweaponフィールドに脱着可能．
 */
public abstract class Weapon extends Item {
  protected int attack;

  public Weapon(String name, String description, int price, Entity owner, int attack) {
    super(name, description, price, owner);
    this.attack = attack;
  }

  public int getAttack() {
    return attack;
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
      executor.setWeapon((Weapon) Weapon.this);
      setCommandLog(executor.getName() + "は" + Weapon.this.getName() + "を装備した！");
      return true;
    }
  }
}
