package com.example.commands;

import com.example.utils.Command;
import com.example.utils.Player;
import com.example.utils.SaveDataManager;

/**
 * プレイヤーデータを保存するコマンド
 */
public class SaveCommand extends Command {
    
    private final Player player;
    
    public SaveCommand(Player player) {
        super("save", "プレイヤーデータを保存する", "save");
        this.player = player;
    }
    
    @Override
    public boolean execute(String[] args) {
        SaveDataManager.savePlayer(player);
        System.out.println("プレイヤーデータを保存しました。");
        return true;
    }
} 