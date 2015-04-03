package com.hive.harvest;

import com.hive.harvest.parse.expressions.keywords.HQLKeywordExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;
import com.hive.harvest.parse.tokens.HQLNumericToken;
import com.hive.harvest.parse.tokens.HQLPunctuationToken;
import com.hive.harvest.parse.tokens.HQLToken;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class TestLexer {
  @Test
  public void testLexer() {
    HQLLexer lexer = new HQLLexer("select * from table where x = 10", true);

    assert (testAndAdvance(lexer, HQLIdentifierToken.class, HQLKeywordExpression.SELECT));
    assert (testAndAdvance(lexer, HQLPunctuationToken.class));
    assert (testAndAdvance(lexer, HQLIdentifierToken.class, HQLKeywordExpression.FROM));
    assert (testAndAdvance(lexer, HQLIdentifierToken.class, "table"));
    assert (testAndAdvance(lexer, HQLIdentifierToken.class, HQLKeywordExpression.WHERE));
    assert (testAndAdvance(lexer, HQLIdentifierToken.class, "x"));
    assert (testAndAdvance(lexer, HQLPunctuationToken.class, "="));
    assert (testAndAdvance(lexer, HQLNumericToken.class, "10"));
  }

  private <T extends HQLToken> boolean testAndAdvance(HQLLexer lexer, Class<T> type, String representation) {
    if (lexer.currentIs(type, representation)) {
      lexer.advance();
      return true;
    } else {
      return false;
    }
  }


  private <T extends HQLToken> boolean testAndAdvance(HQLLexer lexer, Class<T> type) {
    if (lexer.currentIs(type)) {
      lexer.advance();
      return true;
    } else {
      return false;
    }
  }
}
