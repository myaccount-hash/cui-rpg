package com.example.commands;

/*
 * ActionをCommandとして扱うためのアダプタ．
 */
public class ActionCommandAdapter extends Command {
  private final Action action;

  public ActionCommandAdapter(Action action) {
    super(action.getName(), action.getDescription());
    this.action = action;
  }

  @Override
  public boolean execute() {
    boolean result = action.execute();
    setCommandLog(action.getCommandLog());
    return result;
  }
}
