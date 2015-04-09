package com.hive.harvest.parse.expressions.keywords.statements;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.keywords.HQLKeywordExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;
import com.hive.harvest.parse.tokens.HQLToken;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/7.
 */
public class HQLUseStatement extends HQLExpression implements HQLStatementExpression {
  private final HQLIdentifierToken identifier;

  public HQLUseStatement(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.identifier = readIdentifier(lexer);
  }

  private HQLIdentifierToken readIdentifier(HQLLexer lexer) {
    lexer.readCurrentAndAdvance(HQLIdentifierToken.class, HQLKeywordExpression.USE);

    if (lexer.currentIs(HQLIdentifierToken.class)) {
      return lexer.readCurrentAndAdvance(HQLIdentifierToken.class);
    } else{
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

  public static HQLUseStatement read(HQLExpression parent, HQLLexer lexer) {
    return new HQLUseStatement(parent, lexer);
  }

  public String identifier() {
    return this.identifier.identifier();
  }

  @Override
  public String toString() {
    return identifier.identifier();
  }
}
