package com.hive.harvest.statements;

import com.hive.harvest.command.HQLCommand;
import com.hive.harvest.parse.expressions.identifiers.HQLVariableExpression;
import com.hive.harvest.parse.expressions.keywords.statements.create.HQLCreateTableExpression;
import com.hive.harvest.parse.expressions.keywords.statements.create.HQLCreateTypedColumnExpression;
import com.hive.harvest.parse.expressions.keywords.statements.create.tools.HQLEntityType;
import com.hive.harvest.parse.expressions.primitive.HQLIdentifierExpression;
import com.hive.harvest.parse.expressions.types.HQLTypeConstraintExpression;
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
    assert (statement.identifier().toString().equals("my_table"));
  }

  @Test
  public void testCreateTableWithVariableName() {
    HQLCommand command = new HQLCommand("create table ${hivevar:namespace.value}");
    HQLCreateTableExpression statement = command.tree().expressions().firstAs(HQLCreateTableExpression.class);

    assert (statement.entityType() == HQLEntityType.TABLE);

    assert (statement.identifier().members().first() instanceof HQLVariableExpression);
    assert (statement.identifier().toString().equals("${hivevar:namespace.value}"));

    HQLVariableExpression variable = statement.identifier().members().firstAs(HQLVariableExpression.class);
    assert (variable.key().toString().equals("hivevar"));
    assert (variable.value().toString().equals("namespace.value"));
  }

  @Test
  public void testCreateTableWithCommentedParameter() {
    HQLCommand command = new HQLCommand("create table commented_table (paramter INT COMMENT 'the quick brown fox')");
    HQLCreateTableExpression statement = command.tree().expressions().firstAs(HQLCreateTableExpression.class);

    assert (statement.entityType() == HQLEntityType.TABLE);
    assert (statement.identifier().toString().equals("commented_table"));
    assert (statement.columnGroup().entries().first().commentInfo().commentString().toString().equals("the quick brown fox"));
  }

  @Test
  public void testCreateExpressionWithParameters() {
    HQLCommand command = new HQLCommand("create table my_table (first INT, second STRING)");
    HQLCreateTableExpression statement = command.tree().expressions().firstAs(HQLCreateTableExpression.class);
    HQLCollection<HQLCreateTypedColumnExpression> entries = statement.columnGroup().entries();

    assert (entries.first().identifier().toString().equals("first"));
    assert (entries.first().typeConstraint().type().toString().equals("INT"));

    assert (entries.second().identifier().toString().equals("second"));
    assert (entries.second().typeConstraint().type().toString().equals("STRING"));
  }


  @Test
  public void testCreateExpressionWithGenericParameters() {
    HQLCommand command = new HQLCommand("create table constrained_table (first MAP<INT, STRING>, second ARRAY<INT>)");

    HQLCreateTableExpression statement = command.tree().expressions().firstAs(HQLCreateTableExpression.class);
    HQLCollection<HQLCreateTypedColumnExpression> entries = statement.columnGroup().entries();

    assert (statement.identifier().toString().equals("constrained_table"));

    assert (entries.first().identifier().toString().equals("first"));
    assert (entries.first().typeConstraint().toString().equals("MAP<INT, STRING>"));

    assert (entries.second().identifier().toString().equals("second"));
    assert (entries.second().typeConstraint().toString().equals("ARRAY<INT>"));
  }

  @Test
  public void testCreateExpressionWithStruct() {
    HQLCommand command = new HQLCommand("create table struct_table (structure STRUCT<one:STRING, two:MAP<INT, ARRAY<TIMESTAMP>>>)");

    HQLCreateTableExpression statement = command.tree().expressions().firstAs(HQLCreateTableExpression.class);
    HQLCreateTypedColumnExpression column = statement.columnGroup().entries().first();
    HQLIdentifierExpression constraint = column.typeConstraint().type();

    assert (statement.identifier().toString().equals("struct_table"));

    assert (column.identifier().toString().equals("structure"));
    assert (constraint.hasGenericParamters());
    assert (constraint.toString().equals("STRUCT<one:STRING, two:MAP<INT, ARRAY<TIMESTAMP>>>"));

    assert (constraint.genericParameters().parameters().first().toString().equals("one:STRING"));
    assert (constraint.genericParameters().parameters().first().hasTypeConstraint());
    assert (constraint.genericParameters().parameters().first().identifier().toString().equals("one"));
    assert (constraint.genericParameters().parameters().first().typeConstraint().toString().equals("STRING"));

    assert (constraint.genericParameters().parameters().second().toString().equals("two:MAP<INT, ARRAY<TIMESTAMP>>"));
    assert (constraint.genericParameters().parameters().second().hasTypeConstraint());
    assert (constraint.genericParameters().parameters().second().identifier().toString().equals("two"));
    assert (constraint.genericParameters().parameters().second().typeConstraint().toString().equals("MAP<INT, ARRAY<TIMESTAMP>>"));

    HQLIdentifierExpression secondTypeConstraint = constraint.genericParameters().parameters().second().typeConstraint().type();
    assert (secondTypeConstraint.genericParameters().parameters().second().identifier().toString().equals("ARRAY<TIMESTAMP>"));
    assert(secondTypeConstraint.genericParameters().parameters().second().identifier()
      .genericParameters().parameters().first().toString().equals("TIMESTAMP"));
  }
}
