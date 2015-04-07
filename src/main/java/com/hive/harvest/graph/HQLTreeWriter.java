package com.hive.harvest.graph;

import com.hive.harvest.parse.expressions.*;
import com.hive.harvest.parse.expressions.columns.HQLColumnSetExpression;
import com.hive.harvest.parse.expressions.columns.HQLNamedColumnExpression;
import com.hive.harvest.parse.expressions.columns.HQLWildcardColumnExpression;
import com.hive.harvest.parse.expressions.keywords.statements.HQLFromExpression;
import com.hive.harvest.parse.expressions.keywords.statements.HQLSelectStatement;
import com.hive.harvest.parse.expressions.primitive.HQLIdentifierExpression;
import com.hive.harvest.parse.expressions.root.HQLTreeRootExpression;
import com.hive.harvest.parse.expressions.tables.HQLNamedTableExpression;
import com.hive.harvest.parse.expressions.tables.HQLTableSetExpression;
import com.hive.harvest.parse.expressions.unknown.HQLUnknownExpression;

/**
 * Created by sircodesalot on 15/4/3.
 */
public class HQLTreeWriter extends HQLNoReturnVisitor {
  private int indent;
  private StringBuilder builder;

  public HQLTreeWriter(String command, HQLExpression expression) {
    this.builder = createStringBuilder(command);
    this.indent = 0;

    this.accept(expression);
  }

  private StringBuilder createStringBuilder(String command) {
    StringBuilder builder = new StringBuilder();
    builder.append(command).append("\n").append("\n");
    return builder;
  }

  private void increateIndent() {
    this.indent += 2;
  }

  private void decreaseIndent() {
    this.indent -= 2;
  }

  @Override
  public void visit(HQLFromExpression expression) {
    this.increateIndent();
    this.onvisited(expression);
    this.acceptAll(expression.tableSet().tables());
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
    this.acceptAll(expression.tables());
    this.decreaseIndent();
  }

  @Override
  public void visit(HQLSelectStatement expression) {
    this.increateIndent();
    this.onvisited(expression);
    this.accept(expression.columnSet());
    this.accept(expression.from());
    this.decreaseIndent();
  }

  @Override
  public void visit(HQLColumnSetExpression expression) {
    this.increateIndent();
    this.onvisited(expression);
    this.acceptAll(expression.columns());
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
    this.increateIndent();
    this.onvisited(expression);
    this.decreaseIndent();
  }

  @Override
  protected void onvisited(HQLExpression expression) {
    String content = String.format("%s%-20s : %s", this.getIndent(), expression, expression.getClass().getSimpleName());
    builder.append(content).append("\n");
  }

  private StringBuilder getIndent() {
    StringBuilder builder = new StringBuilder();
    for (int index = 0; index < this.indent - 2; index++) {
      builder.append(" ");
    }

    if (this.indent > 2) {
      return builder.append("+").append("-");
    } else {
      return builder;
    }
  }

  @Override
  public String toString() {
    return builder.toString();
  }
}
