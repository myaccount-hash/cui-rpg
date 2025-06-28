
# 概要
Javaプログラミングの学習用に製作しています。
RPG形式でモンスターと戦うゲームです。CUI上で動作しています。
Macで開発しています。動作確認済はMacのターミナルのみです。

# 構成
Session.javaとその内部クラスであるCommandクラスを中心として設計されています。
主要なクラスは以下です。
- Session: 全体の統一的な制御を提供。詳細はSession.javaを参照。
- BattleSession: バトルを制御するSession。
- Command: Sessionが実行する処理。
- Command: 攻撃・魔法等の抽象クラス。実行元、対象を伴うCommand。
- Command : アイテムを使って実行するCommand。
- Item: 全てのアイテムの抽象クラス
- Entity: プレイヤー、モンスター等の抽象クラス

# TODO
- [ ] セーブシステム導入
- [ ] Command, Command, Commandの継承関係を整理
- [ ] コイン・ショップを追加。