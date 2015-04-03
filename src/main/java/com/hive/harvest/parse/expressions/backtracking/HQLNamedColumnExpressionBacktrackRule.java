package com.hive.harvest.parse.expressions.backtracking;

import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.keywords.HQLKeywordExpression;
import com.hive.harvest.parse.expressions.backtracking.interfaces.HQLBacktrackRuleBase;
import com.hive.harvest.parse.expressions.columns.HQLNamedColumnExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;

/**
 * Created by sircodesalot on 15/4/3.
 */
public class HQLNamedColumnExpressionBacktrackRule extends HQLBacktrackRuleBase<HQLIdentifierToken> {
  public HQLNamedColumnExpressionBacktrackRule() {
    super(HQLIdentifierToken.class);
  }

  @Override
  public boolean isMatch(HQLExpression parent, HQLLexer lexer) {
    // Return true if we're sitting on a name, but that name isn't a keyword.
    return lexer.currentIs(HQLIdentifierToken.class) && !HQLKeywordExpression.isKeyword(lexer);
  }

  @Override
  public HQLExpression read(HQLExpression parent, HQLLexer lexer) {
    return HQLNamedColumnExpression.read(parent, lexer);
  }
}
