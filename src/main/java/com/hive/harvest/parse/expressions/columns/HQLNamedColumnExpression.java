package com.hive.harvest.parse.expressions.columns;

import com.hive.harvest.exceptions.HQLException;
import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;
import com.hive.harvest.parse.tokens.HQLToken;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/3.
 */
public class HQLNamedColumnExpression extends HQLColumnExpression {
  private final HQLIdentifierToken identifier;

  public HQLNamedColumnExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.identifier = readIdentifier(lexer);
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public HQLCollection<HQLToken> children() {
    return null;
  }

  private HQLIdentifierToken readIdentifier(HQLLexer lexer) {
    if (!lexer.currentIs(HQLIdentifierToken.class)) {
      throw new HQLException("Identifier Expressions must be located on identifiers");
    }

    return (HQLIdentifierToken) lexer.readCurrentAndAdvance(HQLIdentifierToken.class);
  }

  public static HQLColumnExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLNamedColumnExpression(parent, lexer);
  }

  public String identifier() {
    return identifier.identifier();
  }

  @Override
  public String toString() {
    return String.format("[%s]", this.identifier());
  }
}
