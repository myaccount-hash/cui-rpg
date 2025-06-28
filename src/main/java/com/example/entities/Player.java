package com.example.entities;

import com.example.actions.*;
import com.example.core.*;
import com.example.items.*;
import com.example.utils.Utils;

import java.util.ArrayList;

/*
 * プレイヤーデータを管理するクラス。将来的にはセーブファイルからロードする。
 * MainSessionでインスタンス化し、プログラム終了まで保持される。
 */
public class Player extends Entity {
  public Player() {
    super("プレイヤー", 10000, 50, 25, 26, 1, new ArrayList<>());
    this.itemBox.addItem(new BronzeSword());
    this.itemBox.addItem(new LeatherArmor());
    // プレイヤーのスキルを設定
    addSkill(new HpHeal(this, this));
    addSkill(new FireBall(this, null));
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