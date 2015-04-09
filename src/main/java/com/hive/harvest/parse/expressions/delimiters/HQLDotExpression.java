package com.hive.harvest.parse.expressions.delimiters;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.categories.HQLDelimiterExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLPunctuationToken;
import com.hive.harvest.parse.tokens.HQLToken;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/9.
 */
public class HQLDotExpression extends HQLExpression implements HQLDelimiterExpression {
  private final HQLPunctuationToken dot;

  public HQLDotExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.dot = readComma(lexer);
  }

  private HQLPunctuationToken readComma(HQLLexer lexer) {
    return lexer.readCurrentAndAdvance(HQLPunctuationToken.class, HQLPunctuationToken.DOT);
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public HQLCollection<HQLToken> children() {
    return HQLCollection.EMPTY;
  }

  public static boolean canRead(HQLExpression parent, HQLLexer lexer) {
    return lexer.currentIs(HQLPunctuationToken.class, HQLPunctuationToken.DOT);
  }

  public static HQLDotExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLDotExpression(parent, lexer);
  }
}
