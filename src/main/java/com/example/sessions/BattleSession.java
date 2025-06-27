package com.example.sessions;

import com.example.entities.Monster;
import com.example.entities.Player;

public class BattleSession extends Session {
    
    private final Monster monster;
    private final Player player;
    private boolean battleEnded = false;
    
    public BattleSession(String name, String description, Monster monster, Player player, Session parentSession) {
        super(name, description, parentSession);
        this.monster = monster;
        this.player = player;
        
        addCommand(new Command("action", "アクションを選択", "action") {
            @Override
            public boolean execute(String[] args) {
                if (battleEnded) {
                    showMessage("戦闘は終了しています。");
                    return true;
                }
                
                new BattleActionSelectionSession(BattleSession.this).run();
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
        addCommand(new Command("status", "ステータスメニュー", "status") {
            @Override
            public boolean execute(String[] args) {
                new PlayerItemListSession(player, BattleSession.this).run();
                return true;
            }
        });
        addCommand(new QuitCommand()); 
        setDisplayText(getBattleInfo());
    }
    
    @Override
    protected void afterCommandExecuted() {
        setDisplayText(getBattleInfo());
    }
    
    public Monster getMonster() { return monster; }
    public Player getPlayer() { return player; }
    
    private String getBattleInfo() {
        return String.format("%s\n%s\n\nHP: %d/%d  MP: %d/%d", 
                           monster.getIcon(), 
                           monster.getInfoText(), 
                           player.getHp(), 
                           player.getMaxHp(),
                           player.getMp(),
                           player.getMaxMp());
    }
    
    private void executeMonsterTurn() {
        if (battleEnded) return;
        
        String actionResult = executeRandomMonsterAction();
        showMessage(actionResult);
    }

    // モンスターのランダム行動実行
    private String executeRandomMonsterAction() {
        var availableActions = monster.getAvailableActions();
        java.util.Random random = new java.util.Random();
        if (!availableActions.isEmpty()) {
            var selectedAction = availableActions.get(random.nextInt(availableActions.size()));
            // ヒール系ならターゲットを自分に
            if (selectedAction instanceof com.example.actions.Heal) {
                if (selectedAction instanceof com.example.actions.Magic) {
                    var magic = (com.example.actions.Magic) selectedAction;
                    magic.setTarget(monster);
                    magic.execute(new String[]{});
                    return magic.getCommandLog();
                }
            } else if (selectedAction instanceof com.example.actions.Magic) {
                var magic = (com.example.actions.Magic) selectedAction;
                magic.setTarget(player);
                magic.execute(new String[]{});
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
            showMessage(String.format("現在のEXP: %d/%d (レベル%d)", player.getExp(), player.getRequiredExpForNextLevel(), player.getLevel()));
            
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