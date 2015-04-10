package com.hive.harvest.eventing.tooling;

import com.hive.harvest.parse.expressions.HQLExpression;

/**
 * Created by sircodesalot on 15/4/10.
 */
public abstract class LaunchableEvent<TListensFor> {
  private final Class<TListensFor> listensFor;

  public LaunchableEvent(Class<TListensFor> listensFor) {
    this.listensFor = listensFor;
  }

  Class listenForType() {
    return this.listensFor;
  }

  public abstract boolean canLaunch(HQLExpression expression);
  public abstract void launch(HQLExpression expression);
}
