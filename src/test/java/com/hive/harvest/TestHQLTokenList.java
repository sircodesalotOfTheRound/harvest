package com.hive.harvest;

import com.hive.harvest.parse.lexer.HQLTokenList;
import com.hive.harvest.parse.tokens.*;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class TestHQLTokenList {
  @Test
  public void testTokenization() {
    Map<Class, Integer> tokensCounts = new HashMap<Class, Integer>();
    HQLTokenList tokens = new HQLTokenList("select 1, second.third from table1, where name = 'smith'");

    for (HQLToken token : tokens) {
      Class tokenType = token.getClass();
      if (tokensCounts.containsKey(token.getClass())) {
        int seenTimes = tokensCounts.get(tokenType);
        tokensCounts.put(tokenType, seenTimes + 1);
      } else {
        tokensCounts.put(tokenType, 1);
      }
    }

    assert (tokens.size() == 23);

    assert (tokensCounts.get(HQLWhitespaceToken.class) == 8);
    assert (tokensCounts.get(HQLIdentifierToken.class) == 8);
    assert (tokensCounts.get(HQLNumericToken.class) == 1);
    assert (tokensCounts.get(HQLPunctuationToken.class) == 6);
  }
}
