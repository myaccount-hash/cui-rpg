package com.example.items;

import com.example.actions.SelfTarget;
import com.example.commands.Command;
import com.example.entities.Entity;
import java.util.List;

public class HealPotion extends Item {
  Entity owner;
  public HealPotion(Entity owner) {
    super("ヒールポーション", "HPを50回復", 10, owner);
    this.owner = owner;
  }

  @Override
  protected List<Command> createCommands(Entity source) {
    return List.of(new UseCommand(owner));
  }

  public class UseCommand extends Command implements SelfTarget {
    public UseCommand(Entity executor) {
      super("使う", "HPを50回復する", executor);
    }

    @Override
    public boolean execute() {
      source.heal(50);
      setCommandLog(source.getName() + "はヒールポーションを使い、HPが50回復した！");
      source.getItems().remove(HealPotion.this);
      return true;
    }
  }
}
