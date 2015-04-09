package com.hive.harvest.parse.expressions.backtracking;

import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.backtracking.interfaces.HQLBacktrackRuleBase;
import com.hive.harvest.parse.expressions.keywords.HQLKeywordExpression;
import com.hive.harvest.parse.expressions.keywords.statements.create.HQLCreateEntityStatement;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;

/**
 * Created by sircodesalot on 15/4/7.
 */
public class HQLCreateStatementBacktrackRule extends HQLBacktrackRuleBase<HQLIdentifierToken> {
  public HQLCreateStatementBacktrackRule() {
    super(HQLIdentifierToken.class);
  }

  @Override
  public boolean isMatch(HQLExpression parent, HQLLexer lexer) {
    return lexer.currentIs(HQLIdentifierToken.class, HQLKeywordExpression.CREATE);
  }

  @Override
  public HQLExpression read(HQLExpression parent, HQLLexer lexer) {
    return HQLCreateEntityStatement.read(parent, lexer);
  }
}
