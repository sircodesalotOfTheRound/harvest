package com.hive.harvest.parse.expressions.columns;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLPunctuationToken;
import com.hive.harvest.parse.tokens.HQLToken;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLWildcardColumnExpression extends HQLColumnExpression {
  public static String WILDCARD = "*";
  public HQLToken token;

  public HQLWildcardColumnExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.token = readToken(lexer);
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {
    visitor.visit(this);
  }

  private HQLToken readToken(HQLLexer lexer) {
    return lexer.readCurrentAndAdvance(HQLPunctuationToken.class, WILDCARD);
  }

  public static HQLWildcardColumnExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLWildcardColumnExpression(parent, lexer);
  }
}
