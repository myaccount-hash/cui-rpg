package com.example.items;

import com.example.commands.Command;
import com.example.commands.ICommand;
import com.example.entities.IEntity;
import java.util.List;

/*
 * 武器の抽象クラス。一部の攻撃を強化する。IEntity.javaのweaponフィールドに脱着可能。
 */
public abstract class Weapon extends Item {
  protected int attack;

  public Weapon(String name, String description, int price, IEntity owner, int attack) {
    super(name, description, price, owner);
    this.attack = attack;
  }

  public int getAttack() {
    return attack;
  }

  @Override
  protected List<ICommand> createCommands(IEntity source) {
    return List.of(new EquipCommand());
  }

  public class EquipCommand extends Command {
    public EquipCommand() {
      super("装備", "装備する", owner);
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
