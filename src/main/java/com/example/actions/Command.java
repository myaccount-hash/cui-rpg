package com.example.actions;

  /*
   * コマンドの抽象クラス。メニュー項目、プレイヤーの行動、モンスターの行動等は全てCommandとして記述される。
   */
  public abstract class Command {
   protected String name;
   protected String description;
   protected String usage;
   protected String commandLog;

   public Command(String name, String description, String usage) {
     this.name = name;
     this.description = description;
     this.usage = usage;
   }

   public abstract boolean execute(String[] args);

   public String getName() { return name; }
   public String getDescription() { return description; }
   public String getUsage() { return usage; }
   public String getCommandLog() { return commandLog; }

   // コマンド内部でログをセットすれば、親セッションでログが表示される
   public void setCommandLog(String commandLog) { this.commandLog = commandLog; }
 }
