package com.example.commands;

  /*
   * コマンドの抽象クラス。メニュー項目、プレイヤーの行動、モンスターの行動等は全てCommandとして記述される。
   */
  public abstract class Command {
   protected String name;
   protected String description;
   protected String commandLog;

   public Command(String name, String description) {
     this.name = name;
     this.description = description;
   }

   public abstract boolean execute(String[] args);

   public String getName() { return name; }
   public String getDescription() { return description; }
   public String getCommandLog() { return commandLog; }

   // コマンド内部でログをセットすれば、親セッションでログが表示される
   public void setCommandLog(String commandLog) { this.commandLog = commandLog; }
 }
