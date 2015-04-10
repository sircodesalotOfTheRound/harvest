package com.hive.harvest.parse.expressions.tables;

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
public class HQLNamedTableExpression extends HQLTableExpression {
  private final HQLIdentifierExpression identifier;

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
    return new HQLAppendableCollection<HQLExpression>(identifier);
  }

  private void validateLexing(HQLExpression parent, HQLLexer lexer) {
    if (!lexer.currentIs(HQLIdentifierToken.class)) {
      throw new HQLException("Named table expressions must read ");
    } else if (!parent.parentIs(HQLTableSetExpression.class)) {
      throw new HQLException("Parent of HQLTableExpression must be HQLTableSetExpression");
    }
  }

  private HQLIdentifierExpression readIdentifier(HQLLexer lexer) {
    return HQLIdentifierExpression.read(this, lexer);
  }

  public static HQLNamedTableExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLNamedTableExpression(parent, lexer);
  }

  public HQLIdentifierExpression identifier() {
    return identifier;
  }

  @Override
  public String toString() {
    return String.format("[%s]", this.identifier());
  }
}
