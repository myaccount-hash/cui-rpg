package com.example.items;

import com.example.commands.ICommand;
import com.example.entities.IEntity;
import java.util.List;

/*
 * アイテムの抽象クラス。装備, 消費等のアクションはCommandとして記述する。
 */
public abstract class Item implements IItem {
  protected String name;
  protected String description;
  protected int price;
  protected IEntity owner;

  public Item(String name, String description, int price, IEntity owner) {
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

  @Override
  public IEntity getOwner() {
    return owner;
  }

  protected abstract List<ICommand> createCommands(IEntity source);

  public final List<ICommand> getCommands(IEntity source) {
    return createCommands(source);
  }
}
