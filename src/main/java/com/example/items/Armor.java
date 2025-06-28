package com.example.items;

import com.example.core.Command;
import com.example.core.Item;
import java.util.List;
import com.example.actions.SelfTarget;
import com.example.core.Entity;

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
  protected List<Command> createCommands(Entity source) {
    return List.of(new EquipCommand(source));
  }

  public class EquipCommand extends Command implements SelfTarget {
    public EquipCommand(Entity source) {
      super("装備", "装備する", source);
    }

    public String getLabel() {
      return "装備する";
    }

    public boolean execute() {
      target.setArmor((Armor) Armor.this);
      setCommandLog(target.getName() + "は" + Armor.this.getName() + "を装備した！");
      return true;
    }
  }
}
