package com.example.entities;

import com.example.commands.Command;
import com.example.utils.TargetUtils;
import com.example.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// モンスターの抽象クラス
public abstract class Monster extends Entity {
  private String icon;
  private int baseDropExp;

  public Monster(
      String name,
      int hp,
      int mp,
      int attack,
      int defence,
      int level,
      int baseDropExp,
      String icon) {
    super(name, hp, mp, attack, defence, level, createDefaultSkills());
    this.baseDropExp = baseDropExp;
    this.icon = icon;
  }

  private static List<Command> createDefaultSkills() {
    List<Command> skills = new ArrayList<>();
    // デフォルトスキルは空のリスト（サブクラスで設定）
    return skills;
  }

  /**
   * 戦闘用のランダム行動を実行する
   *
   * @param enemy 敵エンティティ
   * @return 行動結果のログ
   */
  public String executeRandomAction(Entity enemy) {
    var availableCommands = getAvailableCommands();
    Random random = new Random();

    if (!availableCommands.isEmpty()) {
      Command selectedCommand = availableCommands.get(random.nextInt(availableCommands.size()));

      // TargetUtilsを使用してターゲットを設定
      TargetUtils.setAppropriateTarget(selectedCommand, this, enemy);
      selectedCommand.execute();
      return selectedCommand.getCommandLog();
    }

    // 通常攻撃
    int damage = getAttack();
    enemy.takeDamage(damage);
    return getName() + "の攻撃！ " + damage + "ダメージ！";
  }

  public String getIcon() {
    return icon;
  }

  public int getDropExp() {
    return baseDropExp + (getLevel() - 1) * 20;
  }

  @Override
  public String getInfoText() {
    return String.format(
        "%s: %s\n%s: %d\n%s: %d/%d\n%s: %d/%d\n%s: %d",
        Utils.format("名前", 8),
        getName(),
        Utils.format("レベル", 8),
        getLevel(),
        Utils.format("HP", 8),
        getHp(),
        getMaxHp(),
        Utils.format("MP", 8),
        getMp(),
        getMaxMp(),
        Utils.format("攻撃力", 8),
        getAttack());
  }
}
