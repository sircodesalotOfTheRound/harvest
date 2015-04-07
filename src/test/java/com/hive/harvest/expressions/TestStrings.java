package com.hive.harvest.expressions;

import com.hive.harvest.parse.expressions.variables.HQLStringExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/4/7.
 */
public class TestStrings {
  @Test
  public void testStringExpression() {
    HQLLexer lexer = new HQLLexer("'This is a string'", true);
    HQLStringExpression expression = HQLStringExpression.read(null, lexer);

    assert (expression.toString().equals("This is a string"));
  }
}
