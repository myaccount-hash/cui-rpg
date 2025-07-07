package com.example.commands;

import com.example.entities.IEntity;

/*
 * コマンドの抽象クラス。メニュー項目、プレイヤーの行動、モンスターの行動等は全てCommandとして記述される。
 */
public abstract class Command implements ICommand {
  protected String name;
  protected String description;
  protected String commandLog;
  protected IEntity executor;
  private IEntity target;

  public Command(String name, String description, IEntity executor) {
    this.name = name;
    this.description = description;
    this.executor = executor;
  }

  public abstract boolean execute();

  public String getName() {
    return name;
  }

  public IEntity getTarget() {
    if (target == null) {
      throw new IllegalStateException("ターゲットが設定されていません。");
    }
    return target;
  }

  public String getDescription() {
    return description;
  }

  public String getCommandLog() {
    return commandLog;
  }

  public void setTarget(IEntity target) {
    this.target = target;
  }

  // コマンド内部でログをセットすれば、親セッションでログが表示される
  public void setCommandLog(String commandLog) {
    this.commandLog = commandLog;
  }
}
