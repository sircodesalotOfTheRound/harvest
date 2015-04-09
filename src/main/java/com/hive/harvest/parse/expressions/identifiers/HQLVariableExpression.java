package com.hive.harvest.parse.expressions.identifiers;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.categories.HQLMemberExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLPunctuationToken;
import com.hive.harvest.parse.tokens.HQLToken;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/9.
 */
public class HQLVariableExpression extends HQLExpression implements HQLMemberExpression {
  private final HQLFullyQualifiedNameExpression key;
  private final HQLFullyQualifiedNameExpression value;

  public HQLVariableExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.key = this.readKey(lexer);
    this.value = this.readValue(lexer);
  }

  private HQLFullyQualifiedNameExpression readKey(HQLLexer lexer) {
    lexer.readCurrentAndAdvance(HQLPunctuationToken.class, HQLPunctuationToken.DOLLAR);
    lexer.readCurrentAndAdvance(HQLPunctuationToken.class, HQLPunctuationToken.OPEN_BRACE);
    return HQLFullyQualifiedNameExpression.read(this, lexer);
  }

  private HQLFullyQualifiedNameExpression readValue(HQLLexer lexer) {
    lexer.readCurrentAndAdvance(HQLPunctuationToken.class, HQLPunctuationToken.COLON);
    HQLFullyQualifiedNameExpression fqn = HQLFullyQualifiedNameExpression.read(this, lexer);

    lexer.readCurrentAndAdvance(HQLPunctuationToken.class, HQLPunctuationToken.CLOSE_BRACE);
    return fqn;
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {

  }

  @Override
  public HQLCollection<HQLToken> children() {
    return null;
  }

  public HQLFullyQualifiedNameExpression key() {
    return this.key;
  }

  public HQLFullyQualifiedNameExpression value() {
    return this.value;
  }

  public static HQLVariableExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLVariableExpression(parent, lexer);
  }

  @Override
  public String toString() {
    return String.format("${%s:%s}", key, value);
  }
}
