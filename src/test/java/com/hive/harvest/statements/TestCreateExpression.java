package com.hive.harvest.statements;

import com.hive.harvest.command.HQLCommand;
import com.hive.harvest.parse.expressions.keywords.statements.create.HQLCreateTableExpression;
import com.hive.harvest.parse.expressions.keywords.statements.create.HQLTypeConstrainedColumnExpression;
import com.hive.harvest.parse.expressions.keywords.statements.create.tools.HQLEntityType;
import com.hive.harvest.tools.collections.HQLCollection;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/4/7.
 */
public class TestCreateExpression {
  @Test
  public void testCreateExpression() {
    HQLCommand command = new HQLCommand("create table my_table");
    HQLCreateTableExpression statement = command.tree().expressions().firstAs(HQLCreateTableExpression.class);

    assert (statement.entityType() == HQLEntityType.TABLE);
    assert (statement.identifier().equals("my_table"));
  }

  @Test
  public void testCreateExpressionWithParameters() {
    HQLCommand command = new HQLCommand("create table my_table (first INT, second STRING)");
    HQLCreateTableExpression statement = command.tree().expressions().firstAs(HQLCreateTableExpression.class);
    HQLCollection<HQLTypeConstrainedColumnExpression> entries = statement.columnGroup().entries();

    assert (entries.first().identifier().toString().equals("first"));
    assert (entries.first().typeConstraint().type().equals("INT"));

    assert (entries.second().identifier().toString().equals("second"));
    assert (entries.second().typeConstraint().type().equals("STRING"));
  }
}
