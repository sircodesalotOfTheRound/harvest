package com.hive.harvest.parse.expressions.keywords.statements.create;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.delimiters.HQLCommaExpression;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLPunctuationToken;
import com.hive.harvest.parse.tokens.HQLToken;
import com.hive.harvest.tools.collections.HQLAppendableCollection;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/9.
 */
public class HQLCreateColumnGroupExpression extends HQLExpression {
  private final HQLCollection<HQLTypedExpression> entries;

  public HQLCreateColumnGroupExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.entries = readEntries(lexer);
  }

  private HQLAppendableCollection<HQLTypedExpression> readEntries(HQLLexer lexer) {
    HQLAppendableCollection<HQLTypedExpression> items = new HQLAppendableCollection<HQLTypedExpression>();

    lexer.readCurrentAndAdvance(HQLPunctuationToken.class, HQLPunctuationToken.OPEN_PARENS);
    while (!lexer.isEof()) {
      if (HQLTypedExpression.canRead(this, lexer)) {
        items.add(HQLTypedExpression.read(this, lexer));
      } else {
        break;
      }

      if (HQLCommaExpression.canRead(this, lexer)) {
        HQLCommaExpression.read(this, lexer);
      } else {
        break;
      }
    }
    lexer.readCurrentAndAdvance(HQLPunctuationToken.class, HQLPunctuationToken.CLOSE_PARENS);

    return items;
  }

  public HQLCollection<HQLTypedExpression> entries() {
    return this.entries;
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {

  }

  @Override
  public HQLCollection<HQLToken> children() {
    return null;
  }

  public static HQLCreateColumnGroupExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLCreateColumnGroupExpression(parent, lexer);
  }

  public static boolean canRead(HQLCreateTableExpression expression, HQLLexer lexer) {
    return lexer.currentIs(HQLPunctuationToken.class, HQLPunctuationToken.OPEN_PARENS);
  }
}