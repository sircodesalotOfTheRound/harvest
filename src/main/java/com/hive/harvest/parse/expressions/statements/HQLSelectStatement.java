package com.hive.harvest.parse.expressions.statements;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.HQLFromExpression;
import com.hive.harvest.parse.expressions.HQLKeywordExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.expressions.columns.HQLColumnSetExpression;
import com.hive.harvest.parse.expressions.tables.HQLTableSetExpression;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLSelectStatement extends HQLKeywordExpression {
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

  public HQLColumnSetExpression columns() {
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
