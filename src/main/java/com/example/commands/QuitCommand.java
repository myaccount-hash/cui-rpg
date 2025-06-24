package com.example.commands;

import com.example.utils.Command;

/**
 * プログラムを終了するコマンド
 */
public class QuitCommand extends Command {
    
    private final Runnable onQuit;
    
    public QuitCommand(Runnable onQuit) {
        super("quit", "プログラムを終了します", "quit");
        this.onQuit = onQuit;
    }
    
    @Override
    public boolean execute(String[] args) {
        System.out.println("プログラムを終了します");
        onQuit.run();
        return true;
    }
} 