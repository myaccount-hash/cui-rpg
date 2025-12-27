package com.example.entities;

import java.util.ArrayList;

import com.example.actions.FireBall;
import com.example.actions.HpHeal;
import com.example.items.BronzeSword;
import com.example.items.LeatherArmor;
import com.example.utils.Utils;

/*
 * プレイヤーデータを管理するクラス．将来的にはセーブファイルからロードする．
 * MainSessionでインスタンス化し，プログラム終了まで保持される．
 */
public class Player extends Entity {
  public Player() {
    super("プレイヤー", 1000, 50, 50, 5, 1, new ArrayList<>());
    this.ItemBox.addItem(new BronzeSword(this));
    this.ItemBox.addItem(new LeatherArmor(this));
    this.ItemBox.addGold(100000000);
    // プレイヤーのスキルを設定
    addSkill(new HpHeal(this));
    addSkill(new FireBall(this));
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
