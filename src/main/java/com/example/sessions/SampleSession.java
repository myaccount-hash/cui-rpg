package com.example.sessions;

import com.example.commands.Command;
import com.example.commands.QuitCommand;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * ユーティリティコマンド用のセッション
 */
public class SampleSession extends Session {
    
    public SampleSession(Session parentSession) {
        super("Sample", "サンプルセッション", parentSession);
    }
    
    @Override
    protected void initializeCommands() {
        addCommand(new DateCommand());
        addCommand(new VersionCommand());
        addCommand(new ClearCommand());
        addCommand(new QuitCommand(() -> stop()));
        
        // 初期表示テキストを設定
        setDisplayText("サンプルセッションが開始されました。");
    }
    
    /**
     * 現在の日時を表示するコマンド（内部クラス）
     */
    private class DateCommand extends Command {
        
        public DateCommand() {
            super("date", "現在の日時を表示します", "date");
        }
        
        @Override
        public boolean execute(String[] args) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            setDisplayText("現在の日時: " + now.format(formatter));
            return true;
        }
    }
    
    /**
     * バージョン情報を表示するコマンド（内部クラス）
     */
    private class VersionCommand extends Command {
        
        public VersionCommand() {
            super("version", "プログラムのバージョン情報を表示します", "version");
        }
        
        @Override
        public boolean execute(String[] args) {
            setDisplayText("=== 対話型CUIプログラム ===\n" +
                          "バージョン: 1.0.0\n" +
                          "Java: " + System.getProperty("java.version") + "\n" +
                          "OS: " + System.getProperty("os.name") + " " + System.getProperty("os.version"));
            return true;
        }
    }
    
    /**
     * 画面をクリアするコマンド（内部クラス）
     */
    private class ClearCommand extends Command {
        
        public ClearCommand() {
            super("clear", "画面をクリアします", "clear");
        }
        
        @Override
        public boolean execute(String[] args) {
            // コンソールをクリア（OSに依存）
            try {
                String os = System.getProperty("os.name").toLowerCase();
                if (os.contains("win")) {
                    // Windows
                    new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
                } else {
                    // Unix/Linux/macOS
                    System.out.print("\033[H\033[2J");
                    System.out.flush();
                }
            } catch (Exception e) {
                // クリアに失敗した場合は改行を50回出力
                for (int i = 0; i < 50; i++) {
                    System.out.println();
                }
            }
            return true;
        }
    }
} 