package com.example.items;

import java.util.List;

import com.example.commands.Command;
import com.example.entities.Entity;

public class HealPotion extends Item {

  public HealPotion(Entity owner) {
    super("ヒールポーション", "HPを50回復", 10, owner);
  }

  @Override
  protected List<Command> createCommands(Entity source) {
    return List.of(new UseCommand(owner));
  }

  public class UseCommand extends Command {
    public UseCommand(Entity executor) {
      super("使う", "HPを50回復する", executor);
    }

    @Override
    public boolean execute() {
      getExecutor().heal(50);
      setCommandLog(getExecutor().getName() + "はヒールポーションを使い，HPが50回復した！");
      getExecutor().getItems().remove(HealPotion.this);
      return true;
    }

    private Entity getExecutor() {
      return super.executor;
    }
  }
}
