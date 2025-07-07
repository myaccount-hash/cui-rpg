package com.example.entities;

import com.example.commands.ICommand;
import com.example.items.Armor;
import com.example.items.IItem;
import com.example.items.Weapon;
import java.util.List;

public interface IEntity {
  String getName();

  int getHp();

  int getMp();

  int getExp();

  int getLevel();

  int getBaseHp();

  int getBaseMp();

  int getBaseAttack();

  int getBaseDefence();

  int getMaxHp();

  int getMaxMp();

  int getAttack();

  int getDefence();

  Weapon getWeapon();

  Armor getArmor();

  List<ICommand> getSkills();

  void addSkill(ICommand skill);

  void removeSkill(ICommand skill);

  void clearSkills();

  List<ICommand> getAvailableCommands();

  void setWeapon(Weapon weapon);

  void setArmor(Armor armor);

  void setBattleTarget(IEntity battleTarget);

  IEntity getBattleTarget();

  void takeDamage(int damage);

  void heal(int amount);

  void useMp(int amount);

  void restoreMp(int amount);

  void gainExp(int amount);

  String getInfoText();

  List<IItem> getItems();

  void addItem(IItem item);

  boolean removeItem(IItem item);

  boolean hasItem(IItem item);

  int getItemCount();

  int getGold();

  void setGold(int gold);

  void addGold(int amount);

  boolean subtractGold(int amount);

  boolean canAfford(int cost);

  int getRequiredExpForCurrentLevel();

  int getRequiredExpForNextLevel();

  ItemBox getItemBox();
}
