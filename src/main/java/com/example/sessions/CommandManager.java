package com.example.sessions;

import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import com.example.commands.Command;

/**
 * セッションのコマンドを管理するクラス
 */
public class CommandManager {
    private final Map<String, Command> commands = new HashMap<>();
    public void registerCommand(Command command) {
        commands.put(command.getName().toLowerCase(), command);
    }
    public Command getCommand(String name) {
        return commands.get(name.toLowerCase());
    }
    
    /**
     * すべてのコマンドを取得
     */
    public Collection<Command> getAllCommands() {
        return commands.values();
    }
    
    /**
     * コマンドを実行
     */
    public boolean executeCommand(String name, String[] args) {
        Command command = getCommand(name);
        if (command != null) {
            return command.execute(args);
        }
        System.out.println("不明なコマンド: " + name);
        return false;
    }
}