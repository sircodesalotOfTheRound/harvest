package com.hive.harvest.parse.expressions.root;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.backtracking.*;
import com.hive.harvest.parse.expressions.backtracking.create.HQLCreateTableBacktrackRule;
import com.hive.harvest.parse.expressions.backtracking.interfaces.HQLBacktrackingRuleSet;
import com.hive.harvest.parse.expressions.unknown.HQLUnknownExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.tools.collections.HQLAppendableCollection;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLTreeRootExpression extends HQLExpression {
  private static final String ROOT = "(ROOT)";
  private static final HQLBacktrackingRuleSet<HQLExpression> rules = new HQLBacktrackingRuleSet<HQLExpression>()
    .add(new HQLSelectStatementBacktrackRule())
    .add(new HQLFromExpressionBacktrackRule())
    .add(new HQLUseStatementBacktrackRule())
    .add(new HQLCreateTableBacktrackRule())
    .add(new HQLDropEntityStatementBacktrackRule())
    .add(new HQLShellStatementBacktrackRule());


  private final HQLCollection<HQLExpression> expressions;

  public HQLTreeRootExpression(HQLLexer lexer) {
    super(null, lexer);

    this.expressions = readExpressions(lexer);
  }

  private HQLCollection<HQLExpression> readExpressions(HQLLexer lexer) {
    HQLAppendableCollection<HQLExpression> expressions = new HQLAppendableCollection<HQLExpression>();
    while (!lexer.isEof()) {
      HQLExpression expression = rules.read(this, lexer);

      if (expression != null) {
        expressions.add(expression);
      } else {
        expressions.add(new HQLUnknownExpression(this, lexer));
      }
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
  public HQLCollection<HQLExpression> children() {
    return this.expressions;
  }

  @Override
  public String toString() {
    return ROOT;
  }
}
