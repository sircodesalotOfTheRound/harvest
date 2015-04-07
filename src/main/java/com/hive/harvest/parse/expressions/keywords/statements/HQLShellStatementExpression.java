package com.hive.harvest.parse.expressions.keywords.statements;

import com.hive.harvest.exceptions.HQLException;
import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLPunctuationToken;
import com.hive.harvest.parse.tokens.HQLToken;
import com.hive.harvest.tools.collections.HQLAppendableCollection;

/**
 * Created by sircodesalot on 15/4/7.
 */
public class HQLShellStatementExpression extends HQLExpression implements HQLStatementExpression {
  private final HQLAppendableCollection<HQLToken> tokens;
  private final String representation;

  public HQLShellStatementExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.tokens = this.readTokens(lexer);
    this.representation = this.asString(tokens);
  }

  private HQLAppendableCollection<HQLToken> readTokens(HQLLexer lexer) {
    if (!lexer.currentIs(HQLPunctuationToken.class, "!")) {
      throw new HQLException("Shell expressiosn must start with '!'");
    }

    lexer.temporarilyIncludeWhitespaces();
    HQLAppendableCollection<HQLToken> tokens = new HQLAppendableCollection<HQLToken>();
    while (!lexer.isEof()) {
      tokens.add(lexer.readCurrentAndAdvance());
    }
    lexer.revertToPreviousWhitespaceInclusionState();

    return tokens;
  }

  private String asString(HQLAppendableCollection<HQLToken> tokens) {
    StringBuilder builder = new StringBuilder();
    for (HQLToken token : tokens) {
      builder.append(token);
    }

    return builder.toString();
  }

  public String commandString() {
    return this.representation.substring(1, representation.length()).trim();
  }

  @Override
  public String toString() {
    return this.representation;
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {

  }

  public static HQLShellStatementExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLShellStatementExpression(parent, lexer);
  }
}
