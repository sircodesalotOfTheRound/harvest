package com.hive.harvest.parse.expressions.columns;

import com.hive.harvest.exceptions.HQLException;
import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.primitive.HQLIdentifierExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;
import com.hive.harvest.tools.collections.HQLAppendableCollection;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/3.
 */
public class HQLNamedColumnExpression extends HQLColumnExpression {
  private final HQLIdentifierExpression identifier;

  public HQLNamedColumnExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.identifier = readIdentifier(lexer);
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public HQLCollection<HQLExpression> children() {
    return new HQLAppendableCollection<HQLExpression>(identifier);
  }

  private HQLIdentifierExpression readIdentifier(HQLLexer lexer) {
    if (!lexer.currentIs(HQLIdentifierToken.class)) {
      throw new HQLException("Identifier Expressions must be located on identifiers");
    }

    return HQLIdentifierExpression.read(this, lexer);
  }

  public static HQLColumnExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLNamedColumnExpression(parent, lexer);
  }

  public HQLIdentifierExpression identifier() {
    return identifier;
  }

  @Override
  public String toString() {
    return String.format("[%s]", this.identifier());
  }
}
