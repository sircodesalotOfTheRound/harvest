package com.hive.harvest.parse.lexer;

/**
 * Created by sircodesalot on 15/4/3.
 */
public class HQLLexPosition {
  private final int line;
  private final int column;
  private final int offset;

  public HQLLexPosition(int line, int column, int offset) {
    this.line = line;
    this.column = column;
    this.offset = offset;
  }

  public int line() {
    return this.line;
  }

  public int column() {
    return this.column;
  }

  public int offset() {
    return this.offset;
  }
}
