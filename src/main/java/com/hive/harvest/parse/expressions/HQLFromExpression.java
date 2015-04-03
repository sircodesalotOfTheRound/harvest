package com.hive.harvest.parse.expressions;

import com.hive.harvest.parse.expressions.tables.HQLTableExpression;
import com.hive.harvest.parse.expressions.tables.HQLTableSetExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;
import com.hive.harvest.tools.HQLCollectionExpression;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLFromExpression extends HQLKeywordExpression {
  private final HQLTableSetExpression tables;

  public HQLFromExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.tables = readTables(lexer);
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

  public HQLCollectionExpression<HQLTableExpression> tables() {
    return this.tables;
  }
}
