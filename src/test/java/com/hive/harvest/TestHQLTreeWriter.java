package com.hive.harvest;

import com.hive.harvest.command.HQLCommand;
import com.hive.harvest.exceptions.HQLException;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/4/3.
 */
public class TestHQLTreeWriter {
  @Test
  public void testTree() {
    HQLCommand command = new HQLCommand("select first, second, third from table_1, table_2");

    String comparison = String.format("%s\n\n%s", command.text(),
      "(ROOT)               : HQLTreeRootExpression\n" +
      "  SELECT               : HQLSelectStatement\n" +
      "    +-(COLUMNS)            : HQLColumnSetExpression\n" +
      "      +-[first]              : HQLNamedColumnExpression\n" +
      "        +-first                : HQLIdentifierExpression\n" +
      "      +-[second]             : HQLNamedColumnExpression\n" +
      "        +-second               : HQLIdentifierExpression\n" +
      "      +-[third]              : HQLNamedColumnExpression\n" +
      "        +-third                : HQLIdentifierExpression\n" +
      "    +-FROM                 : HQLFromExpression\n" +
      "      +-(TABLES)             : HQLTableSetExpression\n" +
      "        +-[table_1]            : HQLNamedTableExpression\n" +
      "          +-table_1              : HQLIdentifierExpression\n" +
      "        +-[table_2]            : HQLNamedTableExpression\n" +
      "          +-table_2              : HQLIdentifierExpression\n");


    check(command.stringTree(), comparison);
    assert (comparison.equals(command.stringTree()));
  }

  public void check(String lhs, String rhs) {
    if (lhs.length() != rhs.length()) {
      throw new HQLException("Mismatching lengths (%s vs. %s)" , lhs.length(), rhs.length());
    }

    for (int index = 0; index < lhs.length(); index++) {
      if (lhs.charAt(index) != rhs.charAt(index)) {
        throw new HQLException("mismatch at (%s)", index);
      }
    }
  }
}
