package com.hive.harvest.parse.expressions;

import com.hive.harvest.parse.expressions.backtracking.HQLFromExpressionBacktrackRule;
import com.hive.harvest.parse.expressions.backtracking.HQLIdentifierExpressionBacktrackRule;
import com.hive.harvest.parse.expressions.backtracking.HQLSelectStatementBacktrackRule;
import com.hive.harvest.parse.expressions.backtracking.HQLUnknownExpressionBacktrackRule;
import com.hive.harvest.parse.expressions.backtracking.interfaces.BacktrackRuleSet;
import com.hive.harvest.parse.lexer.HQLLexer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLTreeRootExpression extends HQLExpression {
  private final BacktrackRuleSet rules = new BacktrackRuleSet()
    .add(new HQLIdentifierExpressionBacktrackRule())
    .add(new HQLSelectStatementBacktrackRule())
    .add(new HQLFromExpressionBacktrackRule())
    .add(new HQLUnknownExpressionBacktrackRule());

  private final List<HQLExpression> expressions;

  public HQLTreeRootExpression(HQLLexer lexer) {
    super(null, lexer);

    this.expressions = readExpressions(lexer);
  }

  private List<HQLExpression> readExpressions(HQLLexer lexer) {
    List<HQLExpression> expressions = new ArrayList<HQLExpression>();
    while (!lexer.isEof()) {
      HQLExpression expression = rules.read(this, lexer);

      expressions.add(expression);
    }

    return expressions;
  }

  public Iterable<HQLExpression> expressions() {
    return this.expressions;
  }

  public HQLExpression read(HQLLexer lexer) {
    return new HQLTreeRootExpression(lexer);
  }
}
