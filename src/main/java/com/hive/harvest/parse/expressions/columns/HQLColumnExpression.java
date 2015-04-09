package com.hive.harvest.parse.expressions.columns;

import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.backtracking.HQLNamedColumnExpressionBacktrackRule;
import com.hive.harvest.parse.expressions.backtracking.HQLWildcardExpressionBacktrackRule;
import com.hive.harvest.parse.expressions.backtracking.interfaces.HQLBacktrackingRuleSet;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLToken;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/3.
 */
public abstract class HQLColumnExpression extends HQLExpression {
  private static final HQLBacktrackingRuleSet<HQLColumnExpression> rules = new HQLBacktrackingRuleSet<HQLColumnExpression>()
    .add(new HQLNamedColumnExpressionBacktrackRule())
    .add(new HQLWildcardExpressionBacktrackRule());

  public HQLColumnExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);
  }

  @Override
  public HQLCollection<HQLToken> children() {
    return null;
  }

  public static HQLColumnExpression read(HQLExpression parent, HQLLexer lexer) {
    return rules.read(parent, lexer);
  }

  public static boolean canParse(HQLExpression parent, HQLLexer lexer) {
    return rules.canParse(parent, lexer);
  }

  public boolean isWildcardColumn() {
    return this instanceof HQLWildcardExpression;
  }

  public boolean isNamedColumn() {
    return this instanceof HQLNamedColumnExpression;
  }
}
