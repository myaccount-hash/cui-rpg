package com.example.entities;

import com.example.actions.*;
import java.util.List;
import java.util.ArrayList;

/**
 * ドラゴンクラス
 */
public class Dragon extends Monster {
    
    // ドラゴンの種族値
    private static final int DRAGON_BASE_HP = 1000000;
    private static final int DRAGON_BASE_MP = 30;
    private static final int DRAGON_BASE_ATTACK = 25;
    private static final int DRAGON_BASE_DEFENCE = 10;
    private static final int DRAGON_BASE_DROP_EXP = 50;
    
    public Dragon(int level) {
        super("Dragon", DRAGON_BASE_HP, DRAGON_BASE_MP, DRAGON_BASE_ATTACK, DRAGON_BASE_DEFENCE, level, DRAGON_BASE_DROP_EXP,
            "      . \n" +
            " .>   )\\;`a__\n" +
            "(  _ _)/ /-.\" ~~\n" +
            " `( )_ )/\n" +
            "  <_  <_ ");
        
        // ドラゴンのスキルを設定
        addSkill(new NormalAttack(this, null)); // ターゲットは戦闘時に設定
        addSkill(new FireBall(this, null)); // ターゲットは戦闘時に設定
    }
}
