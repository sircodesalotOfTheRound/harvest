package com.hive.harvest.parse.expressions.types;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.delimiters.HQLCommaExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLPunctuationToken;
import com.hive.harvest.parse.tokens.HQLToken;
import com.hive.harvest.tools.collections.HQLAppendableCollection;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/9.
 */
public class HQLGenericParameterListExpression extends HQLExpression {
  private final HQLCollection<HQLGenericParameterExpression> entries;
  private final String representation;

  public HQLGenericParameterListExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.entries = readEntries(lexer);
    this.representation = generateRepresentation();
  }

  private HQLCollection<HQLGenericParameterExpression> readEntries(HQLLexer lexer) {
    HQLAppendableCollection<HQLGenericParameterExpression> entries = new HQLAppendableCollection<HQLGenericParameterExpression>();
    lexer.readCurrentAndAdvance(HQLPunctuationToken.class, HQLPunctuationToken.OPEN_DIAMOND);
    while (!lexer.isEof()) {
      if (HQLGenericParameterExpression.canRead(this, lexer)) {
        entries.add(HQLGenericParameterExpression.read(this, lexer));
      } else {
        break;
      }

      if (HQLCommaExpression.canRead(this, lexer)) {
        HQLCommaExpression.read(this, lexer);
      } else {
        break;
      }
    }
    lexer.readCurrentAndAdvance(HQLPunctuationToken.class, HQLPunctuationToken.CLOSE_DIAMOND);

    return entries;
  }

  private String generateRepresentation() {
    StringBuilder builder = new StringBuilder();
    builder.append(HQLPunctuationToken.OPEN_DIAMOND);

    for (HQLGenericParameterExpression parameter : entries) {
      // If the string contains something other than the "<", then append a space.
      if (builder.length() > HQLPunctuationToken.OPEN_DIAMOND.length()) {
        builder.append(", ");
      }
      builder.append(parameter.toString());
    }

    builder.append(HQLPunctuationToken.CLOSE_DIAMOND);
    return builder.toString();
  }

  public HQLCollection<HQLGenericParameterExpression> parameters() {
    return this.entries;
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {

  }

  @Override
  public HQLCollection<HQLToken> children() {
    return null;
  }

  public static HQLGenericParameterListExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLGenericParameterListExpression(parent, lexer);
  }

  public static boolean canRead(HQLExpression parent, HQLLexer lexer) {
    return lexer.currentIs(HQLPunctuationToken.class, HQLPunctuationToken.OPEN_DIAMOND);
  }

  @Override
  public String toString() {
    return representation;
  }
}
