package com.hive.harvest.parse.expressions.keywords.statements.create;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.categories.HQLCreateEntityExpression;
import com.hive.harvest.parse.expressions.keywords.HQLKeywordExpression;
import com.hive.harvest.parse.expressions.categories.HQLStatementExpression;
import com.hive.harvest.parse.expressions.keywords.statements.create.tools.HQLEntityType;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;
import com.hive.harvest.parse.tokens.HQLToken;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/7.
 */
public class HQLCreateTableExpression extends HQLExpression
  implements HQLStatementExpression, HQLCreateEntityExpression {

  private final HQLIdentifierToken identifier;
  private final HQLCreateColumnGroupExpression columnGroup;

  public HQLCreateTableExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.identifier = this.readIdentifier(lexer);
    this.columnGroup = this.readColumnGroup(lexer);
  }


  private HQLIdentifierToken readIdentifier(HQLLexer lexer) {
    lexer.readCurrentAndAdvance(HQLIdentifierToken.class, HQLKeywordExpression.CREATE);
    lexer.readCurrentAndAdvance(HQLIdentifierToken.class, HQLKeywordExpression.TABLE);
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

  @Override
  public HQLEntityType entityType() {
    return HQLEntityType.TABLE;
  }

  public String identifier() {
    return this.identifier.identifier();
  }

  public HQLCreateColumnGroupExpression columnGroup() {
    return this.columnGroup;
  }

  public static HQLCreateTableExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLCreateTableExpression(parent, lexer);
  }
}
