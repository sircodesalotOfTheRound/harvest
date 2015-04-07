package com.hive.harvest;

import com.hive.harvest.parse.expressions.tables.HQLNamedTableExpression;
import com.hive.harvest.parse.expressions.columns.HQLNamedColumnExpression;
import com.hive.harvest.parse.expressions.columns.HQLWildcardColumnExpression;
import com.hive.harvest.parse.expressions.keywords.statements.HQLSelectStatement;
import com.hive.harvest.parse.expressions.root.HQLTreeRootExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class TestHQLSelectStatement {
  @Test
  public void testRootExpression() {
    HQLLexer lexer = new HQLLexer("select *, first, second, third, fourth from table1, table2", true);
    HQLTreeRootExpression root = new HQLTreeRootExpression(lexer);

    HQLSelectStatement select = (HQLSelectStatement) root.expressions().first();

    final Set<String> columnNames = fillSet("first", "second", "third", "fourth");
    final Set<String> tableNames = fillSet("table1", "table2");

    assert (select.columnSet().columns().size() == 5);
    assert (select.columnSet().columns().ofType(HQLWildcardColumnExpression.class).size() == 1);
    assert (select.columnSet().columns().ofType(HQLNamedColumnExpression.class).size() == 4);
    assert (select.from().tableSet().tables().size() == 2);

    boolean areAllColumnsContainedInTheSetAbove = select.columnSet().columns()
      .ofType(HQLNamedColumnExpression.class)
      .all(new Predicate<HQLNamedColumnExpression>() {
        @Override
        public boolean test(HQLNamedColumnExpression column) {
          return columnNames.contains(column.identifier());
        }
      });

    boolean areAllTablesContainedInTheSetAbove = select.columnSet().columns()
      .ofType(HQLNamedTableExpression.class)
      .all(new Predicate<HQLNamedTableExpression>() {
        @Override
        public boolean test(HQLNamedTableExpression table) {
          return tableNames.contains(table.identifier());
        }
      });

    assert (areAllColumnsContainedInTheSetAbove);
    assert (areAllTablesContainedInTheSetAbove);
  }

  private Set<String> fillSet(String ... items) {
    Set<String> set = new HashSet<String>();
    for (String item : items) {
      set.add(item);
    }

    return set;
  }
}
