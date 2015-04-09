package com.hive.harvest.parse.expressions.backtracking;

import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.backtracking.interfaces.HQLBacktrackRuleBase;
import com.hive.harvest.parse.expressions.identifiers.HQLVariableExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLPunctuationToken;

/**
 * Created by sircodesalot on 15/4/9.
 */
public class HQLVariableExpressionBacktrackRule extends HQLBacktrackRuleBase<HQLPunctuationToken> {
  public HQLVariableExpressionBacktrackRule() {
    super(HQLPunctuationToken.class);
  }

  @Override
  public boolean isMatch(HQLExpression parent, HQLLexer lexer) {
    boolean isValidVariableExpression = false;

    lexer.setUndoPoint();
    if(lexer.currentIs(HQLPunctuationToken.class, HQLPunctuationToken.DOLLAR)) {
      lexer.readCurrentAndAdvance();
      if (lexer.currentIs(HQLPunctuationToken.class, HQLPunctuationToken.OPEN_BRACE)) {
        isValidVariableExpression = true;
      }
    }
    lexer.rollbackToUndoPoint();

    return isValidVariableExpression;
  }

  @Override
  public HQLExpression read(HQLExpression parent, HQLLexer lexer) {
    return HQLVariableExpression.read(parent, lexer);
  }
}
