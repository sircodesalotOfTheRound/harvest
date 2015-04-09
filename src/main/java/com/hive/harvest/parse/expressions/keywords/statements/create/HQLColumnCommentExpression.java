package com.hive.harvest.parse.expressions.keywords.statements.create;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.keywords.HQLKeywordExpression;
import com.hive.harvest.parse.expressions.variables.HQLStringExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;
import com.hive.harvest.tools.collections.HQLAppendableCollection;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/9.
 */
public class HQLColumnCommentExpression extends HQLExpression {
  private final HQLStringExpression comment;

  public HQLColumnCommentExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.comment = readComment(lexer);
  }

  private HQLStringExpression readComment(HQLLexer lexer) {
    lexer.readCurrentAndAdvance(HQLIdentifierToken.class, HQLKeywordExpression.COMMENT);
    return HQLStringExpression.read(this, lexer);
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {

  }

  @Override
  public HQLCollection<HQLExpression> children() {
    return new HQLAppendableCollection<HQLExpression>(comment);
  }

  public static boolean canRead(HQLExpression parent, HQLLexer lexer) {
    return lexer.currentIs(HQLIdentifierToken.class, HQLKeywordExpression.COMMENT);
  }

  public static HQLColumnCommentExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLColumnCommentExpression(parent, lexer);
  }

  public HQLStringExpression commentString() {
    return this.comment;
  }

  @Override
  public String toString() {
    return String.format("COMMENT '%s'", comment);
  }
}
