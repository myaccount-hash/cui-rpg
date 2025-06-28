package com.example.items;

import com.example.core.Command;
import com.example.core.Item;
import java.util.List;

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
  protected List<Command> createCommands() {
    return List.of(new EquipCommand());
  }

  public class EquipCommand extends Command {
    public EquipCommand() {
      super("装備", "装備する");
    }

    public String getLabel() {
      return "装備する";
    }

    @Override
    public boolean execute() {
      source.setWeapon((Weapon) Weapon.this);
      setCommandLog(source.getName() + "は" + Weapon.this.getName() + "を装備した！");
      return true;
    }
  }
}
