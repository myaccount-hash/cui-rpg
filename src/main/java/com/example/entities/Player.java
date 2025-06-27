package com.example.entities;

import com.example.Utils;
import com.example.actions.*;
import com.example.items.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
 * プレイヤーデータを管理するクラス。将来的にはセーブファイルからロードする。
 * MainSessionでインスタンス化し、プログラム終了まで保持される。
 */
public class Player extends Entity {
  private List<Item> items = new ArrayList<>();
  private int gold = 1000; // 初期所持金
  protected Weapon weapon;
  protected Armor armor;

  public Player() {
    super("プレイヤー", 10000, 50, 25, 26, 1, new ArrayList<>());
    this.items = new ArrayList<>(Arrays.asList(new IronSword(), new IronArmor()));
    // 初期装備を設定
    setWeapon(new IronSword());
    setArmor(new IronArmor());

    // プレイヤーのスキルを設定
    addSkill(new HpHeal(this, this));
    addSkill(new FireBall(this, null));
  }

  public List<Item> getItems() {
    return items;
  }

  public int getGold() {
    return gold;
  }

  public void setGold(int gold) {
    this.gold = gold;
  }

  public void addGold(int amount) {
    this.gold += amount;
  }

  public boolean subtractGold(int amount) {
    if (this.gold >= amount) {
      this.gold -= amount;
      return true;
    }
    return false;
  }

  public void addItem(Item item) {
    this.items.add(item);
  }

  public void setWeapon(Weapon weapon) {
    // 現在の武器をアイテムリストに戻す（NoWeapon以外）
    if (this.weapon != null && !(this.weapon instanceof Weapon.NoWeapon)) {
      addItem(this.weapon);
    }
    // 新しい武器を装備し、アイテムリストから削除（NoWeapon以外）
    if (weapon != null && !(weapon instanceof Weapon.NoWeapon)) {
      this.items.remove(weapon);
    }
    super.setWeapon(weapon);
  }

  public void setArmor(Armor armor) {
    if (this.armor != null && !(this.armor instanceof Armor.NoArmor)) {
      addItem(this.armor);
    }
    if (armor != null && !(armor instanceof Armor.NoArmor)) {
      this.items.remove(armor);
    }
    super.setArmor(armor);
  }

  @Override
  public void unequipWeapon() {
    setWeapon(null);
  }

  @Override
  public void unequipArmor() {
    setArmor(null);
  }

  @Override
  public String getInfoText() {
    String[] lines = {
      Utils.format("名前", 8) + ": " + getName(),
      Utils.format("レベル", 8) + ": " + getLevel(),
      Utils.format("HP", 8) + ": " + getHp() + "/" + getMaxHp(),
      Utils.format("MP", 8) + ": " + getMp() + "/" + getMaxMp(),
      Utils.format("EXP", 8) + ": " + getExp() + "/" + getRequiredExpForNextLevel(),
      Utils.format("攻撃力", 8) + ": " + getAttack(),
      Utils.format("防御力", 8) + ": " + getDefence(),
      Utils.format("武器", 8)
          + ": "
          + getWeapon().getName()
          + "（攻撃力: "
          + getWeapon().getAttack()
          + "）",
      Utils.format("防具", 8) + ": " + getArmor().getName() + "（防御力: " + getArmor().getDefense() + "）"
    };

    return String.join("\n", lines);
  }
}
