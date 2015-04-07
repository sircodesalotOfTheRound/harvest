package com.hive.harvest.parse.tokens;

import com.hive.harvest.parse.lexer.HQLLexPosition;

/**
 * Created by sircodesalot on 15/4/2.
 */
public abstract class HQLToken {
  private HQLLexPosition position;

  public HQLToken(HQLLexPosition position) {
    this.position = position;
  }

  public HQLLexPosition position() {
    return this.position;
  }

  public <T> boolean is(Class<T> type) {
    return type.isAssignableFrom(this.getClass());
  }

  public <T> boolean is(Class<T> type, String representation) {
    return is(type) && representation.equalsIgnoreCase(this.toString());
  }

  public <T> boolean isMatchingCase(Class<T> type, String representation) {
    return is(type) && representation.equals(this.toString());
  }
}
