package com.hive.harvest.statements;

import com.hive.harvest.command.HQLCommand;
import com.hive.harvest.parse.expressions.keywords.statements.HQLCreateStatement;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/4/7.
 */
public class TestCreateExpression {
  @Test
  public void testCreateExpression() {
    HQLCommand command = new HQLCommand("create table my_table");
    HQLCreateStatement statement = command.tree().expressions().firstAs(HQLCreateStatement.class);

    assert (statement.entityType() == HQLCreateStatement.EntityType.TABLE);
    assert (statement.identifier().equals("my_table"));
  }
}
