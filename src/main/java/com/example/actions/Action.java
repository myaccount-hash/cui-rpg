package com.example.actions;

import com.example.entities.Entity;

/*
 * 戦闘やアイテム使用などの実行アクションを表す．
 */
public abstract class Action {
  protected String name;
  protected String description;
  protected String commandLog;
  protected Entity executor;
  private Entity target;

  public Action(String name, String description, Entity executor) {
    this.name = name;
    this.description = description;
    this.executor = executor;
  }

  public abstract boolean execute();

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Entity getExecutor() {
    return executor;
  }

  public Entity getTarget() {
    if (target == null) {
      throw new IllegalStateException("ターゲットが設定されていません．");
    }
    return target;
  }

  public void setTarget(Entity target) {
    this.target = target;
  }

  public String getCommandLog() {
    return commandLog;
  }

  public void setCommandLog(String commandLog) {
    this.commandLog = commandLog;
  }
}
