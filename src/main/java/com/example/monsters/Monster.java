package com.example.monsters;

/**
 * モンスターの抽象クラス
 */
public abstract class Monster {
    
    protected String name;
    protected int hp;
    protected int maxHp;
    protected int attack;
    protected String icon;
    
    public Monster(String name, int hp, int attack, String icon) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;  // 初期HPを最大HPとして設定
        this.attack = attack;
        this.icon = icon;
    }
    
    /**
     * 名前を取得
     * @return モンスターの名前
     */
    public String getName() {
        return name;
    }
    
    /**
     * HPを取得
     * @return モンスターのHP
     */
    public int getHp() {
        return hp;
    }
    
    /**
     * 最大HPを取得
     * @return モンスターの最大HP
     */
    public int getMaxHp() {
        return maxHp;
    }
    
    /**
     * 攻撃力を取得
     * @return モンスターの攻撃力
     */
    public int getAttack() {
        return attack;
    }
    
    /**
     * アイコンを取得
     * @return モンスターのアイコン
     */
    public String getIcon() {
        return icon;
    }
    
    /**
     * 名前を設定
     * @param name モンスターの名前
     */
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * HPを設定
     * @param hp モンスターのHP
     */
    public void setHp(int hp) {
        this.hp = hp;
    }
    
    /**
     * 最大HPを設定
     * @param maxHp モンスターの最大HP
     */
    public void setMaxHp(int maxHp) {
        this.maxHp = maxHp;
    }
    
    /**
     * 攻撃力を設定
     * @param attack モンスターの攻撃力
     */
    public void setAttack(int attack) {
        this.attack = attack;
    }
    
    /**
     * アイコンを設定
     * @param icon モンスターのアイコン
     */
    public void setIcon(String icon) {
        this.icon = icon;
    }
}
