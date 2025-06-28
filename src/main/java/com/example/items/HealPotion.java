package com.example.items;

import com.example.core.Command;
import com.example.core.*;
import com.example.actions.SelfTarget;
import java.util.List;

public class HealPotion extends Item {
  public HealPotion() {
    super("ヒールポーション", "HPを50回復", 10);
  }

  @Override
  protected List<Command> createCommands() {
    return List.of(new UseCommand());
  }

  public class UseCommand extends Command implements SelfTarget {
    public UseCommand() {
      super("使う", "HPを50回復する");
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
