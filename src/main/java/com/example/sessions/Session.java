package com.example.sessions;

import com.example.Utils;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public abstract class Session {
  private static final int SCREEN_WIDTH = 80;
  private static final String SEPARATOR = "│";

  protected String name;
  protected boolean running;
  protected Scanner scanner;
  protected Map<String, Command> commands;
  protected List<String> commandNames;
  protected Session parentSession;
  protected String displayText;
  protected String currentLog;
  protected boolean showingLog;

  public Session(String name, String description, Session parentSession) {
    this.name = name;
    this.scanner = new Scanner(System.in);
    this.commands = new HashMap<>();
    this.commandNames = new ArrayList<>();
    this.displayText = "";
    this.currentLog = "";
    this.parentSession = parentSession;
  }

  public void stop() {
    running = false;
  }

  // ゲッター
  public String getName() {
    return name;
  }

  public boolean isRunning() {
    return running;
  }

  public Session getParentSession() {
    return parentSession;
  }

  public void setDisplayText(String text) {
    this.displayText = text;
    refreshDisplay();
  }

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

  public void refreshDisplay() {
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

  public void showMessage(String message) {
    this.currentLog = message;
    this.showingLog = true;
    refreshDisplay();
    scanner.nextLine();
    this.showingLog = false;
    refreshDisplay();
  }

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

  // --- 内部クラス: セッション終了コマンド ---
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

  /** セッションの共通ループ処理 */
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

  public String getDisplayText() {
    return displayText;
  }

  /** コマンド実行後のフック（必要に応じてサブクラスでオーバーライド） */
  protected void afterCommandExecuted() {
    // デフォルトは何もしない
  }

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

    public String getName() {
      return name;
    }

    public String getDescription() {
      return description;
    }

    public String getUsage() {
      return usage;
    }

    public void setCommandLog(String commandLog) {
      this.commandLog = commandLog;
    }

    public String getCommandLog() {
      return commandLog;
    }
  }
}
