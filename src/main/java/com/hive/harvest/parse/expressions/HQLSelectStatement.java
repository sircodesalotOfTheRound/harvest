package com.hive.harvest.parse.expressions;

import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;
import com.hive.harvest.parse.tokens.HQLPunctuationToken;
import com.hive.harvest.tools.collections.HQLColumnSet;
import com.hive.harvest.tools.collections.HQLTableSet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLSelectStatement extends HQLKeywordExpression {
  private final HQLColumnSet columns;
  private final HQLTableSet tables;

  public HQLSelectStatement(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.columns = readColumns(lexer);
    this.tables = readTables(lexer);
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

  private HQLColumnSet readColumns(HQLLexer lexer) {
    lexer.readCurrentAndAdvance(HQLIdentifierToken.class, HQLKeywordExpression.SELECT);
    List<HQLExpression> columns = new ArrayList<HQLExpression>();

    while (!lexer.isEof()) {
      // Read the next token. If the token is a keyword, then drop out.
      if (lexer.currentIs(HQLIdentifierToken.class)) {
        if (!HQLKeywordExpression.isKeyword(lexer)) {
          columns.add(HQLIdentifierExpression.read(this, lexer));
        } else {
          break;
        }

      } else if (lexer.currentIs(HQLPunctuationToken.class, HQLWildcardExpression.WILDCARD)) {
        columns.add(HQLWildcardExpression.read(this, lexer));
      }

      // If the following isn't a comma, then drop out.
      if (!lexer.isEof() && lexer.currentIs(HQLPunctuationToken.class, ",")) {
        lexer.readCurrentAndAdvance(HQLPunctuationToken.class);
      } else {
        break;
      }
    }

    return new HQLColumnSet(columns);
  }

  public HQLColumnSet columns() {
    return this.columns;
  }

  public HQLTableSet tables() {
    return this.tables;
  }


  public static HQLSelectStatement read(HQLExpression parent, HQLLexer lexer) {
    return new HQLSelectStatement(parent, lexer);
  }
}
