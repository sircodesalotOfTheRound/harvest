package com.hive.harvest.parse.expressions.keywords.statements;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.categories.HQLStatementExpression;
import com.hive.harvest.parse.expressions.keywords.HQLKeywordExpression;
import com.hive.harvest.parse.expressions.keywords.statements.create.tools.HQLEntityType;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/7.
 */
public class HQLDropEntityStatement extends HQLExpression implements HQLStatementExpression {
  private final HQLEntityType entityType;
  private final HQLIdentifierToken identifier;

  public HQLDropEntityStatement(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.entityType = this.readHQLEntityType(lexer);
    this.identifier = this.readIdentifier(lexer);
  }

  private HQLEntityType readHQLEntityType(HQLLexer lexer) {
    lexer.readCurrentAndAdvance(HQLIdentifierToken.class, HQLKeywordExpression.DROP);

    if (HQLEntityType.isValidEntityType((HQLIdentifierToken) lexer.current())) {
      return HQLEntityType.fromIdentifierToken(lexer.readCurrentAndAdvance(HQLIdentifierToken.class));
    } else {
      return null;
    }
  }

  private HQLIdentifierToken readIdentifier(HQLLexer lexer) {
    return lexer.readCurrentAndAdvance(HQLIdentifierToken.class);
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {

  }

  @Override
  public HQLCollection<HQLExpression> children() {
    return null;
  }

  public HQLEntityType entityType() {
    return this.entityType;
  }

  public String identifier() {
    return this.identifier.identifier();
  }

  public static HQLDropEntityStatement read(HQLExpression parent, HQLLexer lexer) {
    return new HQLDropEntityStatement(parent, lexer);
  }
}
