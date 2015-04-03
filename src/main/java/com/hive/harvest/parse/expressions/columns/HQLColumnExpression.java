package com.hive.harvest.parse.expressions.columns;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.backtracking.HQLNamedColumnExpressionBacktrackRule;
import com.hive.harvest.parse.expressions.backtracking.HQLWildcardColumnExpressionBacktrackRule;
import com.hive.harvest.parse.expressions.backtracking.interfaces.BacktrackRuleSet;
import com.hive.harvest.parse.lexer.HQLLexer;

/**
 * Created by sircodesalot on 15/4/3.
 */
public abstract class HQLColumnExpression extends HQLExpression {
  private static final BacktrackRuleSet<HQLColumnExpression> rules = new BacktrackRuleSet<HQLColumnExpression>()
    .add(new HQLNamedColumnExpressionBacktrackRule())
    .add(new HQLWildcardColumnExpressionBacktrackRule());

  public HQLColumnExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);
  }

  public static HQLColumnExpression read(HQLExpression parent, HQLLexer lexer) {
    return rules.read(parent, lexer);
  }

  public static boolean canParse(HQLExpression parent, HQLLexer lexer) {
    return rules.canParse(parent, lexer);
  }

  public boolean isWildcardColumn() {
    return this instanceof HQLWildcardColumnExpression;
  }

  public boolean isNamedColumn() {
    return this instanceof HQLNamedColumnExpression;
  }
}
