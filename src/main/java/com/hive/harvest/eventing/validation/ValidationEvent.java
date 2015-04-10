package com.hive.harvest.eventing.validation;

import com.hive.harvest.eventing.tooling.LaunchableEvent;
import com.hive.harvest.parse.expressions.HQLExpression;

/**
 * Created by sircodesalot on 15/4/10.
 */
public abstract class ValidationEvent<TListensFor extends HQLExpression> extends LaunchableEvent<TListensFor> {
  public ValidationEvent(Class<TListensFor> listensFor) {
    super(listensFor);
  }
}
