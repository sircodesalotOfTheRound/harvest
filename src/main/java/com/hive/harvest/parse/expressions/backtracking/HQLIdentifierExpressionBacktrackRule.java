package com.hive.harvest.parse.expressions.backtracking;

import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.primitive.HQLIdentifierExpression;
import com.hive.harvest.parse.expressions.keywords.HQLKeywordExpression;
import com.hive.harvest.parse.expressions.backtracking.interfaces.HQLBacktrackRuleBase;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLIdentifierExpressionBacktrackRule extends HQLBacktrackRuleBase<HQLIdentifierToken> {
  public HQLIdentifierExpressionBacktrackRule() {
    super(HQLIdentifierToken.class);
  }

  public boolean isMatch(HQLExpression parent, HQLLexer lexer) {
    return lexer.currentIs(HQLIdentifierToken.class) && !HQLKeywordExpression.isKeyword(lexer);
  }

  public HQLExpression read(HQLExpression parent, HQLLexer lexer) {
    return HQLIdentifierExpression.read(parent, lexer);
  }
}
