package com.example.core;

/*
   * コマンドの抽象クラス。メニュー項目、プレイヤーの行動、モンスターの行動等は全てCommandとして記述される。
   */
  public abstract class Command {
   protected String name;
   protected String description;
   protected String commandLog;
   protected Entity source;
   protected Entity target;
 
   public Command(String name, String description, Entity source, Entity target) {
    this.name = name;
    this.description = description;
     this.source = source;
     this.target = target;
   }
 
   public Command(String name, String description) {
     this.name = name;
     this.description = description;
   }

   public abstract boolean execute();

   public String getName() { return name; }
   public String getDescription() { return description; }
   public String getCommandLog() { return commandLog; }
   public void setTarget(Entity target) {
      this.target = target;
   }
   public void setSource(Entity source) {
      this.source = source;
   }

   // コマンド内部でログをセットすれば、親セッションでログが表示される
   public void setCommandLog(String commandLog) { this.commandLog = commandLog; }

 }
