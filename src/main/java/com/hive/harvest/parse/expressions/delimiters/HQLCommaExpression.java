package com.hive.harvest.parse.expressions.delimiters;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.categories.HQLDelimiterExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLPunctuationToken;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/9.
 */
public class HQLCommaExpression extends HQLExpression implements HQLDelimiterExpression {
  private final HQLPunctuationToken comma;

  public HQLCommaExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.comma = readComma(lexer);
  }

  private HQLPunctuationToken readComma(HQLLexer lexer) {
    return lexer.readCurrentAndAdvance(HQLPunctuationToken.class, HQLPunctuationToken.COMMA);
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public HQLCollection<HQLExpression> children() {
    return HQLCollection.EMPTY;
  }

  public static boolean canRead(HQLExpression parent, HQLLexer lexer) {
    return lexer.currentIs(HQLPunctuationToken.class, HQLPunctuationToken.COMMA);
  }

  public static HQLCommaExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLCommaExpression(parent, lexer);
  }
}
