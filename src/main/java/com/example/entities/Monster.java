package com.example.entities;

public abstract class Monster extends Entity{
    private String icon;
    
    public Monster(String name, int hp, int attack,int defence, String icon) {
        super(name, hp, attack, defence);
        this.icon = icon;
    }
    public String getIcon() {
       return icon;
    }
}