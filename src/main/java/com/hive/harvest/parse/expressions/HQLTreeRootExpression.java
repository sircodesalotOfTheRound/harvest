package com.hive.harvest.parse.expressions;

import com.hive.harvest.parse.expressions.backtracking.HQLSelectStatementBacktrackRule;
import com.hive.harvest.parse.expressions.backtracking.HQLUnknownExpressionBacktrackRule;
import com.hive.harvest.parse.expressions.backtracking.interfaces.BacktrackRuleSet;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.tools.HQLCollectionExpression;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLTreeRootExpression extends HQLExpression {
  private static final BacktrackRuleSet<HQLExpression> rules = new BacktrackRuleSet<HQLExpression>()
    .add(new HQLSelectStatementBacktrackRule())
    .add(new HQLUnknownExpressionBacktrackRule());

  private final HQLCollectionExpression<HQLExpression> expressions;

  public HQLTreeRootExpression(HQLLexer lexer) {
    super(null, lexer);

    this.expressions = readExpressions(lexer);
  }

  private HQLCollectionExpression<HQLExpression> readExpressions(HQLLexer lexer) {
    List<HQLExpression> expressions = new ArrayList<HQLExpression>();
    while (!lexer.isEof()) {
      HQLExpression expression = rules.read(this, lexer);
      expressions.add(expression);
    }

    return new HQLCollectionExpression.HQLDefaultCollection<HQLExpression>(expressions);
  }

  public HQLCollectionExpression<HQLExpression> expressions() {
    return this.expressions;
  }

  public HQLExpression read(HQLLexer lexer) {
    return new HQLTreeRootExpression(lexer);
  }
}
