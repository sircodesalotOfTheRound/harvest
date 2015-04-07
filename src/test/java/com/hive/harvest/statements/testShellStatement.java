package com.hive.harvest.statements;

import com.hive.harvest.command.HQLCommand;
import com.hive.harvest.parse.expressions.keywords.statements.HQLShellStatementExpression;
import org.junit.Test;

/**
 * Created by sircodesalot on 15/4/7.
 */
public class testShellStatement {
  @Test
  public void testShellStatementExpression() {
    HQLCommand command = new HQLCommand(" ! ls -la");

    HQLShellStatementExpression statement = command.tree().expressions().firstAs(HQLShellStatementExpression.class);
    assert (statement.toString().equals("! ls -la"));
    assert (statement.commandString().equals("ls -la"));
  }
}
