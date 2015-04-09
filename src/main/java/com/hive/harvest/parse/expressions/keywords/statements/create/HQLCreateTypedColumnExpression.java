package com.hive.harvest.parse.expressions.keywords.statements.create;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.types.HQLTypeConstraintExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;
import com.hive.harvest.parse.tokens.HQLToken;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/9.
 */
public class HQLCreateTypedColumnExpression extends HQLExpression {
  private final HQLIdentifierToken identifier;
  private final HQLTypeConstraintExpression type;
  private final HQLColumnCommentExpression comment;

  public HQLCreateTypedColumnExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.identifier = readIdentifier(lexer);
    this.type = readType(lexer);
    this.comment = readComment(lexer);
  }


  private HQLIdentifierToken readIdentifier(HQLLexer lexer) {
    return lexer.readCurrentAndAdvance(HQLIdentifierToken.class);
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

  public HQLIdentifierToken identifier() {
    return this.identifier;
  }

  public HQLTypeConstraintExpression typeConstraint() {
    return this.type;
  }

  public HQLColumnCommentExpression commentInfo() {
    return this.comment;
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {

  }

  @Override
  public HQLCollection<HQLToken> children() {
    return null;
  }

  public static HQLCreateTypedColumnExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLCreateTypedColumnExpression(parent, lexer);
  }

  public static boolean canRead(HQLExpression parent, HQLLexer lexer) {
    return lexer.currentIs(HQLIdentifierToken.class);
  }
}
