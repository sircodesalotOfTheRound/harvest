package com.hive.harvest.parse.expressions.backtracking;

import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.backtracking.interfaces.HQLBacktrackRuleBase;
import com.hive.harvest.parse.expressions.keywords.HQLKeywordExpression;
import com.hive.harvest.parse.expressions.keywords.statements.HQLCreateEntityStatement;
import com.hive.harvest.parse.expressions.keywords.statements.HQLDropEntityStatement;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;

/**
 * Created by sircodesalot on 15/4/7.
 */
public class HQLDropEntityStatementBacktrackRule extends HQLBacktrackRuleBase<HQLIdentifierToken> {
  public HQLDropEntityStatementBacktrackRule() {
    super(HQLIdentifierToken.class);
  }

  @Override
  public boolean isMatch(HQLExpression parent, HQLLexer lexer) {
    return lexer.currentIs(HQLIdentifierToken.class, HQLKeywordExpression.DROP);
  }

  @Override
  public HQLExpression read(HQLExpression parent, HQLLexer lexer) {
    return HQLDropEntityStatement.read(parent, lexer);
  }
}
