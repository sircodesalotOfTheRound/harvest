package com.hive.harvest.parse.expressions;

import com.hive.harvest.parse.lexer.HQLLexer;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLIdentifierExpression implements HQLExpression {
  public HQLIdentifierExpression(HQLExpression parent, HQLLexer lexer) {

  }

  public static HQLExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLIdentifierExpression(parent, lexer);
  }
}
