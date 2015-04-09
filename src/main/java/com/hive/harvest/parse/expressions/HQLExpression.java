package com.hive.harvest.parse.expressions;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLToken;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/2.
 */
public abstract class HQLExpression extends HQLToken {
  private final HQLLexer lexer;
  private HQLExpression parent;

  public HQLExpression(HQLExpression parent, HQLLexer lexer) {
    super(lexer.position());
    this.parent = parent;
    this.lexer = lexer;
  }

  public HQLExpression parent() {
    return this.parent;
  }

  public HQLLexer lexer() {
    return this.lexer;
  }

  public <T> boolean parentIs(Class<T> type) {
    return (parent != null) && type.isAssignableFrom(this.getClass());
  }

  public abstract void accept(HQLNoReturnVisitor visitor);

  public abstract HQLCollection<HQLExpression> children();
}
