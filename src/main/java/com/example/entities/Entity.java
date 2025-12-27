package com.example.entities;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.example.actions.Action;
import com.example.actions.Magic;
import com.example.items.Armor;
import com.example.items.Item;
import com.example.items.Weapon;
import com.example.utils.Utils;

// プレイヤーやモンスターを含む抽象クラス．
public abstract class Entity {
  private String name;
  private int hp;
  private int mp;
  private int exp;
  private int level;
  private int baseHp;
  private int baseMp;
  private int baseAttack;
  private int baseDefence;
  protected Weapon weapon;
  protected Armor armor;
  private final List<Action> skills = new ArrayList<>();
  private Entity battleTarget;

  // ItemBoxでアイテムとゴールドを管理
  public ItemBox ItemBox = new ItemBox();

  protected Entity(
      String name,
      int baseHp,
      int baseMp,
      int baseAttack,
      int baseDefence,
      int level,
      List<Action> skills) {
    this.name = name;
    this.level = level;
    this.baseHp = baseHp;
    this.baseMp = baseMp;
    this.baseAttack = baseAttack;
    this.baseDefence = baseDefence;

    // 初期HPとMPを設定
    this.hp = baseHp;
    this.mp = baseMp;
    this.exp = 0;

    // デフォルトの武器と防具を匿名クラスで作成
    this.weapon = new Weapon("素手", "何も持っていない", 0, this, 0) {};
    this.armor = new Armor("素肌", "何も着ていない", 0, this, 0) {};

    // デフォルトで通常攻撃を追加
    this.skills.add(new com.example.actions.NormalAttack(this, null));
    // スキルを設定
    if (skills != null) {
      this.skills.addAll(skills);
    }
  }

  // 基本ゲッター
  public String getName() {
    return name;
  }

  public ItemBox getItemBox() {
    return ItemBox;
  }

  public int getHp() {
    return hp;
  }

  public int getMp() {
    return mp;
  }

  public int getExp() {
    return exp;
  }

  public int getLevel() {
    return level;
  }

  // 種族値ゲッター
  public int getBaseHp() {
    return baseHp;
  }

  public int getBaseMp() {
    return baseMp;
  }

  public int getBaseAttack() {
    return baseAttack;
  }

  public int getBaseDefence() {
    return baseDefence;
  }

  // レベルに応じたステータス計算
  public int getMaxHp() {
    return baseHp + (level - 1) * 10;
  }

  public int getMaxMp() {
    return baseMp + (level - 1) * 5;
  }

  public int getAttack() {
    return baseAttack + (level - 1) * 2 + weapon.getAttack();
  }

  public int getDefence() {
    return baseDefence + (level - 1) * 1 + armor.getDefense();
  }

  public Weapon getWeapon() {
    return weapon;
  }

  public Armor getArmor() {
    return armor;
  }

  // スキル管理
  public List<Action> getSkills() {
    return skills;
  }

  public void addSkill(Action skill) {
    skills.add(skill);
  }

  public void removeSkill(Action skill) {
    skills.remove(skill);
  }

  public void clearSkills() {
    skills.clear();
  }

  // 使用可能なアクションを取得
  public List<Action> getAvailableActions() {
    List<Action> available = new ArrayList<>();
    for (Action action : skills) {
      // 使用可能判定をここで直接実行
      if (action instanceof Magic magic) {
        if (getMp() >= magic.getMpCost()) {
          available.add(action);
        }
      } else {
        // 他のアクションタイプはとりあえず使用可能
        available.add(action);
      }
    }
    return available;
  }

  // 基本セッター（必要最小限）
  protected void setName(String name) {
    this.name = name;
  }

  protected void setHp(int hp) {
    this.hp = hp;
  }

  protected void setMp(int mp) {
    this.mp = mp;
  }

  protected void setExp(int exp) {
    this.exp = exp;
  }

  protected void setLevel(int level) {
    this.level = level;
  }

  protected void setBaseHp(int baseHp) {
    this.baseHp = baseHp;
  }

  protected void setBaseMp(int baseMp) {
    this.baseMp = baseMp;
  }

  protected void setBaseAttack(int baseAttack) {
    this.baseAttack = baseAttack;
  }

  protected void setBaseDefence(int baseDefence) {
    this.baseDefence = baseDefence;
  }

  public void setWeapon(Weapon weapon) {
    // 現在の武器がデフォルト武器でない場合はアイテムに戻す
    if (this.weapon != null && !this.weapon.getName().equals("素手")) {
      addItem(this.weapon);
    }
    // 新しい武器がアイテム内にある場合は取り除く
    if (hasItem(weapon)) {
      removeItem(weapon);
    }
    this.weapon = weapon;
  }

  public void setArmor(Armor armor) {
    // 現在の防具がデフォルト防具でない場合はアイテムに戻す
    if (this.armor != null && !this.armor.getName().equals("素肌")) {
      addItem(this.armor);
    }
    // 新しい防具がアイテム内にある場合は取り除く
    if (hasItem(armor)) {
      removeItem(armor);
    }
    this.armor = armor;
  }

  public void setBattleTarget(Entity battleTarget) {
    this.battleTarget = battleTarget;
    for (Action skill : skills) {
      skill.setTarget(battleTarget);
    }
  }

  public Entity getBattleTarget() {
    if (battleTarget == null) {
      throw new IllegalStateException("battleTargetが設定されていません．");
    }
    return battleTarget;
  }

  public void takeDamage(int damage) {
    this.hp = Math.max(0, this.hp - damage);
  }

  public void heal(int amount) {
    this.hp = Math.min(getMaxHp(), this.hp + amount);
  }

  public void useMp(int amount) {
    this.mp = Math.max(0, this.mp - amount);
  }

  public void restoreMp(int amount) {
    this.mp = Math.min(getMaxMp(), this.mp + amount);
  }

  public void gainExp(int amount) {
    this.exp += amount;
    checkLevelUp();
  }

  // レベルアップ判定と処理
  private void checkLevelUp() {
    int requiredExp = getRequiredExpForNextLevel();
    while (exp >= requiredExp) {
      levelUp();
      requiredExp = getRequiredExpForNextLevel();
    }
  }

  private void levelUp() {
    level++;
    exp -= getRequiredExpForCurrentLevel();

    // HPとMPを全回復
    hp = getMaxHp();
    mp = getMaxMp();
  }

  // 次のレベルに必要な経験値
  public int getRequiredExpForNextLevel() {
    return level * 100;
  }

  // 現在のレベルに必要な経験値
  public int getRequiredExpForCurrentLevel() {
    return (level - 1) * 100;
  }

  // 情報表示
  public String getInfoText() {
    return String.format(
        "%s: %s\n%s: %d\n%s: %d/%d\n%s: %d/%d\n%s: %d\n%s: %d\n%s: %d",
        Utils.format("名前", 8),
        getName(),
        Utils.format("レベル", 8),
        getLevel(),
        Utils.format("HP", 8),
        getHp(),
        getMaxHp(),
        Utils.format("MP", 8),
        getMp(),
        getMaxMp(),
        Utils.format("EXP", 8),
        getExp(),
        Utils.format("攻撃力", 8),
        getAttack(),
        Utils.format("防御力", 8),
        getDefence());
  }

  // ItemBoxへの委譲メソッド
  public List<Item> getItems() {
    return ItemBox.getItems();
  }

  public void addItem(Item item) {
    ItemBox.addItem(item);
  }

  public boolean removeItem(Item item) {
    return ItemBox.removeItem(item);
  }

  public boolean hasItem(Item item) {
    return ItemBox.hasItem(item);
  }

  public int getItemCount() {
    return ItemBox.getItemCount();
  }

  public int getGold() {
    return ItemBox.getGold();
  }

  public void setGold(int gold) {
    ItemBox.setGold(gold);
  }

  public void addGold(int amount) {
    ItemBox.addGold(amount);
  }

  public boolean subtractGold(int amount) {
    return ItemBox.subtractGold(amount);
  }

  public boolean canAfford(int cost) {
    return ItemBox.canAfford(cost);
  }

  /** アイテムとゴールドを管理するクラス */
  public static class ItemBox {
    private final List<Item> items = new ArrayList<>();
    private int gold;

    public ItemBox() {
      this.gold = 0; // 初期所持金
    }

    public ItemBox(int initialGold) {
      this.gold = initialGold;
    }

    // アイテム管理
    public List<Item> getItems() {
      return new ArrayList<>(items); // 防御的コピー
    }

    public void addItem(Item item) {
      if (item != null) {
        items.add(item);
      }
    }

    public boolean removeItem(Item item) {
      return items.remove(item);
    }

    public boolean hasItem(Item item) {
      return items.contains(item);
    }

    public int getItemCount() {
      return items.size();
    }

    public void clearItems() {
      items.clear();
    }

    // アイテム集計機能
    public Map<String, ItemCount> getItemCounts() {
      Map<String, ItemCount> itemCountMap = new LinkedHashMap<>();
      for (Item item : items) {
        String key = item.getName();
        itemCountMap.merge(
            key,
            new ItemCount(item, 1),
            (existing, newCount) -> {
              existing.count++;
              return existing;
            });
      }
      return itemCountMap;
    }

    // ゴールド管理
    public int getGold() {
      return gold;
    }

    public void setGold(int gold) {
      this.gold = Math.max(0, gold);
    }

    public void addGold(int amount) {
      if (amount > 0) {
        this.gold += amount;
      }
    }

    public boolean subtractGold(int amount) {
      if (amount > 0 && this.gold >= amount) {
        this.gold -= amount;
        return true;
      }
      return false;
    }

    public boolean canAfford(int cost) {
      return this.gold >= cost;
    }

    // アイテム個数管理用クラス
    public static class ItemCount {
      public final Item item;
      public int count;

      public ItemCount(Item item, int count) {
        this.item = item;
        this.count = count;
      }

      public String getDisplayName() {
        return count > 1 ? item.getName() + "(" + count + ")" : item.getName();
      }
    }
  }
}
