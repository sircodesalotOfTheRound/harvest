package com.hive.harvest.parse.expressions.keywords.statements;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.categories.HQLStatementExpression;
import com.hive.harvest.parse.expressions.keywords.HQLKeywordExpression;
import com.hive.harvest.parse.expressions.tables.HQLTableSetExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;
import com.hive.harvest.parse.tokens.HQLToken;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLFromExpression extends HQLKeywordExpression implements HQLStatementExpression {
  private final HQLTableSetExpression tableSet;

  public HQLFromExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.tableSet = readTables(lexer);
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public HQLCollection<HQLToken> children() {
    return null;
  }

  private HQLTableSetExpression readTables(HQLLexer lexer) {
    lexer.readCurrentAndAdvance(HQLIdentifierToken.class, HQLKeywordExpression.FROM);
    return HQLTableSetExpression.read(this, lexer);
  }

  public static boolean canParse(HQLExpression parent,  HQLLexer lexer) {
    return lexer.currentIs(HQLIdentifierToken.class, HQLKeywordExpression.FROM);
  }

  public static HQLFromExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLFromExpression(parent, lexer);
  }

  public HQLTableSetExpression tableSet() {
    return this.tableSet;
  }

  @Override
  public String toString() {
    return HQLKeywordExpression.FROM;
  }
}
