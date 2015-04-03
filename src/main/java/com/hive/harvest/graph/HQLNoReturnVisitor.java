package com.hive.harvest.graph;

import com.hive.harvest.parse.expressions.*;
import com.hive.harvest.parse.expressions.columns.HQLColumnSetExpression;
import com.hive.harvest.parse.expressions.columns.HQLNamedColumnExpression;
import com.hive.harvest.parse.expressions.columns.HQLWildcardColumnExpression;
import com.hive.harvest.parse.expressions.statements.HQLSelectStatement;
import com.hive.harvest.parse.expressions.tables.HQLTableSetExpression;

/**
 * Created by sircodesalot on 15/4/2.
 */
public abstract class HQLNoReturnVisitor {
  protected abstract void onvisited(HQLExpression expression);

  public void visit(HQLFromExpression expression) {
    this.onvisited(expression);
  }


  public void visit(HQLIdentifierExpression expression) {
    this.onvisited(expression);
  }

  public void visit(HQLNamedTableExpression expression) {
    this.onvisited(expression);
  }

  public void visit(HQLUnknownExpression expression) {
    this.onvisited(expression);
  }

  public void visit(HQLTreeRootExpression expression) {
    this.onvisited(expression);
  }

  public void visit(HQLTableSetExpression expression) {
    this.onvisited(expression);
  }

  public void visit(HQLSelectStatement expression) {
    this.onvisited(expression);
  }

  public void visit(HQLColumnSetExpression expression) {
    this.onvisited(expression);
  }

  public void visit(HQLWildcardColumnExpression expression) {
    this.onvisited(expression);
  }

  public void visit(HQLNamedColumnExpression expression) {
    this.onvisited(expression);
  }

  protected void acceptAll(Iterable<? extends HQLExpression> expressions) {
    for (HQLExpression expression : expressions) {
      expression.accept(this);
    }
  }

  protected void accept(HQLExpression expression) {
    expression.accept(this);
  }
}