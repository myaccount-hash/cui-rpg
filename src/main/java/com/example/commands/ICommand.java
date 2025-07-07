package com.example.commands;

import com.example.entities.IEntity;

public interface ICommand {
  String getName();

  String getDescription();

  String getCommandLog();

  void setTarget(IEntity target);

  IEntity getTarget();

  boolean execute();

  void setCommandLog(String commandLog);
}
