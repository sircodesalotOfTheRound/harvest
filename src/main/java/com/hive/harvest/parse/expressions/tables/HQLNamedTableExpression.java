package com.hive.harvest.parse.expressions.tables;

import com.hive.harvest.exceptions.HQLException;
import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/3.
 */
public class HQLNamedTableExpression extends HQLTableExpression {
  private final HQLIdentifierToken identifier;

  public HQLNamedTableExpression(HQLExpression parent, HQLLexer lexer) {
    super (parent, lexer);

    this.validateLexing(parent, lexer);
    this.identifier = readIdentifier(lexer);
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public HQLCollection<HQLExpression> children() {
    return null;
  }

  private void validateLexing(HQLExpression parent, HQLLexer lexer) {
    if (!lexer.currentIs(HQLIdentifierToken.class)) {
      throw new HQLException("Named table expressions must read ");
    } else if (!parent.parentIs(HQLTableSetExpression.class)) {
      throw new HQLException("Parent of HQLTableExpression must be HQLTableSetExpression");
    }
  }

  private HQLIdentifierToken readIdentifier(HQLLexer lexer) {
    return (HQLIdentifierToken) lexer.readCurrentAndAdvance(HQLIdentifierToken.class);
  }

  public static HQLNamedTableExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLNamedTableExpression(parent, lexer);
  }

  public String identifier() {
    return identifier.identifier();
  }

  @Override
  public String toString() {
    return String.format("[%s]", this.identifier());
  }
}
