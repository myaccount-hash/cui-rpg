package com.example.sessions;

import com.example.entities.Player;
import com.example.items.Item;
import java.util.ArrayList;
import java.util.List;

public class PlayerItemListSession extends Session {
  private final Player player;

  public PlayerItemListSession(Player player, Session parentSession) {
    super("ItemList", "所持アイテム一覧", parentSession);
    this.player = player;

    // アイテムをコマンドとして追加
    List<Command> itemCommands = new ArrayList<>();
    for (Item item : player.getItems()) {
      itemCommands.add(
          new Command(item.getName(), item.getDescription(), item.getName()) {
            @Override
            public boolean execute(String[] args) {
              new ItemActionSession(player, item, PlayerItemListSession.this).run();
              return true;
            }
          });
    }

    // 一括でコマンドを追加
    addCommands(itemCommands);
    addCommand(new QuitCommand());

    setDisplayText(player.getInfoText());
  }

  @Override
  protected void afterCommandExecuted() {
    setDisplayText(player.getInfoText());
  }
}
