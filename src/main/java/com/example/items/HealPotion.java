package com.example.items;

import java.util.List;

import com.example.actions.Action;
import com.example.entities.Entity;

public class HealPotion extends Item {

  public HealPotion(Entity owner) {
    super("ヒールポーション", "HPを50回復", 10, owner);
  }

  @Override
  protected List<Action> createActions(Entity source) {
    return List.of(new UseAction(source));
  }

  public class UseAction extends Action {
    public UseAction(Entity executor) {
      super("使う", "HPを50回復する", executor);
    }

    @Override
    public boolean execute() {
      executor.heal(50);
      setCommandLog(executor.getName() + "はヒールポーションを使い，HPが50回復した！");
      executor.getItems().remove(HealPotion.this);
      return true;
    }
  }
}
