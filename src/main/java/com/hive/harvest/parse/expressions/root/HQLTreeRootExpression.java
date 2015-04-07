package com.hive.harvest.parse.expressions.root;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.backtracking.HQLSelectStatementBacktrackRule;
import com.hive.harvest.parse.expressions.backtracking.HQLUnknownExpressionBacktrackRule;
import com.hive.harvest.parse.expressions.backtracking.interfaces.BacktrackRuleSet;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.tools.collections.HQLAppendableCollection;
import com.hive.harvest.tools.collections.HQLCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLTreeRootExpression extends HQLExpression {
  private static final String ROOT = "(ROOT)";
  private static final BacktrackRuleSet<HQLExpression> rules = new BacktrackRuleSet<HQLExpression>()
    .add(new HQLSelectStatementBacktrackRule())
    .add(new HQLUnknownExpressionBacktrackRule());


  private final HQLCollection<HQLExpression> expressions;

  public HQLTreeRootExpression(HQLLexer lexer) {
    super(null, lexer);

    this.expressions = readExpressions(lexer);
  }

  private HQLCollection<HQLExpression> readExpressions(HQLLexer lexer) {
    HQLAppendableCollection<HQLExpression> expressions = new HQLAppendableCollection<>();
    while (!lexer.isEof()) {
      HQLExpression expression = rules.read(this, lexer);
      expressions.add(expression);
    }

    return expressions;
  }

  public HQLCollection<HQLExpression> expressions() {
    return this.expressions;
  }

  public HQLExpression read(HQLLexer lexer) {
    return new HQLTreeRootExpression(lexer);
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public String toString() {
    return ROOT;
  }
}
