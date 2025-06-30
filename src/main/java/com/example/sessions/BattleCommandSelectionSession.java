package com.example.sessions;

import com.example.actions.*;
import com.example.commands.QuitCommand;
import com.example.core.*;
import com.example.utils.TargetUtils;

/*
 * Playerの持つスキル等から、攻撃や魔法等のアクションを選択するセッション。
 * BattleSessionから推移する。
 */
public class BattleCommandSelectionSession extends Session {

  public BattleCommandSelectionSession(Session parentSession) {
    super("アクション選択", "アクション選択", parentSession);
    BattleSession battleSession = (BattleSession) parentSession;
    this.displayText = parentSession.getDisplayText();

    // プレイヤーの戦闘コマンドを取得（ターゲット設定済み）
    var battleCommands =
        TargetUtils.getBattleCommands(battleSession.getPlayer(), battleSession.getMonster());
    for (Command action : battleCommands) {
      addCommand(action);
    }

    addCommand(new QuitCommand(this));
  }

  @Override
  public void setDisplayText(String text) {
    parentSession.setDisplayText(text);
  }

  @Override
  public void showMessage(String text) {
    parentSession.showMessage(text);
  }

  @Override
  protected void afterCommandExecuted() {
    stop();
  }
}
