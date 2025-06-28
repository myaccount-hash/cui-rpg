package com.example.sessions;

import com.example.entities.Player;
import com.example.items.Item;

/*
 * 特定のItemに対して行うItemActionを選ぶセッション。
 */
public class ItemActionSession extends Session {
  public ItemActionSession(Player player, Item item, Session parentSession) {
    super("ItemAction", "アイテムアクション", parentSession);
    setDisplayText(buildItemDetail(item));

    // プレイヤーからItemを取得し、ItemActionコマンドの項目リストを生成
    item.getActions().forEach(action -> addCommand(createActionCommand(action, player, item)));
    addCommand(new QuitCommand());
  }

  private String buildItemDetail(Item item) {
    return String.format("【アイテム詳細】\n名前: %s\n説明: %s", item.getName(), item.getDescription());
  }

  private Command createActionCommand(Item.ItemAction action, Player player, Item item) {
    return new Command(action.getName(), action.getLabel(), action.getName()) {
      @Override
      public boolean execute(String[] args) {
        action.execute(player, item);
        return true;
      }
    };
  }

  @Override
  protected void afterCommandExecuted() {
    stop();
  }
}
