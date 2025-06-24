package com.example.sessions;


import com.example.commands.HelpCommand;
import com.example.commands.QuitCommand;

/**
 * バトルアクション選択セッション
 * 攻撃方法を選択するためのセッション
 */
public class BattleActionSelectionSession extends Session {
    
    private BattleSession parentSession; // 親セッション参照
    
    public BattleActionSelectionSession(Session parentSession) {
        super("アクション選択", "攻撃方法を選択してください", parentSession);
        this.parentSession = (BattleSession) parentSession; // 親セッションを保存
    }
    
    /**
     * 親セッションを取得
     * @return 親セッション
     */
    public BattleSession getParentSession() {
        return parentSession;
    }
    
    /**
     * 親セッションにログを設定
     * @param text ログメッセージ
     */
    public void setParentLog(String text) {
        parentSession.setLogText(text);
    }

    
    /**
     * コマンド実行後にセッションを停止するprocessInputメソッドをオーバーライド
     * @param input ユーザー入力
     */
    @Override
    protected void processInput(String input) {
        String commandName = null;
        String[] args = new String[0];
        
        // 入力が数字ならコマンド名に変換
        if (input.matches("\\d+")) {
            int idx = Integer.parseInt(input) - 1;
            if (idx >= 0 && idx < commandOrder.size()) {
                commandName = commandOrder.get(idx);
            } else {
                setDisplayText("無効な番号です");
                return;
            }
        } else {
            String[] parts = input.split("\\s+");
            commandName = parts[0].toLowerCase();
            if (parts.length > 1) {
                args = new String[parts.length - 1];
                System.arraycopy(parts, 1, args, 0, parts.length - 1);
            }
        }
        
        // コマンドを実行
        boolean executed = commandManager.executeCommand(commandName, args);
        
        // コマンドが実行された場合はセッションを停止
        if (executed) {
            stop();
        }
    }
    
    @Override
    protected void initializeCommands() {
        // 剣攻撃コマンド（ラムダ式で実装）
        addCommand(new com.example.utils.Command("sword", "剣で攻撃する", "sword") {
            @Override
            public boolean execute(String[] args) {
                // バトル状態チェック
                if (!parentSession.checkBattleState()) {
                    setParentLog("バトルが終了しています。");
                    return false;
                }
                int damage = parentSession.getPlayer().getAttack();
                String actionMessage = "剣で攻撃！ ";
                boolean result = parentSession.executeBattleAction(damage, actionMessage);
                if (result) {
                    setParentLog("剣で攻撃した！");
                }
                return result;
            }
        });
        
        addCommand(new HelpCommand(commandManager));
        addCommand(new QuitCommand(this::stop));
        
        // 初期表示テキストを設定
        setDisplayText("攻撃方法を選択してください");
    }
}