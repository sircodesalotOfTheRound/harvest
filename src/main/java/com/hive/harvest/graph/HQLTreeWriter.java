package com.hive.harvest.graph;

import com.hive.harvest.parse.expressions.*;
import com.hive.harvest.parse.expressions.columns.HQLColumnSetExpression;
import com.hive.harvest.parse.expressions.columns.HQLNamedColumnExpression;
import com.hive.harvest.parse.expressions.columns.HQLWildcardColumnExpression;
import com.hive.harvest.parse.expressions.statements.HQLSelectStatement;
import com.hive.harvest.parse.expressions.tables.HQLTableSetExpression;

/**
 * Created by sircodesalot on 15/4/3.
 */
public class HQLTreeWriter extends HQLNoReturnVisitor {
  private int indent;
  private StringBuilder builder;

  public HQLTreeWriter(HQLExpression expression) {
    this.builder = new StringBuilder();
    this.indent = 0;

    this.accept(expression);
  }

  private void increateIndent() {
    this.indent += 1;
  }

  private void decreaseIndent() {
    this.indent -= 1;
  }

  @Override
  public void visit(HQLFromExpression expression) {
    this.increateIndent();
    this.onvisited(expression);
    this.acceptAll(expression.tables());
    this.decreaseIndent();
  }

  @Override
  public void visit(HQLIdentifierExpression expression) {
    this.increateIndent();
    this.onvisited(expression);
    this.decreaseIndent();
  }

  @Override
  public void visit(HQLNamedTableExpression expression) {
    this.increateIndent();
    this.onvisited(expression);
    this.decreaseIndent();
  }

  @Override
  public void visit(HQLUnknownExpression expression) {
    this.increateIndent();
    this.onvisited(expression);
    this.decreaseIndent();
  }

  @Override
  public void visit(HQLTreeRootExpression expression) {
    this.increateIndent();
    this.onvisited(expression);
    this.acceptAll(expression.expressions());
    this.decreaseIndent();
  }

  @Override
  public void visit(HQLTableSetExpression expression) {
    this.increateIndent();
    this.onvisited(expression);
    this.acceptAll(expression);
    this.decreaseIndent();
  }

  @Override
  public void visit(HQLSelectStatement expression) {
    this.increateIndent();
    this.onvisited(expression);
    this.acceptAll(expression.columns());
    this.accept(expression.from());
    this.decreaseIndent();
  }

  @Override
  public void visit(HQLColumnSetExpression expression) {
    this.increateIndent();
    this.onvisited(expression);
    this.acceptAll(expression);
    this.decreaseIndent();
  }

  @Override
  public void visit(HQLWildcardColumnExpression expression) {
    this.increateIndent();
    this.onvisited(expression);
    this.decreaseIndent();
  }

  @Override
  public void visit(HQLNamedColumnExpression expression) {
    this.indent += 1;
    this.onvisited(expression);
    this.indent -= 1;
  }

  @Override
  protected void onvisited(HQLExpression expression) {
    String content = String.format("%s%s : %s", this.getIndent(), expression, expression.getClass());
    builder.append(content).append("\n");
  }

  private StringBuilder getIndent() {
    StringBuilder builder = new StringBuilder();
    for (int index = 0; index < this.indent; index++) {
      builder.append(" ");
    }
    return builder;
  }

  @Override
  public String toString() {
    return builder.toString();
  }
}
