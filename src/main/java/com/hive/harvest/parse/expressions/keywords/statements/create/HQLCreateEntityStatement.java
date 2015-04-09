package com.hive.harvest.parse.expressions.keywords.statements.create;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.keywords.HQLKeywordExpression;
import com.hive.harvest.parse.expressions.keywords.statements.HQLStatementExpression;
import com.hive.harvest.parse.expressions.keywords.statements.create.tools.HQLEntityType;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;
import com.hive.harvest.parse.tokens.HQLToken;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/7.
 */
public class HQLCreateEntityStatement extends HQLExpression implements HQLStatementExpression {
  private final HQLEntityType entityType;
  private final HQLIdentifierToken identifier;
  private final HQLCreateColumnGroupExpression columnGroup;

  public HQLCreateEntityStatement(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.entityType = this.readHQLEntityType(lexer);
    this.identifier = this.readIdentifier(lexer);
    this.columnGroup = this.readColumnGroup(lexer);
  }

  private HQLEntityType readHQLEntityType(HQLLexer lexer) {
    lexer.readCurrentAndAdvance(HQLIdentifierToken.class, HQLKeywordExpression.CREATE);

    if (HQLEntityType.isValidEntityType((HQLIdentifierToken) lexer.current())) {
      return HQLEntityType.fromIdentifierToken(lexer.readCurrentAndAdvance(HQLIdentifierToken.class));
    } else {
      return null;
    }
  }

  private HQLIdentifierToken readIdentifier(HQLLexer lexer) {
    return lexer.readCurrentAndAdvance(HQLIdentifierToken.class);
  }

  private HQLCreateColumnGroupExpression readColumnGroup(HQLLexer lexer) {
    if (HQLCreateColumnGroupExpression.canRead(this, lexer)) {
      return HQLCreateColumnGroupExpression.read(this, lexer);
    } else {
      return null;
    }
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {

  }

  @Override
  public HQLCollection<HQLToken> children() {
    return null;
  }

  public HQLEntityType entityType() {
    return this.entityType;
  }

  public String identifier() {
    return this.identifier.identifier();
  }

  public HQLCreateColumnGroupExpression columnGroup() {
    return this.columnGroup;
  }

  public static HQLCreateEntityStatement read(HQLExpression parent, HQLLexer lexer) {
    return new HQLCreateEntityStatement(parent, lexer);
  }
}
