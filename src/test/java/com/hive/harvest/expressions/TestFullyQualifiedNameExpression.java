package com.hive.harvest.expressions;

import com.hive.harvest.parse.expressions.columns.HQLWildcardExpression;
import com.hive.harvest.parse.expressions.identifiers.HQLFullyQualifiedNameExpression;
import com.hive.harvest.parse.expressions.primitive.HQLIdentifierExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/4/9.
 */
public class TestFullyQualifiedNameExpression {
  @Test
  public void testSimpleNamespace() {
    HQLLexer lexer = new HQLLexer("first.second.third.fourth.fifth.*", true);
    HQLFullyQualifiedNameExpression fqn = HQLFullyQualifiedNameExpression.read(null, lexer);

    assert (fqn.members().count() == 6);
    assert (fqn.members().ofType(HQLIdentifierExpression.class).count() == 5);
    assert (fqn.members().ofType(HQLWildcardExpression.class).count() == 1);

    assert (fqn.members().get(0).toString().equals("first"));
    assert (fqn.members().get(1).toString().equals("second"));
    assert (fqn.members().get(2).toString().equals("third"));
    assert (fqn.members().get(3).toString().equals("fourth"));
    assert (fqn.members().get(4).toString().equals("fifth"));
    assert (fqn.members().get(5).toString().equals("*"));
  }
}
