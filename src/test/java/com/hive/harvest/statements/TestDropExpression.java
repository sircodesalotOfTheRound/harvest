package com.hive.harvest.statements;

import com.hive.harvest.command.HQLCommand;
import com.hive.harvest.parse.expressions.keywords.statements.HQLDropEntityStatement;
import com.hive.harvest.parse.expressions.keywords.statements.create.tools.HQLEntityType;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/4/7.
 */
public class TestDropExpression {
  @Test
  public void testCreateExpression() {
    HQLCommand command = new HQLCommand("drop table my_table");
    HQLDropEntityStatement statement = command.tree().expressions().firstAs(HQLDropEntityStatement.class);

    assert (statement.entityType() == HQLEntityType.TABLE);
    assert (statement.identifier().equals("my_table"));
  }
}
