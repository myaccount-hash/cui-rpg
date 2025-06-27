package com.example.actions;

import com.example.entities.Entity;

public abstract class Magic extends Action {
    protected int mpCost;
    
    public Magic(String name, String description, String commandName, Entity source, Entity target, int mpCost) {
        super(name, description, commandName, source, target);
        this.mpCost = mpCost;
    }
    
    @Override
    public boolean execute(String[] args) {
        // MPが不足している場合は実行できない
        if (source.getMp() < mpCost) {
            setCommandLog(source.getName() + "のMPが足りない！");
            return false;
        }
        
        // MPを消費
        source.useMp(mpCost);
        
        // 魔法を実行
        return executeMagic(args);
    }
    
    // 各魔法クラスで実装する抽象メソッド
    protected abstract boolean executeMagic(String[] args);
    public int getMpCost() {
       return mpCost;
    }
}
