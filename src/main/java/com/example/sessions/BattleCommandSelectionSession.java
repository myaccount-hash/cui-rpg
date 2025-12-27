package com.example.sessions;

import com.example.actions.Action;
import com.example.commands.ActionCommandAdapter;
import com.example.commands.QuitCommand;
import com.example.entities.Entity;

/*
 * Playerの持つスキル等から，攻撃や魔法等のアクションを選択するセッション．
 * BattleSessionから推移する．
 */
public class BattleCommandSelectionSession extends Session {

  public BattleCommandSelectionSession(Session parentSession, Entity sessionOwner) {
    super("アクション選択", "アクション選択", parentSession, sessionOwner);
    BattleSession battleSession = (BattleSession) parentSession;
    this.displayText = parentSession.getDisplayText();

    // プレイヤーの戦闘コマンドを取得（ターゲット設定済み）
    sessionOwner.setBattleTarget(battleSession.getMonster());
    for (Action action : sessionOwner.getSkills()) {
      addCommand(new ActionCommandAdapter(action));
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
