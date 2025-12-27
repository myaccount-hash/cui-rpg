package com.example.sessions;

import com.example.commands.Command;
import com.example.commands.QuitCommand;
import com.example.entities.Entity;
import com.example.entities.Monster;

/*
プレイヤーの行動，モンスターの行動，ログ，勝敗判定等を制御するセッション．
*/
public class BattleSession extends Session {

  private final Monster monster;
  private boolean battleEnded = false;

  public BattleSession(
      String name,
      String description,
      Monster monster,
      Session parentSession,
      Entity sessionOwner) {
    super(name, description, parentSession, sessionOwner);
    this.monster = monster;

    addCommand(
        new Command("action", "アクションを選択") {
          @Override
          public boolean execute() {
            if (battleEnded) {
              showMessage("戦闘は終了しています．");
              return true;
            }

            new BattleCommandSelectionSession(BattleSession.this, sessionOwner).run();
            setDisplayText(getBattleInfo());

            // 勝負判定
            if (checkBattleResult()) {
              return true;
            }

            executeMonsterTurn();
            setDisplayText(getBattleInfo());
            checkBattleResult();
            return true;
          }
        });
    addCommand(
        new Command("status", "ステータスメニュー") {
          @Override
          public boolean execute() {
            new PlayerItemListSession(BattleSession.this, sessionOwner).run();
            return true;
          }
        });
    addCommand(new QuitCommand(this));
    setDisplayText(getBattleInfo());
  }

  @Override
  protected void afterCommandExecuted() {
    setDisplayText(getBattleInfo());
  }

  public Monster getMonster() {
    return monster;
  }

  public Entity getPlayer() {
    return sessionOwner;
  }

  private String getBattleInfo() {
    return String.format(
        "%s\n%s\n\nHP: %d/%d  MP: %d/%d",
        monster.getIcon(),
        monster.getInfoText(),
        sessionOwner.getHp(),
        sessionOwner.getMaxHp(),
        sessionOwner.getMp(),
        sessionOwner.getMaxMp());
  }

  private static String executeRandomAction(Monster monster, Entity sessionOwner) {
    var availableActions = monster.getAvailableActions();
    java.util.Random random = new java.util.Random();

    if (!availableActions.isEmpty()) {
      var selectedAction = availableActions.get(random.nextInt(availableActions.size()));
      selectedAction.setTarget(sessionOwner);
      selectedAction.execute();
      return selectedAction.getCommandLog();
    }
    // 通常攻撃
    var normalAttack = new com.example.actions.NormalAttack(monster, sessionOwner);
    normalAttack.execute();
    return normalAttack.getCommandLog();
  }

  private void executeMonsterTurn() {
    if (battleEnded) return;
    String actionResult = executeRandomAction(monster, sessionOwner);
    showMessage(actionResult);
  }

  // 勝負判定
  private boolean checkBattleResult() {
    if (monster.getHp() <= 0) {
      battleEnded = true;
      int gainedExp = monster.getDropExp();
      int oldLevel = sessionOwner.getLevel();
      sessionOwner.gainExp(gainedExp);
      int newLevel = sessionOwner.getLevel();

      showMessage(String.format("勝利！ %sを倒しました！", monster.getName()));
      showMessage(String.format("%d EXPを獲得しました！", gainedExp));
      if (newLevel > oldLevel) showMessage(String.format("レベルアップ！ レベル%dになりました！", newLevel));
      showMessage(
          String.format(
              "現在のEXP: %d/%d (レベル%d)",
              sessionOwner.getExp(),
              sessionOwner.getRequiredExpForNextLevel(),
              sessionOwner.getLevel()));

      stop();
      return true;
    }

    if (sessionOwner.getHp() <= 0) {
      battleEnded = true;
      showMessage("敗北... " + monster.getName() + "に倒されました...");
      stop();
      return true;
    }
    return false;
  }
}
