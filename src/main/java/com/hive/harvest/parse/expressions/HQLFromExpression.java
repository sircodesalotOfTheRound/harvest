package com.hive.harvest.parse.expressions;

import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLFromExpression extends HQLKeywordExpression {
  public HQLFromExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    lexer.readCurrentAndAdvance(HQLIdentifierToken.class, HQLKeywordExpression.FROM);
  }

  public static HQLFromExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLFromExpression(parent, lexer);
  }
}
