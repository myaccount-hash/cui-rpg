package com.example.items;

import com.example.core.Command;
import com.example.core.Entity;
import java.util.List;

/*
 * アイテムの抽象クラス。装備, 消費等のアクションはCommandとして記述する。
 */
public abstract class Item {
  protected String name;
  protected String description;
  protected int price;

  public Item(String name, String description, int price) {
    this.name = name;
    this.description = description;
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public String getName() {
    return name;
  }

  public int getPrice() {
    return price;
  }

  protected abstract List<Command> createCommands(Entity source);

  public final List<Command> getCommands(Entity source) {
    return createCommands(source);
  }
}
