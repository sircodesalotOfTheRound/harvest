package com.hive.harvest;

import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.HQLIdentifierExpression;
import com.hive.harvest.parse.expressions.HQLKeywordExpression;
import com.hive.harvest.parse.expressions.HQLTreeRootExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import org.junit.Test;

import java.util.Iterator;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class TestHQLRootExpression {
  @Test
  public void testRootExpression() {
    HQLLexer lexer = new HQLLexer("select something from another", true);
    HQLTreeRootExpression root = new HQLTreeRootExpression(lexer);

    Iterator<HQLExpression> expressions = root.expressions().iterator();

    assert(expressions.next() instanceof HQLKeywordExpression);
    assert(expressions.next() instanceof HQLIdentifierExpression);
    assert(expressions.next() instanceof HQLKeywordExpression);
    assert(expressions.next() instanceof HQLIdentifierExpression);

    assert(!expressions.hasNext());
  }
}
