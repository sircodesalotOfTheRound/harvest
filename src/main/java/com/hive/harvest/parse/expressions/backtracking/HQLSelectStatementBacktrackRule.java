package com.hive.harvest.parse.expressions.backtracking;

import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.HQLKeywordExpression;
import com.hive.harvest.parse.expressions.HQLSelectStatement;
import com.hive.harvest.parse.expressions.backtracking.interfaces.HQLBacktrackRuleBase;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLSelectStatementBacktrackRule extends HQLBacktrackRuleBase<HQLIdentifierToken> {
  public HQLSelectStatementBacktrackRule() {
    super(HQLIdentifierToken.class);
  }

  public boolean isMatch(HQLExpression parent, HQLLexer lexer) {
    return lexer.currentIs(HQLIdentifierToken.class, HQLKeywordExpression.SELECT);
  }

  public HQLExpression read(HQLExpression parent, HQLLexer lexer) {
    return HQLSelectStatement.read(parent, lexer);
  }
}
