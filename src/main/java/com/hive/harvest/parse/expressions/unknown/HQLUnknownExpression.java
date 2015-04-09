package com.hive.harvest.parse.expressions.unknown;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLToken;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLUnknownExpression extends HQLExpression {
  private final HQLToken token;

  public HQLUnknownExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.token = lexer.readCurrentAndAdvance();
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public HQLCollection<HQLExpression> children() {
    return null;
  }

  public HQLToken token() {
    return this.token;
  }

  @Override
  public String toString() {
    return this.token.toString();
  }

  public static HQLExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLUnknownExpression(parent, lexer);
  }
}
