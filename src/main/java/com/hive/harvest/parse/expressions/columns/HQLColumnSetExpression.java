package com.hive.harvest.parse.expressions.columns;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.keywords.HQLKeywordExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;
import com.hive.harvest.parse.tokens.HQLPunctuationToken;
import com.hive.harvest.tools.collections.HQLAppendableCollection;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLColumnSetExpression extends HQLExpression {
  private final String COLUMNS = "(COLUMNS)";
  private final HQLCollection<HQLColumnExpression> columns;

  public HQLColumnSetExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);
    this.columns = readColumns(lexer);
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public HQLCollection<HQLExpression> children() {
    return this.columns.castTo(HQLExpression.class);
  }

  private HQLCollection<HQLColumnExpression> readColumns(HQLLexer lexer) {
    lexer.readCurrentAndAdvance(HQLIdentifierToken.class, HQLKeywordExpression.SELECT);
    HQLAppendableCollection<HQLColumnExpression> columns = new HQLAppendableCollection<HQLColumnExpression>();

    while (HQLColumnExpression.canParse(this, lexer)) {
      columns.add(HQLColumnExpression.read(this, lexer));

      // If the following isn't a comma, then drop out.
      if (!lexer.isEof() && lexer.currentIs(HQLPunctuationToken.class, ",")) {
        lexer.readCurrentAndAdvance(HQLPunctuationToken.class);
      } else {
        break;
      }
    }

    return columns;
  }

  public boolean containsWildcardColumn() {
    for (HQLExpression column : columns) {
      if (column instanceof HQLWildcardExpression) {
        return true;
      }
    }

    return false;
  }

  public HQLCollection<HQLColumnExpression> columns() {
    return this.columns;
  }

  public static HQLColumnSetExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLColumnSetExpression(parent, lexer);
  }

  @Override
  public String toString() {
    return COLUMNS;
  }
}
