package com.hive.harvest.parse.expressions.backtracking;

import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.keywords.statements.HQLFromExpression;
import com.hive.harvest.parse.expressions.keywords.HQLKeywordExpression;
import com.hive.harvest.parse.expressions.backtracking.interfaces.HQLBacktrackRuleBase;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLFromExpressionBacktrackRule extends HQLBacktrackRuleBase<HQLIdentifierToken> {
  public HQLFromExpressionBacktrackRule() {
    super(HQLIdentifierToken.class);
  }

  public boolean isMatch(HQLExpression parent, HQLLexer lexer) {
    return lexer.currentIs(HQLIdentifierToken.class, HQLKeywordExpression.FROM);
  }

  public HQLExpression read(HQLExpression parent, HQLLexer lexer) {
    return HQLFromExpression.read(parent, lexer);
  }
}
