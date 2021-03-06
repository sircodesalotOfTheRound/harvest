package com.hive.harvest.parse.expressions.keywords.statements.create;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.delimiters.HQLCommaExpression;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLPunctuationToken;
import com.hive.harvest.tools.collections.HQLAppendableCollection;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/9.
 */
public class HQLCreateColumnGroupExpression extends HQLExpression {
  private static final String COLUMN_GROUP = "(COLUMN GROUP)";
  private final HQLCollection<HQLCreateTypedColumnExpression> entries;

  public HQLCreateColumnGroupExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.entries = readEntries(lexer);
  }

  private HQLAppendableCollection<HQLCreateTypedColumnExpression> readEntries(HQLLexer lexer) {
    HQLAppendableCollection<HQLCreateTypedColumnExpression> items = new HQLAppendableCollection<HQLCreateTypedColumnExpression>();

    lexer.readCurrentAndAdvance(HQLPunctuationToken.class, HQLPunctuationToken.OPEN_PARENS);
    while (!lexer.isEof()) {
      if (HQLCreateTypedColumnExpression.canRead(this, lexer)) {
        items.add(HQLCreateTypedColumnExpression.read(this, lexer));
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

  public HQLCollection<HQLCreateTypedColumnExpression> entries() {
    return this.entries;
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {

  }

  @Override
  public HQLCollection<HQLExpression> children() {
    return this.entries.castTo(HQLExpression.class);
  }

  public static HQLCreateColumnGroupExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLCreateColumnGroupExpression(parent, lexer);
  }

  public static boolean canRead(HQLCreateTableExpression expression, HQLLexer lexer) {
    return lexer.currentIs(HQLPunctuationToken.class, HQLPunctuationToken.OPEN_PARENS);
  }

  @Override
  public String toString() {
    return COLUMN_GROUP;
  }
}
