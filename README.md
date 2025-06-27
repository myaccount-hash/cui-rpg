
# 概要
Javaプログラミングの学習用に製作しています。
RPG形式でモンスターと戦うゲームです。
Macで開発しています。動作確認済はMacのターミナルのみです。

# 構成
Session.javaとその内部クラスであるCommandクラスを中心として設計されています。
主要なクラスは以下です。
- Session: 全体の統一的な制御を提供。詳細はSession.javaを参照。
- BattleSession: バトルを制御するSession。
- Session.Command: Sessionが実行する処理。
- Action: 攻撃・魔法等の抽象クラス。実行元、対象を伴うCommand。
- Item.ItemAction : アイテムを使って実行するAction。
- Item: 全てのアイテムの抽象クラス
- Entity: プレイヤー、モンスター等の抽象クラス

# TODO
- [ ] セーブシステム導入
- [ ] Action, ItemAction, Commandの継承関係を整理