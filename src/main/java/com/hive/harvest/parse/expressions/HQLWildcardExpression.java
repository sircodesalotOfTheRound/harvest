package com.hive.harvest.parse.expressions;

import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLPunctuationToken;
import com.hive.harvest.parse.tokens.HQLToken;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLWildcardExpression extends HQLExpression {
  public static String WILDCARD = "*";
  public HQLToken token;

  public HQLWildcardExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.token = readToken(lexer);
  }

  private HQLToken readToken(HQLLexer lexer) {
    return lexer.readCurrentAndAdvance(HQLPunctuationToken.class, WILDCARD);
  }

  public static HQLWildcardExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLWildcardExpression(parent, lexer);
  }
}
