package com.example.actions;

import com.example.entities.Entity;

/*
 * 魔法の抽象クラス。mpを消費するAction。
 */
public abstract class Magic extends Action {
    protected int mpCost;
    
    public Magic(String name, String description, String commandName, Entity source, Entity target, int mpCost) {
        super(name, description, commandName, source, target);
        this.mpCost = mpCost;
    }
    

    // 各魔法クラスで実装する抽象メソッド
    public int getMpCost() {
       return mpCost;
    }
}
