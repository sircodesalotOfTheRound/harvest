package com.hive.harvest.parse.expressions;

import com.hive.harvest.parse.lexer.HQLLexer;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLKeywordExpression implements HQLExpression {
  private static final Set<String> keywords = generateKeywordSet();

  public static final String SELECT = "SELECT";
  public static final String FROM = "FROM";
  public static final String WHERE = "WHERE";

  public HQLKeywordExpression(HQLExpression parent, HQLLexer lexer) {

  }

  public static HQLExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLKeywordExpression(parent, lexer);
  }

  public static boolean isKeyword(HQLLexer lexer) {
    return isKeyword(lexer.current().toString());
  }

  public static boolean isKeyword(String identifier) {
    return keywords.contains(identifier);
  }

  private static Set<String> generateKeywordSet() {
    Set<String> keywords = new HashSet<String>();
    keywords.add(SELECT);
    keywords.add(FROM);
    keywords.add(WHERE);

    return keywords;
  }
}
