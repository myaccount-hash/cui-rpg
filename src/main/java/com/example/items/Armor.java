package com.example.items;

import com.example.commands.Command;
import com.example.entities.Entity;
import java.util.List;

// 防具の抽象クラス。一部の攻撃を強化する。Entity.javaのarmorフィールドに脱着可能。
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
  protected List<Command> createCommands(Entity source) {
    return List.of(new EquipCommand(source));
  }

  public class EquipCommand extends Command {
    public EquipCommand(Entity executor) {
      super("装備", "装備する", executor);
    }

    public String getLabel() {
      return "装備する";
    }
    @Override
    public boolean execute() {
      executor.setArmor((Armor) Armor.this);
      setCommandLog(getTarget().getName() + "は" + Armor.this.getName() + "を装備した！");
      return true;
    }
  }
}
