package com.hive.harvest.parse.expressions.variables;

import com.hive.harvest.exceptions.HQLException;
import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLPunctuationToken;
import com.hive.harvest.parse.tokens.HQLToken;
import com.hive.harvest.tools.collections.HQLAppendableCollection;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/7.
 */
public class HQLStringExpression extends HQLExpression {
  private final HQLCollection<HQLToken> contents;
  private final String innerString;

  public HQLStringExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.contents = this.readContents(lexer);
    this.innerString = this.readInnerString(this.contents);
  }


  private HQLCollection<HQLToken> readContents(HQLLexer lexer) {
    if (!lexer.currentIs(HQLPunctuationToken.class, "'")) {
      throw new HQLException("Strings must begin with quotations.");
    }

    HQLAppendableCollection<HQLToken> contents = new HQLAppendableCollection<HQLToken>();

    // Include whitespaces, then collect all the tokens until we read two quotations.
    int seenQuotationCount = 0;
    lexer.temporarilyIncludeWhitespaces();
    while (!lexer.isEof() && seenQuotationCount < 2) {
      if (lexer.currentIs(HQLPunctuationToken.class, "'")) {
        seenQuotationCount += 1;
      }

      contents.add(lexer.readCurrentAndAdvance());
    }

    lexer.revertToPreviousWhitespaceInclusionState();
    return contents;
  }

  private String readInnerString(HQLCollection<HQLToken> contents) {
    StringBuilder builder = new StringBuilder();
    for (HQLToken token : contents) {
      if (!token.is(HQLPunctuationToken.class, "'")) {
        builder.append(token);
      }
    }

    return builder.toString();
  }

  @Override
  public String toString() {
    return this.innerString;
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  public HQLCollection<HQLToken> children() {
    return null;
  }

  public static HQLStringExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLStringExpression(parent, lexer);
  }
}
