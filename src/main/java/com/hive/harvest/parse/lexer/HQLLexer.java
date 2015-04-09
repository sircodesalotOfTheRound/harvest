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
  private final HQLTokenList tokens;
  private boolean skipWhitespaces;
  private int currentIndex;
  private Stack<Integer> undoStack;
  private Stack<Boolean> whitespaceStateStack;

  public HQLLexer(String text, boolean skipWhitespacesByDefault) {
    this.text = text;
    this.skipWhitespaces = skipWhitespacesByDefault;
    this.tokens = new HQLTokenList(text);
    this.undoStack = new Stack<Integer>();
    this.whitespaceStateStack = new Stack<Boolean>();
  }

  public String text() {
    return this.text;
  }

  public void advance() {
    if (isEof()) {
      throw new HQLException("Attempt to advance past end of file");
    }

    currentIndex += 1;

    if (skipWhitespaces) {
      while (!isEof() && currentIs(HQLWhitespaceToken.class)) {
        currentIndex += 1;
      }
    }
  }

  public HQLToken current() {
    return this.tokens.get(currentIndex);
  }

  public int currentIndex() {
    return this.currentIndex;
  }

  public HQLToken atIndex(int index) {
    return tokens.get(index);
  }

  public boolean isIncludingWhitespaces() {
    return this.skipWhitespaces;
  }

  public void temporarilyIncludeWhitespaces() {
    this.whitespaceStateStack.push(this.skipWhitespaces);
    this.skipWhitespaces = false;
  }

  public void temporarilyExcludeWhitespaces() {
    this.whitespaceStateStack.push(this.skipWhitespaces);
    this.skipWhitespaces = true;
  }

  public void revertToPreviousWhitespaceInclusionState() {
    this.skipWhitespaces = this.whitespaceStateStack.pop();
  }

  public HQLToken readCurrentAndAdvance() {
    HQLToken current = this.current();
    this.advance();

    return current;
  }

  public <T extends HQLToken> T readCurrentAndAdvance(Class<T> type) {
    if (currentIs(type)) {
      return (T) readCurrentAndAdvance();
    } else {
      throw new HQLException("Expected %s, found %s", type, this.current().getClass());
    }
  }

  public <T extends HQLToken> T readCurrentAndAdvanceMatchCase(Class<T> type, String representation) {
    if (currentIsMatchCase(type, representation)) {
      return (T) readCurrentAndAdvance();
    } else {
      throw new HQLException("Expected (%s : %s), found (%s : %s)",
        representation, type,
        this.current().toString(), this.current().getClass());
    }
  }

  public <T extends HQLToken> T readCurrentAndAdvance(Class<T> type, String representation) {
    if (currentIs(type, representation)) {
      return (T) readCurrentAndAdvance();
    } else {
      throw new HQLException("Expected (%s : %s), found (%s : %s)",
        representation, type,
        this.current().toString(), this.current().getClass());
    }
  }

  public <T extends HQLToken> boolean currentIs(Class<T> type) {
    return !this.isEof() && type.isAssignableFrom(current().getClass());
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

  public HQLLexPosition position() {
    return this.tokens.get(currentIndex).position();
  }
}
