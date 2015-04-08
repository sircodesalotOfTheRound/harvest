package com.hive.harvest.statements;

import com.hive.harvest.command.HQLCommand;
import com.hive.harvest.parse.expressions.keywords.statements.HQLCreateEntityStatement;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/4/7.
 */
public class TestCreateExpression {
  @Test
  public void testCreateExpression() {
    HQLCommand command = new HQLCommand("create table my_table");
    HQLCreateEntityStatement statement = command.tree().expressions().firstAs(HQLCreateEntityStatement.class);

    assert (statement.entityType() == HQLCreateEntityStatement.EntityType.TABLE);
    assert (statement.identifier().equals("my_table"));
  }
}
