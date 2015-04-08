package com.hive.harvest.statements;

import com.hive.harvest.command.HQLCommand;
import com.hive.harvest.parse.expressions.keywords.statements.HQLCreateEntityStatement;
import com.hive.harvest.parse.expressions.keywords.statements.HQLDropEntityStatement;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/4/7.
 */
public class TestDropExpression {
  @Test
  public void testCreateExpression() {
    HQLCommand command = new HQLCommand("drop table my_table");
    HQLDropEntityStatement statement = command.tree().expressions().firstAs(HQLDropEntityStatement.class);

    assert (statement.entityType() == HQLDropEntityStatement.EntityType.TABLE);
    assert (statement.identifier().equals("my_table"));
  }
}
