package com.example.commands;

import com.example.sessions.Session;

/**
 * コマンドの抽象クラス
 * すべてのコマンドはこのクラスを継承する必要があります
 */
public abstract class Command {
    
    protected String name;
    protected String description;
    protected String usage;
    protected Session session;
    protected String commandLog;
    
    public Command(String name, String description, String usage) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.commandLog = null;
    }
    
    public Command(String name, String description, String usage, Session session) {
        this.name = name;
        this.description = description;
        this.usage = usage;
        this.session = session;
    }
    
    /**
     * コマンド名を取得
     * @return コマンド名
     */
    public String getName() {
        return name;
    }
    
    /**
     * コマンドの説明を取得
     * @return コマンドの説明
     */
    public String getDescription() {
        return description;
    }
    
    /**
     * コマンドの使用方法を取得
     * @return 使用方法の文字列
     */
    public String getUsage() {
        return usage;
    }
    
    /**
     * セッションを取得
     * @return セッション
     */
    public Session getSession() {
        return session;
    }
    
    /**
     * セッションを設定
     * @param session セッション
     */
    public void setSession(Session session) {
        this.session = session;
    }
    
    /**
     * コマンドを実行
     * @param args コマンドの引数
     * @return 実行結果（成功: true, 失敗: false）
     */
    public abstract boolean execute(String[] args);
    
    /**
     * コマンドのヘルプを表示
     */
    public void showHelp() {
        System.out.println("コマンド: " + getName());
        System.out.println("説明: " + getDescription());
        System.out.println("使用方法: " + getUsage());
    }
    
    /**
     * 引数チェックのヘルパーメソッド
     * @param args 引数配列
     * @param expectedCount 期待する引数の数
     * @return 引数が正しい場合はtrue
     */
    protected boolean checkArgs(String[] args, int expectedCount) {
        if (args.length != expectedCount) {
            System.out.println("使用方法: " + getUsage());
            return false;
        }
        return true;
    }
    
    /**
     * 引数チェックのヘルパーメソッド（最小数指定）
     * @param args 引数配列
     * @param minCount 最小引数数
     * @return 引数が正しい場合はtrue
     */
    protected boolean checkMinArgs(String[] args, int minCount) {
        if (args.length < minCount) {
            System.out.println("使用方法: " + getUsage());
            return false;
        }
        return true;
    }
    public void setCommandLog(String commandLog) {
       this.commandLog = commandLog;
    }
    public String getCommandLog() {
        return commandLog;
    }
}