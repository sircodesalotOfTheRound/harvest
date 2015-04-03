package com.hive.harvest.parse.expressions.backtracking;

import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.unknown.HQLUnknownExpression;
import com.hive.harvest.parse.expressions.backtracking.interfaces.HQLBacktrackRule;
import com.hive.harvest.parse.lexer.HQLLexer;

/**
 * Created by sircodesalot on 15/4/3.
 */
public class HQLUnknownExpressionBacktrackRule implements HQLBacktrackRule {
  @Override
  public Class launchForTokensOfType() {
    // Lauches only when nothing else is found.
    return Object.class;
  }

  @Override
  public boolean isMatch(HQLExpression parent, HQLLexer lexer) {
    // Always launches when called.
    return true;
  }

  @Override
  public HQLExpression read(HQLExpression parent, HQLLexer lexer) {
    return HQLUnknownExpression.read(parent, lexer);
  }
}
