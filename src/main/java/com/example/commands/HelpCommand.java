package com.example.commands;

import com.example.sessions.CommandManager;
import com.example.utils.Command;

/**
 * ヘルプを表示するコマンド
 */
public class HelpCommand extends Command {
    
    private final CommandManager commandManager;
    
    public HelpCommand(CommandManager commandManager) {
        super("help", "利用可能なコマンドの一覧を表示します", "help [command]");
        this.commandManager = commandManager;
    }
    
    @Override
    public boolean execute(String[] args) {
        if (args.length == 0) {
            // 全コマンドの一覧を表示
            System.out.println("利用可能なコマンド:");
            commandManager.getAllCommands().forEach(this::printCommandInfo);
            System.out.println("\n詳細なヘルプを表示するには 'help <command>' を使用してください");
        } else {
            // 特定のコマンドのヘルプを表示
            String commandName = args[0].toLowerCase();
            Command command = commandManager.getCommand(commandName);
            
            if (command != null) {
                command.showHelp();
            } else {
                System.out.println("不明なコマンド: " + commandName);
                System.out.println("'help' と入力して利用可能なコマンドを確認してください");
                return false;
            }
        }
        return true;
    }
    
    private void printCommandInfo(Command command) {
        System.out.printf("  %-10s - %s%n", command.getName(), command.getDescription());
    }
} 