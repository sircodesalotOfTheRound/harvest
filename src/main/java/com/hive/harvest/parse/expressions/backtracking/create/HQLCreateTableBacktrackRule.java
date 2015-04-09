package com.hive.harvest.parse.expressions.backtracking.create;

import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.backtracking.interfaces.HQLBacktrackRuleBase;
import com.hive.harvest.parse.expressions.keywords.HQLKeywordExpression;
import com.hive.harvest.parse.expressions.keywords.statements.create.HQLCreateTableExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;

/**
 * Created by sircodesalot on 15/4/7.
 */
public class HQLCreateTableBacktrackRule extends HQLBacktrackRuleBase<HQLIdentifierToken> {
  public HQLCreateTableBacktrackRule() {
    super(HQLIdentifierToken.class);
  }

  @Override
  public boolean isMatch(HQLExpression parent, HQLLexer lexer) {
    boolean matchesCreateTableExpression = false;

    lexer.setUndoPoint();
    if (lexer.currentIs(HQLIdentifierToken.class, HQLKeywordExpression.CREATE)) {
      lexer.readCurrentAndAdvance();
      if (lexer.currentIs(HQLIdentifierToken.class, HQLKeywordExpression.TABLE)) {
        matchesCreateTableExpression = true;
      }
    }
    lexer.rollbackToUndoPoint();

    return matchesCreateTableExpression;
  }

  @Override
  public HQLExpression read(HQLExpression parent, HQLLexer lexer) {
    return HQLCreateTableExpression.read(parent, lexer);
  }
}
