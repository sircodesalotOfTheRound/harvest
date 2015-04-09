package com.hive.harvest.parse.expressions.types;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;
import com.hive.harvest.parse.tokens.HQLToken;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/9.
 */
public class HQLTypeConstraintExpression extends HQLExpression {
  private final HQLIdentifierToken type;

  public HQLTypeConstraintExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.type = this.readType(lexer);
  }

  private HQLIdentifierToken readType(HQLLexer lexer) {
    return lexer.readCurrentAndAdvance(HQLIdentifierToken.class);
  }

  public String type() {
    return this.type.identifier();
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {

  }

  @Override
  public HQLCollection<HQLToken> children() {
    return null;
  }

  public static HQLTypeConstraintExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLTypeConstraintExpression(parent, lexer);
  }
}
