package com.example.entities;

import com.example.actions.FireBall;
import com.example.actions.HpHeal;

/*
 * ドラゴンクラス
 */
public class Dragon extends Monster {

  // ドラゴンの種族値
  private static final int DRAGON_BASE_HP = 1000;
  private static final int DRAGON_BASE_MP = 30000;
  private static final int DRAGON_BASE_ATTACK = 25;
  private static final int DRAGON_BASE_DEFENCE = 10;
  private static final int DRAGON_BASE_DROP_EXP = 50;
  private static final String DRAGON_ICON = """
      .
       .>   )\\;`a__
      (  _ _)/ /-.\" ~~
       `( )_ )/
        <_  <_ """;

  public Dragon(int level) {
    super(
        "Dragon",
        DRAGON_BASE_HP,
        DRAGON_BASE_MP,
        DRAGON_BASE_ATTACK,
        DRAGON_BASE_DEFENCE,
        level,
        DRAGON_BASE_DROP_EXP,
        DRAGON_ICON);

    // ドラゴンのスキルを設定
    addSkill(new FireBall(this));
    addSkill(new HpHeal(this));
  }
}
