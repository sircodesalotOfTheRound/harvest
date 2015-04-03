package com.hive.harvest.parse.expressions.tables;

import com.hive.harvest.exceptions.HQLException;
import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.HQLFromExpression;
import com.hive.harvest.parse.expressions.HQLKeywordExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;
import com.hive.harvest.parse.tokens.HQLPunctuationToken;
import com.hive.harvest.tools.HQLCollectionExpression;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLTableSetExpression extends HQLCollectionExpression<HQLTableExpression> {
  private final HQLCollectionExpression<HQLTableExpression> tables;

  public HQLTableSetExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    //this.validateLexing(parent, lexer);
    this.tables = readTables(lexer);
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {
    visitor.visit(this);
  }

  @Override
  protected Iterable<HQLTableExpression> contents() {
    return tables;
  }

  private void validateLexing(HQLExpression parent, HQLLexer lexer) {
    // Todo, fix this validation.
    if (!lexer.currentIs(HQLIdentifierToken.class, HQLKeywordExpression.FROM)) {
      throw new HQLException("Table set must be a NamedExpression");
    } else if (parent == null || !parent.parentIs(HQLFromExpression.class)) {
      throw new HQLException("Parent of HQLTableSetExpression must be HQLFromExpression");
    }
  }

  private HQLDefaultCollection<HQLTableExpression> readTables(HQLLexer lexer) {
    List<HQLTableExpression> tables = new ArrayList<HQLTableExpression>();
    while (!lexer.isEof()) {
      // Read the next entry. Or break on failure.
      if (HQLTableExpression.canParse(this, lexer)) {
        tables.add(HQLTableExpression.read(this, lexer));
      } else {
        break;
      }

      // If the following isn't a comma, then drop out.
      if (!lexer.isEof() && lexer.currentIs(HQLPunctuationToken.class, ",")) {
        lexer.readCurrentAndAdvance(HQLPunctuationToken.class);
      } else {
        break;
      }
    }

    return new HQLDefaultCollection<HQLTableExpression>(tables);
  }


  public static boolean canParse(HQLExpression parent, HQLLexer lexer) {
    return parent.parentIs(HQLFromExpression.class);
  }

  public static HQLTableSetExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLTableSetExpression(parent, lexer);
  }
}
