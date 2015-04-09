package com.hive.harvest.expressions;

import com.hive.harvest.parse.expressions.identifiers.HQLFullyQualifiedNameExpression;
import com.hive.harvest.parse.expressions.identifiers.HQLVariableExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/4/9.
 */
public class TestVariableExpression {
  @Test
  public void testVariableExpression() {
    HQLLexer lexer = new HQLLexer("${env:LOGNAME}", true);
    HQLVariableExpression variable = HQLVariableExpression.read(null, lexer);

    HQLFullyQualifiedNameExpression key = variable.key();
    HQLFullyQualifiedNameExpression value = variable.value();

    assert (key.toString().equals("env"));
    assert (value.toString().equals("LOGNAME"));
  }
}
