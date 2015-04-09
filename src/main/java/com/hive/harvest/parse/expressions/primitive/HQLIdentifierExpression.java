package com.hive.harvest.parse.expressions.primitive;

import com.hive.harvest.exceptions.HQLException;
import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.types.HQLGenericParameterListExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;
import com.hive.harvest.parse.tokens.HQLToken;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLIdentifierExpression extends HQLExpression {
  private final HQLIdentifierToken identifier;
  private final HQLGenericParameterListExpression genericParameters;
  private final String representation;

  public HQLIdentifierExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.identifier = readIdentifier(lexer);
    this.genericParameters = readGenericParameters(lexer);
    this.representation = generateRepresentation();
  }

  private HQLIdentifierToken readIdentifier(HQLLexer lexer) {
    if (!lexer.currentIs(HQLIdentifierToken.class)) {
      throw new HQLException("Identifiers must start with Identifier tokens. Found %s", lexer.current());
    }

    return lexer.readCurrentAndAdvance(HQLIdentifierToken.class);
  }

  private HQLGenericParameterListExpression readGenericParameters(HQLLexer lexer) {
    if (HQLGenericParameterListExpression.canRead(this, lexer)) {
      return HQLGenericParameterListExpression.read(this, lexer);
    } else {
      return null;
    }
  }

  private String generateRepresentation() {
    StringBuilder builder = new StringBuilder();
    builder.append(identifier);
    if (hasGenericParamters()) {
      builder.append(genericParameters);
    }
    return builder.toString();
  }

  public String identifier() {
    return identifier.toString();
  }

  public boolean hasGenericParamters() {
    return this.genericParameters != null;
  }

  public HQLGenericParameterListExpression genericParameters() {
    return this.genericParameters;
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public HQLCollection<HQLToken> children() {
    return null;
  }

  public static HQLIdentifierExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLIdentifierExpression(parent, lexer);
  }

  @Override
  public String toString() {
    return this.representation;
  }
}
