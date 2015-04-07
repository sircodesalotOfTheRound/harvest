package com.hive.harvest.parse.expressions.tables;

import com.hive.harvest.exceptions.HQLException;
import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.keywords.statements.HQLFromExpression;
import com.hive.harvest.parse.expressions.keywords.HQLKeywordExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;
import com.hive.harvest.parse.tokens.HQLPunctuationToken;
import com.hive.harvest.tools.collections.HQLAppendableCollection;
import com.hive.harvest.tools.collections.HQLCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLTableSetExpression extends HQLExpression {
  private final HQLCollection<HQLTableExpression> tables;

  public HQLTableSetExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.tables = readTables(lexer);
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {
    visitor.visit(this);
  }

  private HQLCollection<HQLTableExpression> readTables(HQLLexer lexer) {
    HQLAppendableCollection<HQLTableExpression> tables = new HQLAppendableCollection<HQLTableExpression>();
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

    return tables;
  }

  public HQLCollection<HQLTableExpression> tables() {
    return this.tables;
  }


  public static boolean canParse(HQLExpression parent, HQLLexer lexer) {
    return parent.parentIs(HQLFromExpression.class);
  }

  public static HQLTableSetExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLTableSetExpression(parent, lexer);
  }
}
