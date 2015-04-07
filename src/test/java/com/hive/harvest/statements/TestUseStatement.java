package com.hive.harvest.statements;

import com.hive.harvest.command.HQLCommand;
import com.hive.harvest.parse.expressions.keywords.statements.HQLUseStatement;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/4/7.
 */
public class TestUseStatement {
  @Test
  public void testUseStatement() {
    HQLCommand command = new HQLCommand("use my_database");

    assert (command.tree().expressions().size() == 1);
    HQLUseStatement statement = command.tree().expressions().firstAs(HQLUseStatement.class);

    assert (statement.identifier().equals("my_database"));
  }
}
