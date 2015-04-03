package com.hive.harvest.parse.expressions.backtracking;

import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.backtracking.interfaces.HQLBacktrackRuleBase;
import com.hive.harvest.parse.expressions.columns.HQLWildcardColumnExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLPunctuationToken;

/**
 * Created by sircodesalot on 15/4/3.
 */
public class HQLWildcardColumnExpressionBacktrackRule extends HQLBacktrackRuleBase<HQLPunctuationToken> {

  public HQLWildcardColumnExpressionBacktrackRule() {
    super(HQLPunctuationToken.class);
  }

  @Override
  public boolean isMatch(HQLExpression parent, HQLLexer lexer) {
    return lexer.currentIs(HQLPunctuationToken.class, HQLWildcardColumnExpression.WILDCARD);
  }

  @Override
  public HQLExpression read(HQLExpression parent, HQLLexer lexer) {
    return HQLWildcardColumnExpression.read(parent, lexer);
  }
}
