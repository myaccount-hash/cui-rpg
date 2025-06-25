package com.example.sessions;

import java.util.Scanner;
import com.example.commands.Command;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public abstract class Session {
    private static final int SCREEN_WIDTH = 80;
    private static final String SEPARATOR = "│";
    
    protected String name;
    protected boolean running;
    protected Scanner scanner;
    protected CommandManager commandManager;
    protected List<String> commandOrder;
    protected String displayText;
    protected Queue<String> logQueue; // FIFOログキュー
    protected boolean logDisplaying;
    protected Session parentSession;
    
    // メニュー内容を保持
    protected List<String> menuLines;
    
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
    
    // ゲッターメソッド
    public String getName() { return name; }
    public boolean isRunning() { return running; }
    public boolean isLogDisplaying() { return logDisplaying; }
    public String getDisplayText() { return displayText; }
    
    // 現在のログを文字列として取得
    public String getLogText() {
        if (logQueue.isEmpty()) return "";
        return String.join("\n", logQueue);
    }
    
    public List<String> getMenuLines() { return menuLines; }
    public Session getParentSession() { return parentSession; }
    
    public void addCommand(Command command) {
        commandManager.registerCommand(command);
        commandOrder.add(command.getName().toLowerCase());
        updateMenuLines(); // メニュー内容を更新
    }
    
    public String getPrompt() {
        return logDisplaying ? getLogText() + " (↵で続行) > " : getName() + "> ";
    }
    
    // ディスプレイテキストのみ更新
    public void setDisplayText(String text) {
        this.displayText = text;
        refreshDisplay();
    }
    
    // ログテキストのみ更新（Enter入力まで待機）
    public void setLogText(String text) {
        logQueue.offer(text);
        if (logQueue.size() > 100) logQueue.poll(); // 最大100件まで保持
        this.logDisplaying = true;
        refreshDisplay();
        waitForEnter(); // Enter入力まで処理停止
    }
    
    // ログクリア
    public void clearLog() {
        this.logQueue.clear();
        this.logDisplaying = false;
        refreshDisplay();
    }
    
    // Enter入力まで待機
    private void waitForEnter() {
        scanner.nextLine(); // Enter入力を待つ
        logQueue.poll(); // 表示済みログを削除
        if (logQueue.isEmpty()) {
            this.logDisplaying = false; // キューが空なら表示終了
        }
        refreshDisplay();
    }
    
    // 画面全体更新（状態に応じて適切な内容を表示）
    protected void refreshDisplay() {
        System.out.print("\033[H\033[2J");
        showLayout();
        System.out.print(getPrompt());
    }
    
    private void showLayout() {
        String[] display = getDisplayText().split("\n");
        List<String> menu = getMenuLinesForDisplay();
        int maxLines = Math.max(display.length, menu.size());
        int half = SCREEN_WIDTH / 2;
        
        System.out.printf("%-" + half + "s " + SEPARATOR + " %s%n", "--- Display ---", "--- Menu ---");
        
        for (int i = 0; i < maxLines; i++) {
            String left = i < display.length ? display[i] : "";
            String right = i < menu.size() ? menu.get(i) : "";
            System.out.printf("%-" + half + "s " + SEPARATOR + " %s%n", left, right);
        }
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
    
    // 表示用メニュー生成（ログ表示状態を考慮）
    private List<String> getMenuLinesForDisplay() {
        if (isLogDisplaying()) {
            return List.of("ログ表示中...");
        }
        return getMenuLines();
    }
    
    public void start() {
        running = true;
        initializeCommands();
        refreshDisplay();
        
        while (isRunning()) {
            String input = scanner.nextLine();
            
            // ログ表示中の場合は次のログを表示
            if (isLogDisplaying()) {
                if (!logQueue.isEmpty()) {
                    refreshDisplay();
                } else {
                    this.logDisplaying = false;
                    refreshDisplay();
                }
                continue;
            }
            
            if (!input.trim().isEmpty()) {
                processInput(input.trim());
            }
        }
    }
    
    public void stop() { 
        running = false; 
    }
    
    protected abstract void initializeCommands();
    
    protected void processInput(String input) {
        String commandName = null;
        String[] args = new String[0];
        
        if (input.matches("\\d+")) {
            int idx = Integer.parseInt(input) - 1;
            if (idx >= 0 && idx < commandOrder.size()) {
                commandName = commandOrder.get(idx);
            } else {
                setDisplayText("無効な番号です");
                return;
            }
        } else {
            String[] parts = input.split("\\s+");
            commandName = parts[0].toLowerCase();
            if (parts.length > 1) {
                args = new String[parts.length - 1];
                System.arraycopy(parts, 1, args, 0, parts.length - 1);
            }
        }
        
        if (commandName != null) {
            commandManager.executeCommand(commandName, args);
        }
    }
}