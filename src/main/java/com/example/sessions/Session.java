package com.example.sessions;

import java.util.Scanner;
import com.example.commands.Command;
import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

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
        List<String> menu = logDisplaying ? List.of("ログ表示中...") : getMenuLines();
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
        try {
            int idx = Integer.parseInt(input) - 1;
            if (idx >= 0 && idx < commandOrder.size()) {
                String commandName = commandOrder.get(idx);
                commandManager.executeCommand(commandName, new String[0]);
                // コマンドログがnullでなければログ表示
                Command cmd = commandManager.getCommand(commandName);
                if (cmd != null && cmd.getCommandLog() != null) {
                    setLogText(cmd.getCommandLog());
                }
            } else {
                setDisplayText("無効な番号です");
            }
        } catch (NumberFormatException e) {
            setDisplayText("無効な番号です");
        }
    }

    /*
     * メニュー系メソッド
     */
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

    /**
     * セッションのコマンドを管理する内部クラス
     */
    public class CommandManager {
        private final Map<String, Command> commands = new HashMap<>();
        
        public void registerCommand(Command command) {
            commands.put(command.getName().toLowerCase(), command);
        }
        
        Command getCommand(String name) {
            return commands.get(name.toLowerCase());
        }
        
        Collection<Command> getAllCommands() {
            return commands.values();
        }
        
        boolean executeCommand(String name, String[] args) {
            Command command = getCommand(name);
            if (command != null) {
                return command.execute(args);
            }
            System.out.println("不明なコマンド: " + name);
            return false;
        }
    }
}