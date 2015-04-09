package com.hive.harvest.parse.expressions.keywords.statements;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.categories.HQLStatementExpression;
import com.hive.harvest.parse.expressions.keywords.HQLKeywordExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.expressions.columns.HQLColumnSetExpression;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLSelectStatement extends HQLKeywordExpression implements HQLStatementExpression {
  private final HQLColumnSetExpression columns;
  private final HQLFromExpression from;

  public HQLSelectStatement(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.columns = readColumns(lexer);
    this.from = readTables(lexer);
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public HQLCollection<HQLExpression> children() {
    return null;
  }

  private HQLColumnSetExpression readColumns(HQLLexer lexer) {
    return HQLColumnSetExpression.read(this, lexer);
  }

  private HQLFromExpression readTables(HQLLexer lexer) {
    if (HQLFromExpression.canParse(this, lexer)) {
      return HQLFromExpression.read(this, lexer);
    } else {
      return null;
    }
  }

  public HQLColumnSetExpression columnSet() {
    return this.columns;
  }

  public HQLFromExpression from() {
    return this.from;
  }


  public static HQLSelectStatement read(HQLExpression parent, HQLLexer lexer) {
    return new HQLSelectStatement(parent, lexer);
  }

  @Override
  public String toString() {
    return HQLKeywordExpression.SELECT;
  }
}
