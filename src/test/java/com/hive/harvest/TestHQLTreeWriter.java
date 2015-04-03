package com.hive.harvest;

import com.hive.harvest.command.HQLCommand;
import com.hive.harvest.graph.HQLTreeWriter;
import com.hive.harvest.parse.expressions.root.HQLTreeRootExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/4/3.
 */
public class TestHQLTreeWriter {
  @Test
  public void testTree() {
    HQLCommand command = new HQLCommand("select first, second, third from table_1, table_2");

    String result = String.format("%s\n\n%s", command.text(),
      "(ROOT)               : HQLTreeRootExpression\n" +
      "  +-SELECT               : HQLSelectStatement\n" +
      "    +-(COLUMNS)            : HQLColumnSetExpression\n" +
      "      +-[first]              : HQLNamedColumnExpression\n" +
      "      +-[second]             : HQLNamedColumnExpression\n" +
      "      +-[third]              : HQLNamedColumnExpression\n" +
      "    +-FROM                 : HQLFromExpression\n" +
      "      +-[table_1]            : HQLNamedTableExpression\n" +
      "      +-[table_2]            : HQLNamedTableExpression\n");

    assert (result.equals(command.tree()));

    System.out.println(command.tree());
  }
}
