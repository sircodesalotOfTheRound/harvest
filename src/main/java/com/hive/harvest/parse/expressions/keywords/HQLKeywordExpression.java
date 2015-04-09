package com.hive.harvest.parse.expressions.keywords;

import com.hive.harvest.exceptions.HQLException;
import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;
import com.hive.harvest.parse.tokens.HQLToken;
import com.hive.harvest.tools.collections.HQLCollection;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sircodesalot on 15/4/2.
 */
public abstract class HQLKeywordExpression extends HQLExpression {
  private static final Set<String> keywords = generateKeywordSet();

  public static final String SELECT = "SELECT";
  public static final String FROM = "FROM";
  public static final String WHERE = "WHERE";
  public static final String USE = "USE";
  public static final String CREATE = "CREATE";
  public static final String DROP = "DROP";

  private final HQLIdentifierToken token;

  public HQLKeywordExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.token = readToken(lexer);
  }

  @Override
  public HQLCollection<HQLToken> children() {
    return null;
  }

  private HQLIdentifierToken readToken(HQLLexer lexer) {
    if (!isKeyword(lexer)) {
      throw new HQLException("Keyword expressions must be keywords");
    }

    return (HQLIdentifierToken)lexer.current();
  }

  public static boolean isKeyword(HQLLexer lexer) {
    return isKeyword(lexer.current().toString());
  }

  public static boolean isKeyword(String identifier) {
    return keywords.contains(identifier.toUpperCase());
  }

  private static Set<String> generateKeywordSet() {
    Set<String> keywords = new HashSet<String>();
    keywords.add(SELECT);
    keywords.add(FROM);
    keywords.add(WHERE);
    keywords.add(USE);
    keywords.add(CREATE);
    keywords.add(DROP);

    return keywords;
  }
}
