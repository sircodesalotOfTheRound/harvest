package com.hive.harvest.parse.expressions.backtracking;

import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.backtracking.interfaces.HQLBacktrackRuleBase;
import com.hive.harvest.parse.expressions.columns.HQLWildcardExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLPunctuationToken;

/**
 * Created by sircodesalot on 15/4/3.
 */
public class HQLWildcardExpressionBacktrackRule extends HQLBacktrackRuleBase<HQLPunctuationToken> {

  public HQLWildcardExpressionBacktrackRule() {
    super(HQLPunctuationToken.class);
  }

  @Override
  public boolean isMatch(HQLExpression parent, HQLLexer lexer) {
    return lexer.currentIs(HQLPunctuationToken.class, HQLPunctuationToken.WILDCARD);
  }

  @Override
  public HQLExpression read(HQLExpression parent, HQLLexer lexer) {
    return HQLWildcardExpression.read(parent, lexer);
  }
}
