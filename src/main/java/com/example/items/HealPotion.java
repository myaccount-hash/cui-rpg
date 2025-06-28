package com.example.items;

import com.example.core.Command;
import com.example.core.Item;
import java.util.List;

public class HealPotion extends Item {
  public HealPotion() {
    super("ヒールポーション", "HPを50回復", 10);
  }

  @Override
  protected List<Command> createCommands() {
    return List.of(new UseCommand());
  }

  public class UseCommand extends Command {
    public UseCommand() {
      super("使う", "HPを50回復する", null, null);
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
