package com.hive.harvest;

import com.hive.harvest.parse.expressions.*;
import com.hive.harvest.parse.expressions.statements.HQLSelectStatement;
import com.hive.harvest.parse.lexer.HQLLexer;
import org.junit.Test;

import java.util.Iterator;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class TestHQLRootExpression {
  @Test
  public void testRootExpression() {
    HQLLexer lexer = new HQLLexer("select one, two, three from sometable", true);
    HQLTreeRootExpression root = new HQLTreeRootExpression(lexer);

    Iterator<HQLExpression> expressions = root.expressions().iterator();

    assert (expressions.next() instanceof HQLSelectStatement);
    assert (!expressions.hasNext());
  }
}
