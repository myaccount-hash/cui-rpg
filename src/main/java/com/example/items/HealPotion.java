package com.example.items;

import com.example.commands.Command;
import com.example.commands.ICommand;
import com.example.entities.IEntity;
import java.util.List;

public class HealPotion extends Item {
  IEntity owner;

  public HealPotion(IEntity owner) {
    super("ヒールポーション", "HPを50回復", 10, owner);
    this.owner = owner;
  }

  @Override
  protected List<ICommand> createCommands(IEntity source) {
    return List.of(new UseCommand(owner));
  }

  public class UseCommand extends Command implements ICommand {
    public UseCommand(IEntity executor) {
      super("使う", "HPを50回復する", executor);
    }

    @Override
    public boolean execute() {
      getExecutor().heal(50);
      setCommandLog(getExecutor().getName() + "はヒールポーションを使い、HPが50回復した！");
      getExecutor().getItems().remove(HealPotion.this);
      return true;
    }

    private IEntity getExecutor() {
      return super.executor;
    }
  }
}
