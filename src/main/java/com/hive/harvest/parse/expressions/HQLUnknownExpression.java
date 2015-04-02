package com.hive.harvest.parse.expressions;

import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLToken;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLUnknownExpression extends HQLExpression {
  private final HQLToken token;

  public HQLUnknownExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.token = lexer.readCurrentAndAdvance();
  }

  public HQLToken token() {
    return this.token;
  }

  @Override
  public String toString() {
    return this.token.toString();
  }

  public static HQLExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLUnknownExpression(parent, lexer);
  }
}
