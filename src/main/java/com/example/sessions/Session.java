package com.example.sessions;

import java.util.Scanner;
import com.example.commands.Command;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

// TODO: コマンドからログを受け取る機能追加

public abstract class Session {
    private static final int SCREEN_WIDTH = 80;
    private static final String SEPARATOR = "│";
    
    protected String name;
    protected boolean running;
    protected Scanner scanner;
    protected CommandManager commandManager;
    protected List<String> commandOrder;

    protected Queue<String> logQueue; // FIFOログキュー
    protected boolean logDisplaying;

    protected Session parentSession;
    protected List<String> menuLines;
    protected List<String> menuText;
    protected String displayText;

    public Session(String name, String description, Session parentSession) {
        this.name = name;
        this.scanner = new Scanner(System.in);
        this.commandManager = new CommandManager();
        this.commandOrder = new ArrayList<>();
        this.displayText = "";
        this.logQueue = new LinkedList<>();
        this.parentSession = parentSession;
        this.menuLines = new ArrayList<>();
    }


    protected void showLog(){
        if (!logQueue.isEmpty()) {
            refreshDisplay();
        } else {
            this.logDisplaying = false;
            refreshDisplay();
        }
    }

    public void stop() { running = false; }

    // ゲッターメソッド
    public String getName() { return name; }
    public boolean isRunning() { return running; }
    public boolean isLogDisplaying() { return logDisplaying; }
    public String getDisplayText() { return displayText; }
    public List<String> getMenuLines() { return menuLines; }
    public Session getParentSession() { return parentSession; }
    

    protected void setDisplayText(String text) { this.displayText = text; refreshDisplay(); }
    // Session.javaのサブクラスはこのメソッドで任意のコマンドを登録
    protected void addCommand(Command command) {
        commandManager.registerCommand(command);
        commandOrder.add(command.getName().toLowerCase());
        updateMenuLines(); // メニュー内容を更新
    }
    

    // 画面全体更新（状態に応じて適切な内容を表示）
    protected void refreshDisplay() {
        System.out.print("\033[H\033[2J");
        String[] display = getDisplayText().split("\n");
        refreshMenu();
        List<String> menu = menuText;
        int maxLines = Math.max(display.length, menu.size());
        int half = SCREEN_WIDTH / 2;
        
        System.out.printf("%-" + half + "s " + SEPARATOR + " %s%n", "--- Display ---", "--- Menu ---");
        
        for (int i = 0; i < maxLines; i++) {
            String left = i < display.length ? display[i] : "";
            String right = i < menu.size() ? menu.get(i) : "";
            System.out.printf("%-" + half + "s " + SEPARATOR + " %s%n", left, right);
        }
        System.out.print(logDisplaying ? getLogText() + " (↵で続行) > " : getName() + "> ");
    }

    /*
     * ログ系メソッド
     */
    // サブクラス用のログセッター
    public void setLogText(String text) {
        logQueue.offer(text);
        if (logQueue.size() > 100) logQueue.poll(); // 最大100件まで保持
        this.logDisplaying = true;
        refreshDisplay();
        scanner.nextLine(); // Enter入力を待つ
        logQueue.poll(); // 表示済みログを削除
        if (logQueue.isEmpty()) {
            this.logDisplaying = false; // キューが空なら表示終了
        }
        refreshDisplay();
    }
    // ログクリア
    protected void clearLog() {
        this.logQueue.clear();
        this.logDisplaying = false;
        refreshDisplay();
    }

    // 入力を判定し実行
    protected void processInput(String input) {
        String commandName = null;
        String[] args = new String[0];
        
        // 入力を判定
        int idx = Integer.parseInt(input) - 1;
        if (idx >= 0 && idx < commandOrder.size()) {
            commandName = commandOrder.get(idx);
        } else {
            setDisplayText("無効な番号です");
            return;
        }
        // 実行
        if (commandName != null) {
            commandManager.executeCommand(commandName, args);
        }
    }



    /*
     * メニュー系メソッド
     */
    // メニュー生成
    protected void refreshMenu() {
        if (isLogDisplaying()) {
            menuText = List.of("ログ表示中...");
        }
        menuText = getMenuLines();
        return;
    }
    // メニュー内容更新
    private void updateMenuLines() {
        menuLines = new ArrayList<>();
        for (int i = 0; i < commandOrder.size(); i++) {
            Command cmd = commandManager.getCommand(commandOrder.get(i));
            if (cmd != null) {
                menuLines.add((i + 1) + ": " + cmd.getName());
            }
        }
    }

    // 現在のログを文字列として取得
    private String getLogText() {
        if (logQueue.isEmpty()) return "";
        return String.join("\n", logQueue);
    }
    
}

