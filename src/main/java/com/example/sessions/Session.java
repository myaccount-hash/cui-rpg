package com.example.sessions;

import com.example.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/*
 * 全てのセッションの抽象クラス。メニュー・ディスプレイの表示、入力、コマンドの実行を管理。
 * セッションを開始するにはSession.run()を呼び出す。
 * 左側にディスプレイ、右側にメニューが表示される。メニューには登録されたコマンドが一覧表示される。
 */
public abstract class Session {
  // 定数
  private static final int SCREEN_WIDTH = 80;
  private static final String SEPARATOR = "│";

  // フィールド
  protected String displayText; // ディスプレイに表示するString
  protected boolean showingLog; // セッションに表示するログ
  protected Map<String, Command> commands; // セッションに登録されたコマンド
  protected boolean running; // セッションの停止を制御
  protected String name;
  protected Scanner scanner;
  protected List<String> commandNames;
  protected Session parentSession;
  protected String currentLog;


  // コンストラクタ
  public Session(String name, String description, Session parentSession) {
    this.name = name;
    this.scanner = new Scanner(System.in);
    this.commands = new HashMap<>();
    this.commandNames = new ArrayList<>();
    this.displayText = "";
    this.currentLog = "";
    this.parentSession = parentSession;
  }

  /*
   * public メンバ
   */

  // セッションを開始するメソッド
  public void run() {
    running = true;
    refreshDisplay();
    while (isRunning()) {
      String input = scanner.nextLine();
      if (showingLog) {
        showingLog = false;
        refreshDisplay();
        continue;
      }
      if (!input.trim().isEmpty()) {
        processInput(input.trim());
        afterCommandExecuted();
        refreshDisplay();
      }
    }
    if (parentSession != null) {
      parentSession.refreshDisplay();
    }
  }
  
  // セッションを止めるメソッド
  public void stop() { running = false; }

  // ディスプレイを更新
  public void setDisplayText(String text) {
    this.displayText = text;
    refreshDisplay();
  }

  // ログを設定するメソッド
  public void showMessage(String message) {
    this.currentLog = message;
    this.showingLog = true;
    refreshDisplay();
    scanner.nextLine();
    this.showingLog = false;
    refreshDisplay();
  }
  
  /*
   * コマンドの抽象クラス。メニュー項目、プレイヤーの行動、モンスターの行動等は全てCommandとして記述される。
   */
  public abstract static class Command {
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

  /*
   * セッション終了コマンド
   */
  public class QuitCommand extends Command {
    public QuitCommand() {
      super("quit", "セッションを終了します", "quit");
    }

    @Override
    public boolean execute(String[] args) {
      stop();
      return true;
    }
  }

  public String getName() { return name; }
  public boolean isRunning() { return running; }
  public Session getParentSession() { return parentSession; }
  public String getDisplayText() { return displayText; }

  /*
   * protected メンバ
   */
  
  // セッションにコマンドを追加するメソッド。サブクラスはこれを使いセッションにコマンドを登録
  protected void addCommand(Command command) {
    String key = command.getName().toLowerCase();
    commands.put(key, command);
    commandNames.add(key);
  }

  protected void addCommands(List<? extends Command> commandList) {
    for (Command command : commandList) {
      addCommand(command);
    }
  }

  // コマンド実行後の処理を記述
  protected void afterCommandExecuted() { /*デフォルトは何もしない*/ }

  protected void processInput(String input) {
    try {
      int idx = Integer.parseInt(input) - 1;
      if (idx >= 0 && idx < commandNames.size()) {
        String commandName = commandNames.get(idx);
        Command cmd = commands.get(commandName);
        if (cmd != null) {
          cmd.execute(new String[0]);
          if (cmd.getCommandLog() != null) {
            showMessage(cmd.getCommandLog());
          }
        }
      } else {
        setDisplayText("無効な番号です");
      }
    } catch (NumberFormatException e) {
      setDisplayText("無効な番号です");
    }
  }

  /*
   * private メンバ
   */
  
  private void refreshDisplay() {
    System.out.print("\033[H\033[2J");
    String[] display = displayText.split("\n");
    List<String> menu = showingLog ? List.of("ログ表示中...") : buildMenu();
    int maxLines = Math.max(display.length, menu.size());
    int half = SCREEN_WIDTH / 2;

    // ヘッダー行
    String leftHeader = "--- Display ---";
    String rightHeader = "--- Menu ---";
    System.out.printf("%s " + SEPARATOR + " %s%n", Utils.format(leftHeader, half), rightHeader);

    // コンテンツ行
    for (int i = 0; i < maxLines; i++) {
      String left = i < display.length ? display[i] : "";
      String right = i < menu.size() ? menu.get(i) : "";
      System.out.printf("%s " + SEPARATOR + " %s%n", Utils.format(left, half), right);
    }

    // プロンプト
    String prompt = showingLog ? currentLog + " (↵で続行) > " : name + "> ";
    System.out.print(prompt);
  }

  private List<String> buildMenu() {
    List<String> menu = new ArrayList<>();
    for (int i = 0; i < commandNames.size(); i++) {
      Command cmd = commands.get(commandNames.get(i));
      if (cmd != null) {
        menu.add((i + 1) + ": " + cmd.getName());
      }
    }
    return menu;
  }
}