package com.example.utils;

import com.example.actions.SelfTarget;
import com.example.core.Command;
import com.example.core.Entity;
import java.util.ArrayList;
import java.util.List;

/**
 * ターゲット制御に関するユーティリティクラス
 */
public class TargetUtils {

  /**
   * 戦闘用の使用可能コマンドを取得し、適切なターゲットを設定する
   * @param source 行動主体のエンティティ
   * @param enemy 敵エンティティ
   * @return ターゲットが設定されたコマンドリスト
   */
  public static List<Command> getBattleCommands(Entity source, Entity enemy) {
    List<Command> battleCommands = new ArrayList<>();
    
    for (Command action : source.getAvailableCommands()) {
      // SelfTargetなら自分をターゲットに、それ以外は敵をターゲットに
      if (action instanceof SelfTarget) {
        action.setTarget(source);
      } else {
        action.setTarget(enemy);
      }
      action.setSource(source);
      battleCommands.add(action);
    }
    
    return battleCommands;
  }

  /**
   * アイテムコマンドを取得し、適切なターゲットを設定する
   * @param source 行動主体のエンティティ
   * @param commands アイテムのコマンドリスト
   * @return ターゲットが設定されたコマンドリスト
   */
  public static List<Command> getItemCommands(Entity source, List<Command> commands) {
    List<Command> itemCommands = new ArrayList<>();
    
    for (Command action : commands) {
      // SelfTargetなら自分をターゲットに、それ以外は自分をターゲットに（アイテム使用は基本的に自分が対象）
      if (action instanceof SelfTarget) {
        action.setTarget(source);
      } else {
        action.setTarget(source);
      }
      action.setSource(source);
      itemCommands.add(action);
    }
    
    return itemCommands;
  }

  /**
   * 単一のコマンドに対して適切なターゲットを設定する
   * @param command 設定対象のコマンド
   * @param source 行動主体のエンティティ
   * @param target ターゲットエンティティ（SelfTargetでない場合）
   */
  public static void setAppropriateTarget(Command command, Entity source, Entity target) {
    if (command instanceof SelfTarget) {
      command.setTarget(source);
    } else {
      command.setTarget(target);
    }
    command.setSource(source);
  }
} 