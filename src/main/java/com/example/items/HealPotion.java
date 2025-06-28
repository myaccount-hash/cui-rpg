package com.example.items;

import com.example.entities.Player;
import com.example.actions.Action;
import java.util.List;
import com.example.commands.Command;

public class HealPotion extends Item {
    public HealPotion() {
        super("ヒールポーション", "HPを50回復", 10);
    }

    @Override
    protected List<Command> createActions() {
        return List.of(new UseAction());
    }

    public class UseAction extends Command {
        public UseAction() {
            super("使う", "HPを50回復する", null, null);
        }

        @Override
        public boolean execute() {
            source.heal(50);
            setCommandLog(source.getName() + "はヒールポーションを使い、HPが50回復した！");
            source.getItems().remove(HealPotion.this);
                return true;
            }
        }

    }
