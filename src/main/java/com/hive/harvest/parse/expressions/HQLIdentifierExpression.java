package com.hive.harvest.parse.expressions;

import com.hive.harvest.exceptions.HQLException;
import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;
import com.hive.harvest.parse.tokens.HQLToken;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLIdentifierExpression extends HQLExpression {
  private final HQLToken identifier;

  public HQLIdentifierExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.identifier = readIdentifier(lexer);
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {
    visitor.visit(this);
  }

  private HQLToken readIdentifier(HQLLexer lexer) {
    if (!lexer.currentIs(HQLIdentifierToken.class)) {
      throw new HQLException("Identifiers must start with Identifier tokens. Found %s", lexer.current());
    }

    return lexer.readCurrentAndAdvance(HQLIdentifierToken.class);
  }

  public String identifier() {
    return identifier.toString();
  }

  public static HQLExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLIdentifierExpression(parent, lexer);
  }
}
