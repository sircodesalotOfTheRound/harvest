package com.hive.harvest.parse.expressions.backtracking;

import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.HQLUnknownExpression;
import com.hive.harvest.parse.expressions.backtracking.interfaces.HQLBacktrackRule;
import com.hive.harvest.parse.lexer.HQLLexer;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLUnknownExpressionBacktrackRule implements HQLBacktrackRule {
  public Class launchForTokensOfType() { return Object.class; }

  public boolean isMatch(HQLExpression parent, HQLLexer lexer) {
    return true;
  }

  public HQLExpression read(HQLExpression parent, HQLLexer lexer) {
    return HQLUnknownExpression.read(parent, lexer);
  }
}
