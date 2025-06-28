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

  public Player() {
    super("プレイヤー", 10000, 50, 25, 26, 1, new ArrayList<>());
    this.items = new ArrayList<>(Arrays.asList(new BronzeSword(), new LeatherArmor()));

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

  @Override
  public void setWeapon(Weapon weapon) {
    // 現在の武器がデフォルト武器でない場合はアイテムに戻す
    if (this.weapon != null && !this.weapon.getName().equals("素手")) {
      addItem(this.weapon);
    }
    // 新しい武器がアイテム内にある場合は取り除く
    if (this.items.contains(weapon)) {
      this.items.remove(weapon);
    }
    super.setWeapon(weapon);
  }

  @Override
  public void setArmor(Armor armor) {
    // 現在の防具がデフォルト防具でない場合はアイテムに戻す
    if (this.armor != null && !this.armor.getName().equals("素肌")) {
      addItem(this.armor);
    }
    // 新しい防具がアイテム内にある場合は取り除く
    if (this.items.contains(armor)) {
      this.items.remove(armor);
    }
    super.setArmor(armor);
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