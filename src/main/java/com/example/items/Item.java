package com.example.items;

import com.example.commands.Command;
import com.example.entities.Entity;
import java.util.List;

/*
 * アイテムの抽象クラス。装備, 消費等のアクションはCommandとして記述する。
 */
public abstract class Item {
  protected String name;
  protected String description;
  protected int price;
  protected Entity owner;

  public Item(String name, String description, int price, Entity owner) {
    this.name = name;
    this.description = description;
    this.price = price;
    this.owner = owner;
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

  public Entity getOwner() {
    return owner;
  }

  protected abstract List<Command> createCommands(Entity source);

  public final List<Command> getCommands(Entity source) {
    return createCommands(source);
  }
}
