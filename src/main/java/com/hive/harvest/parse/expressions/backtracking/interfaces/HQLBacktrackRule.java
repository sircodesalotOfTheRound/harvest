package com.hive.harvest.parse.expressions.backtracking.interfaces;

/**
 * Created by sircodesalot on 15/4/2.
 */

import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.lexer.HQLLexer;

public interface HQLBacktrackRule {
  Class launchForTokensOfType();

  boolean isMatch(HQLExpression parent, HQLLexer lexer);
  HQLExpression read(HQLExpression parent, HQLLexer lexer);
}
