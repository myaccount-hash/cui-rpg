package com.example.items;

import java.util.List;

import com.example.actions.Action;
import com.example.entities.Entity;

/*
 * アイテムの抽象クラス．装備, 消費等のアクションはActionとして記述する．
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

  protected abstract List<Action> createActions(Entity source);

  public final List<Action> getActions(Entity source) {
    return createActions(source);
  }
}
