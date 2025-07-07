package com.example.items;

import com.example.commands.Command;
import com.example.commands.ICommand;
import com.example.entities.IEntity;
import java.util.List;

// 防具の抽象クラス。一部の攻撃を強化する。IEntity.javaのarmorフィールドに脱着可能。
public abstract class Armor extends Item {
  protected int defense;

  public Armor(String name, String description, int price, IEntity owner, int defense) {
    super(name, description, price, owner);
    this.defense = defense;
  }

  public int getDefense() {
    return defense;
  }

  @Override
  protected List<ICommand> createCommands(IEntity source) {
    return List.of(new EquipCommand(source));
  }

  public class EquipCommand extends Command implements ICommand {
    public EquipCommand(IEntity executor) {
      super("装備", "装備する", executor);
    }

    public String getLabel() {
      return "装備する";
    }

    public boolean execute() {
      executor.setArmor((Armor) Armor.this);
      setCommandLog(getTarget().getName() + "は" + Armor.this.getName() + "を装備した！");
      return true;
    }
  }
}
