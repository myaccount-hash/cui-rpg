package com.example.items;

import com.example.commands.ICommand;
import com.example.entities.IEntity;
import java.util.List;

public interface IItem {
  String getName();

  String getDescription();

  int getPrice();

  IEntity getOwner();

  List<ICommand> getCommands(IEntity source);
}
