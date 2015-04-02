package com.hive.harvest.parse.lexer;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLLexer {
  private final String rawString;
  public HQLLexer(String rawString) {
    this.rawString = rawString;
  }

  public String rawString() { return this.rawString; }
}
