package com.hive.harvest.parse.lexer;

import com.hive.harvest.exceptions.HQLException;
import com.hive.harvest.parse.tokens.HQLToken;
import com.hive.harvest.parse.tokens.HQLWhitespaceToken;

import java.util.Stack;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLLexer {
  private final String text;
  private final boolean advancePastWhitespace;
  private final HQLTokenList tokens;
  private int currentIndex;
  private Stack<Integer> undoStack;

  public HQLLexer(String text, boolean advancePastWhitespace) {
    this.text = text;
    this.advancePastWhitespace = advancePastWhitespace;
    this.tokens = new HQLTokenList(text);
    this.undoStack = new Stack<Integer>();
  }

  public String text() {
    return this.text;
  }

  public void advance() {
    currentIndex += 1;
    while (!isEof() && currentIs(HQLWhitespaceToken.class)) {
      currentIndex += 1;
    }
  }

  public HQLToken current() {
    return this.tokens.get(currentIndex);
  }

  public HQLToken readCurrentAndAdvance() {
    HQLToken current = this.current();
    this.advance();

    return current;
  }

  public <T extends HQLToken> HQLToken readCurrentAndAdvance(Class<T> type) {
    if (currentIs(type)) {
      return readCurrentAndAdvance();
    } else {
      throw new HQLException("Expected %s, found %s", type, this.current().getClass());
    }
  }

  public <T extends HQLToken> HQLToken readCurrentAndAdvanceMatchCase(Class<T> type, String representation) {
    if (currentIsMatchCase(type, representation)) {
      return readCurrentAndAdvance();
    } else {
      throw new HQLException("Expected (%s : %s), found (%s : %s)",
        representation, type,
        this.current().toString(), this.current().getClass());
    }
  }

  public <T extends HQLToken> HQLToken readCurrentAndAdvance(Class<T> type, String representation) {
    if (currentIs(type, representation)) {
      return readCurrentAndAdvance();
    } else {
      throw new HQLException("Expected (%s : %s), found (%s : %s)",
        representation, type,
        this.current().toString(), this.current().getClass());
    }
  }

  public <T extends HQLToken> boolean currentIs(Class<T> type) {
    return type.isAssignableFrom(current().getClass());
  }

  public <T extends HQLToken> boolean currentIsMatchCase(Class<T> type, String representation) {
    return currentIs(type) && current().toString().equals(representation);
  }

  public <T extends HQLToken> boolean currentIs(Class<T> type, String representation) {
    return currentIs(type) && current().toString().equalsIgnoreCase(representation);
  }

  public boolean isEof() {
    return currentIndex >= this.tokens.size();
  }

  public void setUndoPoint() {
    this.undoStack.push(currentIndex);
  }

  public void rollbackToUndoPoint() {
    this.currentIndex = this.undoStack.pop();
  }

  public void clearUndoPoint() {
    this.undoStack.pop();
  }
}
