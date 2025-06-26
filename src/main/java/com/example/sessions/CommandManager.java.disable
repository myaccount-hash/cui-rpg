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