package com.hive.harvest.parse.expressions;

import com.hive.harvest.parse.lexer.HQLLexer;

/**
 * Created by sircodesalot on 15/4/2.
 */
public abstract class HQLExpression {
  private HQLExpression parent;

  public HQLExpression(HQLExpression parent, HQLLexer lexer) {
    this.parent = parent;
  }

  public HQLExpression parent() {
    return this.parent;
  }
}
