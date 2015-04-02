package com.hive.harvest.parse.expressions.backtracking;

import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.HQLKeywordExpression;
import com.hive.harvest.parse.expressions.backtracking.interfaces.BacktrackRuleBase;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLKeywordExpressionBacktrackRule extends BacktrackRuleBase<HQLIdentifierToken> {
  protected HQLKeywordExpressionBacktrackRule() {
    super(HQLIdentifierToken.class);
  }

  public boolean isMatch(HQLExpression parent, HQLLexer lexer) {
    return HQLKeywordExpression.isKeyword(lexer);
  }

  public HQLExpression read(HQLExpression parent, HQLLexer lexer) {
    return HQLKeywordExpression.read(parent, lexer);
  }
}
