package com.example.sessions;

import java.util.Scanner;

import com.example.utils.Command;

import java.util.List;
import java.util.ArrayList;
import java.nio.charset.Charset;

/**
 * セッションの抽象クラス
 * すべてのセッションはこのクラスを継承する必要があります
 */
public abstract class Session {
    
    // 定数定義
    private static final int SCREEN_WIDTH = 80;
    private static final int DELAY_MS = 500; // 0.5秒
    private static final String CONSOLE_CLEAR = "\033[H\033[2J";
    private static final String SEPARATOR = "│";
    
    protected String name;
    protected boolean running;
    protected Scanner scanner;
    protected CommandManager commandManager;
    protected List<String> commandOrder;
    protected String displayText;
    protected String logText;
    protected boolean logDisplaying; // ログ表示中フラグ
    
    public Session(String name, String description) {
        this.name = name;
        this.running = false;
        this.scanner = new Scanner(System.in);
        this.commandManager = new CommandManager();
        this.commandOrder = new ArrayList<>();
        this.displayText = "";
        this.logText = "";
        this.logDisplaying = false;
    }
    

    /**
     * 0.5秒のラグを実行
     */
    protected void delay() {
        try {
            Thread.sleep(DELAY_MS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    
    /**
     * 文字列を指定された長さにフォーマット（全角文字対応）
     */
    private static String format(String target, int length) {
        int byteDiff = (getByteLength(target, Charset.forName("UTF-8")) - target.length()) / 2;
        return String.format("%-" + (length - byteDiff) + "s", target);
    }
    
    /**
     * 文字列のバイト長を取得
     */
    private static int getByteLength(String string, Charset charset) {
        return string.getBytes(charset).length;
    }
    
    /**
     * セッション名を取得
     * @return セッション名
     */
    public String getName() {
        return name;
    }
    
    /**
     * セッションが実行中かどうかを取得
     * @return 実行中の場合はtrue
     */
    public boolean isRunning() {
        return running;
    }
    
    /**
     * セッションにコマンドを追加
     * @param command 追加するコマンド
     */
    public void addCommand(Command command) {
        commandManager.registerCommand(command);
        commandOrder.add(command.getName().toLowerCase());
    }
    
    /**
     * セッションのプロンプトを取得
     * @return プロンプト文字列
     */
    public String getPrompt() {
        if (logDisplaying) {
            return logText + " (↵で続行) > ";
        }
        return name + "> ";
    }
    
    /**
     * 表示テキストを設定
     * @param text 表示する文字列
     */
    public void setDisplayText(String text) {
        this.displayText = text;
        refreshDisplay();
    }
    
    /**
     * 表示テキストを取得
     * @return 表示テキスト
     */
    public String getDisplayText() {
        return displayText;
    }
    
    /**
     * ログテキストを設定（Enter待ち付き）
     * @param text ログテキスト
     */
    public void setLogText(String text) {
        this.logText = text;
        this.logDisplaying = true;
        refreshDisplay();
    }
    
    /**
     * ログテキストを取得
     * @return ログテキスト
     */
    public String getLogText() {
        return logText;
    }
    
    /**
     * ログ表示を終了
     */
    private void clearLog() {
        this.logText = "";
        this.logDisplaying = false;
        refreshDisplay();
    }
    
    /**
     * ログ表示中かどうかを取得
     * @return ログ表示中の場合はtrue
     */
    public boolean isLogDisplaying() {
        return logDisplaying;
    }
    
    /**
     * 画面をクリア
     */
    private void clearScreen() {
        System.out.print(CONSOLE_CLEAR);
        System.out.flush();
    }
    
    /**
     * 画面を更新（横レイアウト + ログエリア統合）
     */
    protected void refreshDisplay() {
        clearScreen();
        showHorizontalLayout();
        System.out.print(getPrompt());
    }
    
    /**
     * ヘッダーを表示
     */
    private void printHeader(int displayWidth, int menuWidth) {
        System.out.print(format("--- Display ---", displayWidth));
        System.out.print(" " + SEPARATOR + " ");
        System.out.println(format("--- Menu ---", menuWidth));
    }
    
    /**
     * 1行分の内容を表示
     */
    private void printLine(String[] displayLines, List<String> menuLines, 
                          int index, int displayWidth, int menuWidth) {
        String displayLine = getLineContent(displayLines, index, displayWidth);
        String menuLine = getLineContent(menuLines.toArray(new String[0]), index, menuWidth);
        
        System.out.print(format(displayLine, displayWidth));
        System.out.print(" " + SEPARATOR + " ");
        System.out.println(format(menuLine, menuWidth));
    }
    
    /**
     * 行の内容を取得（長すぎる場合は切り詰め）
     */
    private String getLineContent(String[] lines, int index, int maxWidth) {
        String line = (index < lines.length) ? lines[index] : "";
        return line.length() > maxWidth ? line.substring(0, maxWidth - 3) + "..." : line;
    }
    
    /**
     * 横レイアウトで表示
     */
    protected void showHorizontalLayout() {
        String[] displayLines = displayText.split("\n");
        List<String> menuLines = getMenuLines();
        
        int maxLines = Math.max(displayLines.length, menuLines.size());
        int displayWidth = SCREEN_WIDTH / 2;
        int menuWidth = SCREEN_WIDTH - displayWidth - 3; // セパレータ分を考慮
        
        // ヘッダー表示
        printHeader(displayWidth, menuWidth);
        
        // 各行を表示
        for (int i = 0; i < maxLines; i++) {
            printLine(displayLines, menuLines, i, displayWidth, menuWidth);
        }
    }
    
    /**
     * メニュー行を取得
     */
    private List<String> getMenuLines() {
        List<String> lines = new ArrayList<>();
        
        // ログ表示中はメニューを表示しない
        if (logDisplaying) {
            lines.add("ログ表示中...");
            return lines;
        }
        
        for (int i = 0; i < commandOrder.size(); i++) {
            String commandName = commandOrder.get(i);
            Command cmd = commandManager.getCommand(commandName);
            if (cmd != null) {
                lines.add(String.format("%d: %s", i + 1, cmd.getName()));
            }
        }
        
        return lines;
    }
    
    /**
     * セッションを開始
     */
    public void start() {
        running = true;
        initializeCommands();
        
        while (running) {
            String input = scanner.nextLine();
            
            // ログ表示中の場合は入力内容に関係なくクリア
            if (logDisplaying) {
                clearLog();
                continue;
            }
            
            // 空入力の場合は画面更新のみ
            if (input.trim().isEmpty()) {
                refreshDisplay();
                continue;
            }
            
            // 通常の入力処理
            processInput(input.trim());
            refreshDisplay();
        }
    }
    
    /**
     * セッションを停止
     */
    public void stop() {
        running = false;
    }
    
    /**
     * セッション固有のコマンドを初期化
     */
    protected abstract void initializeCommands();
    
    /**
     * ユーザー入力を処理
     */
    protected void processInput(String input) {
        if (input == null || input.isEmpty()) {
            return;
        }
        
        String commandName = null;
        String[] args = new String[0];
        
        // 入力が数字ならコマンド名に変換
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
            if (parts.length > 0) {
                commandName = parts[0].toLowerCase();
                if (parts.length > 1) {
                    args = new String[parts.length - 1];
                    System.arraycopy(parts, 1, args, 0, parts.length - 1);
                }
            }
        }
        
        if (commandName != null) {
            commandManager.executeCommand(commandName, args);
        }
    }
}