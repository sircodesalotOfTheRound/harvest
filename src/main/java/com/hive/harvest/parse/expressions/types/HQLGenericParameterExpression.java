package com.hive.harvest.parse.expressions.types;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.primitive.HQLIdentifierExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;
import com.hive.harvest.parse.tokens.HQLPunctuationToken;
import com.hive.harvest.parse.tokens.HQLToken;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/9.
 */
public class HQLGenericParameterExpression extends HQLExpression {
  private final HQLIdentifierExpression identifier;
  private final HQLTypeConstraintExpression typeConstraint;
  private final String representation;

  public HQLGenericParameterExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.identifier = readIdentifier(lexer);
    this.typeConstraint = readType(lexer);
    this.representation = generateRepresentation();
  }


  private HQLIdentifierExpression readIdentifier(HQLLexer lexer) {
    return HQLIdentifierExpression.read(this, lexer);
  }

  private HQLTypeConstraintExpression readType(HQLLexer lexer) {
    if (lexer.currentIs(HQLPunctuationToken.class, HQLPunctuationToken.COLON)) {
      lexer.readCurrentAndAdvance(HQLPunctuationToken.class, HQLPunctuationToken.COLON);
      return HQLTypeConstraintExpression.read(this, lexer);
    } else {
      return null;
    }
  }

  private String generateRepresentation() {
    StringBuilder builder = new StringBuilder();
    builder.append(identifier);
    if (hasTypeConstraint()) {
      builder.append(":");
      builder.append(typeConstraint);
    }
    return builder.toString();
  }

  public boolean hasTypeConstraint() {
    return this.typeConstraint != null;
  }

  public HQLIdentifierExpression identifier() {
    return this.identifier;
  }

  public HQLTypeConstraintExpression typeConstraint() {
    return this.typeConstraint;
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {

  }

  @Override
  public HQLCollection<HQLToken> children() {
    return null;
  }

  public static HQLGenericParameterExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLGenericParameterExpression(parent, lexer);
  }

  public static boolean canRead(HQLExpression parent, HQLLexer lexer) {
    return lexer.currentIs(HQLIdentifierToken.class);
  }

  @Override
  public String toString() {
    return representation;
  }
}
