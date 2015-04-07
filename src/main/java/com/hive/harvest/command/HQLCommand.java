package com.hive.harvest.command;

import com.hive.harvest.graph.HQLTreeWriter;
import com.hive.harvest.parse.expressions.root.HQLTreeRootExpression;
import com.hive.harvest.parse.lexer.HQLLexer;

/**
 * Created by sircodesalot on 15/4/3.
 */
public class HQLCommand {
  private final String text;
  private final HQLCommandSettings settings;
  private final HQLTreeRootExpression root;

  public HQLCommand(String text, HQLCommandSettings settings) {
    this.text = text.trim();
    this.settings = settings;
    this.root = parseTree(this.text);
  }

  public HQLCommand(String text) {
    this(text, null);
  }

  private HQLTreeRootExpression parseTree(String command) {
    HQLLexer lexer = new HQLLexer(command, true);
    return new HQLTreeRootExpression(lexer);
  }

  public String stringTree() {
    return new HQLTreeWriter(text, root).toString();
  }

  @Override
  public String toString() {
    return stringTree();
  }

  public String text() {
    return this.text;
  }

  public HQLTreeRootExpression tree() {
    return this.root;
  }
}
