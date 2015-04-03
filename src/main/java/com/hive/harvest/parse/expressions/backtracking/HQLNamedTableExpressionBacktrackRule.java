package com.hive.harvest.parse.expressions.backtracking;

import com.hive.harvest.parse.expressions.*;
import com.hive.harvest.parse.expressions.backtracking.interfaces.HQLBacktrackRuleBase;
import com.hive.harvest.parse.expressions.tables.HQLTableSetExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;

/**
 * Created by sircodesalot on 15/4/3.
 */
public class HQLNamedTableExpressionBacktrackRule extends HQLBacktrackRuleBase<HQLIdentifierToken> {
  public HQLNamedTableExpressionBacktrackRule() {
    super(HQLIdentifierToken.class);
  }

  @Override
  public boolean isMatch(HQLExpression parent, HQLLexer lexer) {
    return (parent != null)
      && parent.parentIs(HQLTableSetExpression.class)
      && lexer.currentIs(HQLIdentifierToken.class)
      && !HQLKeywordExpression.isKeyword(lexer);
  }

  @Override
  public HQLExpression read(HQLExpression parent, HQLLexer lexer) {
    return HQLNamedTableExpression.read(parent, lexer);
  }
}
