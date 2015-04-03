package com.hive.harvest.parse.expressions.columns;

import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.HQLIdentifierExpression;
import com.hive.harvest.parse.expressions.HQLKeywordExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;
import com.hive.harvest.parse.tokens.HQLPunctuationToken;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLColumnSetExpression extends HQLExpression implements Iterable<HQLColumnExpression> {
  private final List<HQLColumnExpression> columns;

  public HQLColumnSetExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);
    this.columns = readColumns(lexer);
  }

  private List<HQLColumnExpression> readColumns(HQLLexer lexer) {
    lexer.readCurrentAndAdvance(HQLIdentifierToken.class, HQLKeywordExpression.SELECT);
    List<HQLColumnExpression> columns = new ArrayList<HQLColumnExpression>();

    while (HQLColumnExpression.canParse(this, lexer)) {
      columns.add(HQLColumnExpression.read(this, lexer));


      /*// Read the next token. If the token is a keyword, then drop out.
      if (lexer.currentIs(HQLIdentifierToken.class)) {
        if (!HQLKeywordExpression.isKeyword(lexer)) {
          columns.add(HQLIdentifierExpression.read(this, lexer));
        } else {
          break;
        }

      } else if (lexer.currentIs(HQLPunctuationToken.class, HQLWildcardColumnExpression.WILDCARD)) {
        columns.add(HQLWildcardColumnExpression.read(this, lexer));
      }*/

      // If the following isn't a comma, then drop out.
      if (!lexer.isEof() && lexer.currentIs(HQLPunctuationToken.class, ",")) {
        lexer.readCurrentAndAdvance(HQLPunctuationToken.class);
      } else {
        break;
      }
    }

    return columns;
  }

  public int count() {
    return columns.size();
  }

  public boolean containsWildcardColumn() {
    for (HQLExpression column : columns) {
      if (column instanceof HQLWildcardColumnExpression) {
        return true;
      }
    }

    return false;
  }

  public Iterator<HQLColumnExpression> iterator() {
    return columns.iterator();
  }

  public static HQLColumnSetExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLColumnSetExpression(parent, lexer);
  }
}
