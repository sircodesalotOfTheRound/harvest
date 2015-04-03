package com.hive.harvest.parse.expressions.statements;

import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.HQLIdentifierExpression;
import com.hive.harvest.parse.expressions.HQLKeywordExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;
import com.hive.harvest.parse.tokens.HQLPunctuationToken;
import com.hive.harvest.parse.expressions.columns.HQLColumnSetExpression;
import com.hive.harvest.tools.collections.HQLTableSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLSelectStatement extends HQLKeywordExpression {
  private final HQLColumnSetExpression columns;
  private final HQLTableSet tables;

  public HQLSelectStatement(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.columns = readColumns(lexer);
    this.tables = readTables(lexer);
  }

  private HQLColumnSetExpression readColumns(HQLLexer lexer) {
    return HQLColumnSetExpression.read(this, lexer);
  }

  private HQLTableSet readTables(HQLLexer lexer) {
    List<HQLExpression> tables = new ArrayList<HQLExpression>();

    // Check to see if there is a 'FROM' clause.
    // If one doesn't exist, stop parsing.
    if (!lexer.currentIs(HQLIdentifierToken.class, HQLKeywordExpression.FROM)) {
      return new HQLTableSet(tables);
    }

    lexer.readCurrentAndAdvance(HQLIdentifierToken.class, HQLKeywordExpression.FROM);

    while (!lexer.isEof()) {
      if (lexer.currentIs(HQLIdentifierToken.class)) {
        tables.add(HQLIdentifierExpression.read(this, lexer));
      }

      // If the following isn't a comma, then drop out.
      if (!lexer.isEof() && lexer.currentIs(HQLPunctuationToken.class, ",")) {
        lexer.readCurrentAndAdvance(HQLPunctuationToken.class);
      } else {
        break;
      }
    }

    return new HQLTableSet(tables);
  }

  public HQLColumnSetExpression columns() {
    return this.columns;
  }

  public HQLTableSet tables() {
    return this.tables;
  }


  public static HQLSelectStatement read(HQLExpression parent, HQLLexer lexer) {
    return new HQLSelectStatement(parent, lexer);
  }
}
