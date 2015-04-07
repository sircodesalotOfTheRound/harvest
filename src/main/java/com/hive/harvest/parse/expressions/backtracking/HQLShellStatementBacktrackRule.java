package com.hive.harvest.parse.expressions.backtracking;

import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.backtracking.interfaces.HQLBacktrackRuleBase;
import com.hive.harvest.parse.expressions.keywords.statements.HQLShellStatementExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLPunctuationToken;
import com.hive.harvest.parse.tokens.HQLWhitespaceToken;

/**
 * Created by sircodesalot on 15/4/7.
 */
public class HQLShellStatementBacktrackRule extends HQLBacktrackRuleBase<HQLPunctuationToken> {
  public HQLShellStatementBacktrackRule() {
    super(HQLPunctuationToken.class);
  }

  @Override
  public boolean isMatch(HQLExpression parent, HQLLexer lexer) {
    return lexer.currentIs(HQLPunctuationToken.class, "!") && allPreviousAreWhitespaces(lexer);
  }

  private boolean allPreviousAreWhitespaces(HQLLexer lexer) {
    for (int index = lexer.currentIndex() - 1; index >= 0; index--) {
      if (!lexer.atIndex(index).is(HQLWhitespaceToken.class)) {
        return false;
      }
    }

    return true;
  }

  @Override
  public HQLExpression read(HQLExpression parent, HQLLexer lexer) {
    return HQLShellStatementExpression.read(parent, lexer);
  }
}
