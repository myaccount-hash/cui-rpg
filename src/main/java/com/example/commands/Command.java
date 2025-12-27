package com.example.commands;

/*
 * コマンドの抽象クラス．メニュー項目の操作を表す．
 */
public abstract class Command {
  protected String name;
  protected String description;
  protected String commandLog;

  public Command(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public abstract boolean execute();

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public String getCommandLog() {
    return commandLog;
  }

  // コマンド内部でログをセットすれば，親セッションでログが表示される
  public void setCommandLog(String commandLog) {
    this.commandLog = commandLog;
  }
}
