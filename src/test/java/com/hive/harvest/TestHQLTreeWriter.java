package com.hive.harvest;

import com.hive.harvest.graph.HQLTreeWriter;
import com.hive.harvest.parse.expressions.HQLTreeRootExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/4/3.
 */
public class TestHQLTreeWriter {
  @Test
  public void testTree() {
    HQLLexer lexer = new HQLLexer("select first, second, third from table_1, table_2", true);
    HQLTreeRootExpression root = new HQLTreeRootExpression(lexer);
    HQLTreeWriter writer = new HQLTreeWriter(root);

    String result =
      "(ROOT)               : HQLTreeRootExpression\n" +
      "  +-SELECT               : HQLSelectStatement\n" +
      "    +-(COLUMNS)            : HQLColumnSetExpression\n" +
      "      +-[first]              : HQLNamedColumnExpression\n" +
      "      +-[second]             : HQLNamedColumnExpression\n" +
      "      +-[third]              : HQLNamedColumnExpression\n" +
      "    +-FROM                 : HQLFromExpression\n" +
      "      +-[table_1]            : HQLNamedTableExpression\n" +
      "      +-[table_2]            : HQLNamedTableExpression\n";

    assert (result.equals(writer.toString()));
  }
}
