package com.example.sessions;

import com.example.actions.*;
import com.example.commands.QuitCommand;
import com.example.core.Command;
import com.example.core.Session;
import com.example.entities.Monster;
import com.example.entities.Player;

/*
プレイヤーの行動、モンスターの行動、ログ、勝敗判定等を制御するセッション。
*/
public class BattleSession extends Session {

  private final Monster monster;
  private final Player player;
  private boolean battleEnded = false;

  public BattleSession(
      String name, String description, Monster monster, Player player, Session parentSession) {
    super(name, description, parentSession);
    this.monster = monster;
    this.player = player;

    addCommand(
        new Command("action", "アクションを選択") {
          @Override
          public boolean execute() {
            if (battleEnded) {
              showMessage("戦闘は終了しています。");
              return true;
            }

            new BattleCommandSelectionSession(BattleSession.this).run();
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
            new PlayerItemListSession(player, BattleSession.this).run();
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

  public Player getPlayer() {
    return player;
  }

  private String getBattleInfo() {
    return String.format(
        "%s\n%s\n\nHP: %d/%d  MP: %d/%d",
        monster.getIcon(),
        monster.getInfoText(),
        player.getHp(),
        player.getMaxHp(),
        player.getMp(),
        player.getMaxMp());
  }

  private void executeMonsterTurn() {
    if (battleEnded) return;

    String actionResult = executeRandomMonsterCommand();
    showMessage(actionResult);
  }

  // モンスターのランダム行動実行
  private String executeRandomMonsterCommand() {
    var availableCommands = monster.getAvailableCommands();
    java.util.Random random = new java.util.Random();
    if (!availableCommands.isEmpty()) {
      Command selectedCommand = availableCommands.get(random.nextInt(availableCommands.size()));
      // ヒール系ならターゲットを自分に
      if (selectedCommand instanceof Heal) {
        Command heal = (Command) selectedCommand;
        heal.setTarget(monster);
        heal.execute();
        return heal.getCommandLog();
      } else if (selectedCommand instanceof com.example.actions.Magic) {
        Magic magic = (Magic) selectedCommand;
        magic.setTarget(player);
        magic.execute();
        return magic.getCommandLog();
      }
    }
    // 通常攻撃
    int damage = monster.getAttack();
    player.takeDamage(damage);
    return monster.getName() + "の攻撃！ " + damage + "ダメージ！";
  }

  // 勝負判定
  private boolean checkBattleResult() {
    if (monster.getHp() <= 0) {
      battleEnded = true;
      int gainedExp = monster.getDropExp();
      int oldLevel = player.getLevel();
      player.gainExp(gainedExp);
      int newLevel = player.getLevel();

      showMessage(String.format("勝利！ %sを倒しました！", monster.getName()));
      showMessage(String.format("%d EXPを獲得しました！", gainedExp));
      if (newLevel > oldLevel) showMessage(String.format("レベルアップ！ レベル%dになりました！", newLevel));
      showMessage(
          String.format(
              "現在のEXP: %d/%d (レベル%d)",
              player.getExp(), player.getRequiredExpForNextLevel(), player.getLevel()));

      stop();
      return true;
    }

    if (player.getHp() <= 0) {
      battleEnded = true;
      showMessage("敗北... " + monster.getName() + "に倒されました...");
      stop();
      return true;
    }
    return false;
  }
}
