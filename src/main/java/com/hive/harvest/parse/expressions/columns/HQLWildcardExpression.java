package com.hive.harvest.parse.expressions.columns;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.categories.HQLMemberExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLPunctuationToken;
import com.hive.harvest.parse.tokens.HQLToken;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLWildcardExpression extends HQLColumnExpression implements HQLMemberExpression {
  public HQLToken token;

  public HQLWildcardExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.token = readToken(lexer);
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public HQLCollection<HQLExpression> children() {
    return null;
  }

  private HQLToken readToken(HQLLexer lexer) {
    return lexer.readCurrentAndAdvance(HQLPunctuationToken.class, HQLPunctuationToken.WILDCARD);
  }

  public static HQLWildcardExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLWildcardExpression(parent, lexer);
  }

  @Override
  public String toString() {
    return HQLPunctuationToken.WILDCARD;
  }
}
