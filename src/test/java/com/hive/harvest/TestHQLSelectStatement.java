package com.hive.harvest;

import com.hive.harvest.exceptions.HQLException;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.HQLIdentifierExpression;
import com.hive.harvest.parse.expressions.columns.HQLColumnExpression;
import com.hive.harvest.parse.expressions.columns.HQLNamedColumnExpression;
import com.hive.harvest.parse.expressions.statements.HQLSelectStatement;
import com.hive.harvest.parse.expressions.HQLTreeRootExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import org.junit.Test;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class TestHQLSelectStatement {
  @Test
  public void testRootExpression() {
    HQLLexer lexer = new HQLLexer("select *, first, second, third, fourth from table1, table2", true);
    HQLTreeRootExpression root = new HQLTreeRootExpression(lexer);

    Iterator<HQLExpression> expressions = root.expressions().iterator();
    HQLSelectStatement select = (HQLSelectStatement) expressions.next();

    Set<String> columns = fillSet("first", "second", "third", "fourth");
    Set<String> tables = fillSet("table1", "table2");

    int wildcardColumnCount = 0;
    for (HQLColumnExpression column : select.columns()) {
      assert (column.isNamedColumn() || column.isWildcardColumn());

      if (column.isNamedColumn()) {
        HQLNamedColumnExpression namedColumn = (HQLNamedColumnExpression) column;
        assert (columns.contains(namedColumn.identifier()));
      } else {
        wildcardColumnCount += 1;
        assert(wildcardColumnCount == 1);
        assert (column.isWildcardColumn());
      }
    }

    for (HQLExpression table : select.tables()) {
      HQLIdentifierExpression identifier = (HQLIdentifierExpression)table;
      assert (tables.contains(identifier.identifier()));
    }

    assert (!expressions.hasNext());
  }

  private Set<String> fillSet(String ... items) {
    Set<String> set = new HashSet<String>();
    for (String item : items) {
      set.add(item);
    }

    return set;
  }
}
