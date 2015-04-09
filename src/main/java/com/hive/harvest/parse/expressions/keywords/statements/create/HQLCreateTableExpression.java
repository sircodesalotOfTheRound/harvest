package com.hive.harvest.parse.expressions.keywords.statements.create;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.categories.HQLCreateEntityExpression;
import com.hive.harvest.parse.expressions.identifiers.HQLFullyQualifiedNameExpression;
import com.hive.harvest.parse.expressions.keywords.HQLKeywordExpression;
import com.hive.harvest.parse.expressions.categories.HQLStatementExpression;
import com.hive.harvest.parse.expressions.keywords.statements.create.tools.HQLEntityType;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;
import com.hive.harvest.tools.collections.HQLAppendableCollection;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/7.
 */
public class HQLCreateTableExpression extends HQLExpression
  implements HQLStatementExpression, HQLCreateEntityExpression {
  private final String CREATE_TABLE = String.format("(%s %s)", HQLKeywordExpression.CREATE, HQLKeywordExpression.TABLE);

  private final HQLFullyQualifiedNameExpression identifier;
  private final HQLCreateColumnGroupExpression columnGroup;
  private final HQLCollection<HQLExpression> children;

  public HQLCreateTableExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.identifier = this.readIdentifier(lexer);
    this.columnGroup = this.readColumnGroup(lexer);
    this.children = new HQLAppendableCollection<HQLExpression>(identifier, columnGroup);
  }


  private HQLFullyQualifiedNameExpression readIdentifier(HQLLexer lexer) {
    lexer.readCurrentAndAdvance(HQLIdentifierToken.class, HQLKeywordExpression.CREATE);
    lexer.readCurrentAndAdvance(HQLIdentifierToken.class, HQLKeywordExpression.TABLE);
    return HQLFullyQualifiedNameExpression.read(this, lexer);
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
  public HQLCollection<HQLExpression> children() {
    return this.children;
  }

  @Override
  public HQLEntityType entityType() {
    return HQLEntityType.TABLE;
  }

  public HQLFullyQualifiedNameExpression identifier() {
    return this.identifier;
  }

  public HQLCreateColumnGroupExpression columnGroup() {
    return this.columnGroup;
  }

  public static HQLCreateTableExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLCreateTableExpression(parent, lexer);
  }

  @Override
  public String toString() {
    return CREATE_TABLE;
  }
}
