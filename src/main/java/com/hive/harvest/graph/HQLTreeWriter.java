package com.hive.harvest.graph;

import com.hive.harvest.parse.expressions.HQLExpression;

/**
 * Created by sircodesalot on 15/4/3.
 */
public class HQLTreeWriter {
  private int indent;
  private StringBuilder builder;

  public HQLTreeWriter(String command, HQLExpression expression) {
    this.builder = createStringBuilder(command);
    this.indent = 0;

    this.processExpression(expression);
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

  public void processExpression(HQLExpression expression) {
    if (expression == null) {
      return;
    }

    this.display(expression);

    this.increateIndent();
    if (expression.children() == null) {
      System.out.println(expression.getClass());
    }
    for (HQLExpression child : expression.children()) {
      processExpression(child);
    }
    this.decreaseIndent();
  }

  private void display(HQLExpression expression) {
    String content = String.format("%s%-20s : %s", this.getIndent(expression),
      expression,
      expression.getClass().getSimpleName());

    builder.append(content).append("\n");
  }

  private StringBuilder getIndent(HQLExpression expression) {
    StringBuilder builder = new StringBuilder();
    for (int index = 0; index < this.indent; index++) {
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
