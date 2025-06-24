package com.example.sessions;

import java.util.HashMap;
import java.util.Map;

import com.example.commands.Command;

import java.util.Collection;

/**
 * コマンドを管理するクラス
 */
public class CommandManager {
    
    private final Map<String, Command> commands;
    
    public CommandManager() {
        this.commands = new HashMap<>();
    }
    
    /**
     * コマンドを登録
     * @param command 登録するコマンド
     */
    public void registerCommand(Command command) {
        commands.put(command.getName().toLowerCase(), command);
    }
    
    /**
     * コマンドを取得
     * @param name コマンド名
     * @return コマンド、存在しない場合はnull
     */
    public Command getCommand(String name) {
        return commands.get(name.toLowerCase());
    }
    
    /**
     * すべてのコマンドを取得
     * @return コマンドのコレクション
     */
    public Collection<Command> getAllCommands() {
        return commands.values();
    }
    
    /**
     * コマンドが存在するかチェック
     * @param name コマンド名
     * @return 存在する場合はtrue
     */
    public boolean hasCommand(String name) {
        return commands.containsKey(name.toLowerCase());
    }
    
    /**
     * コマンドを実行
     * @param name コマンド名
     * @param args 引数
     * @return 実行結果（成功: true, 失敗: false）
     */
    public boolean executeCommand(String name, String[] args) {
        Command command = getCommand(name);
        if (command != null) {
            return command.execute(args);
        } else {
            System.out.println("不明なコマンド: " + name);
            return false;
        }
    }
}