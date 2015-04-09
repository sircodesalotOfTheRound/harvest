package com.hive.harvest.parse.expressions.keywords.statements.create;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.keywords.HQLKeywordExpression;
import com.hive.harvest.parse.expressions.primitive.HQLIdentifierExpression;
import com.hive.harvest.parse.expressions.types.HQLTypeConstraintExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;
import com.hive.harvest.tools.collections.HQLAppendableCollection;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/9.
 */
public class HQLCreateTypedColumnExpression extends HQLExpression {
  private final HQLIdentifierExpression identifier;
  private final HQLTypeConstraintExpression type;
  private final HQLColumnCommentExpression comment;
  private final String representation;
  private final HQLCollection<HQLExpression> children;

  public HQLCreateTypedColumnExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.identifier = readIdentifier(lexer);
    this.type = readType(lexer);
    this.comment = readComment(lexer);
    this.representation = generateRepresentation();
    this.children = new HQLAppendableCollection<HQLExpression>(identifier, type, comment);
  }


  private HQLIdentifierExpression readIdentifier(HQLLexer lexer) {
    return HQLIdentifierExpression.read(this, lexer);
  }

  private HQLTypeConstraintExpression readType(HQLLexer lexer) {
    return HQLTypeConstraintExpression.read(this, lexer);
  }

  private HQLColumnCommentExpression readComment(HQLLexer lexer) {
    if (HQLColumnCommentExpression.canRead(this, lexer)) {
      return HQLColumnCommentExpression.read(this, lexer);
    } else {
      return null;
    }
  }

  public String generateRepresentation() {
    StringBuilder builder = new StringBuilder();
    builder.append(identifier);
    builder.append(":").append(type);

    if (hasComment()) {
      builder.append(comment);
    }
    return builder.toString();
  }

  public HQLIdentifierExpression identifier() {
    return this.identifier;
  }

  public HQLTypeConstraintExpression typeConstraint() {
    return this.type;
  }

  public boolean hasComment() {
    return this.comment != null;
  }

  public HQLColumnCommentExpression commentInfo() {
    return this.comment;
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {

  }

  @Override
  public HQLCollection<HQLExpression> children() {
    return children;
  }

  public static HQLCreateTypedColumnExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLCreateTypedColumnExpression(parent, lexer);
  }

  public static boolean canRead(HQLExpression parent, HQLLexer lexer) {
    return lexer.currentIs(HQLIdentifierToken.class);
  }

  @Override
  public String toString() {
    return representation;
  }
}
