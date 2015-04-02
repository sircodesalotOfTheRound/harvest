package com.hive.harvest.parse.lexer;

import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLCharacterStream {
  private static final Set<Character> punctuation = generatePunctuationSet();

  private final String text;
  private int currentIndex;
  private Stack<Integer> undoStack;

  public HQLCharacterStream(String text) {
    this.text = text;
    this.currentIndex = 0;
    this.undoStack = new Stack<Integer>();
  }

  public char current() {
    return text.charAt(currentIndex);
  }

  public boolean currentIs(char character) {
    return current() == character;
  }

  public char readCurrentAndAdvance() {
    char value = text.charAt(currentIndex);
    currentIndex += 1;
    return value;
  }

  public void advance() {
    this.currentIndex += 1;
  }

  public boolean currentIsNumeric() {
    return Character.isDigit(current());
  }

  public boolean currentIsWhitespace() {
    return Character.isWhitespace(current());
  }

  public boolean currentIsAlpha() {
    return Character.isAlphabetic(current());
  }

  public boolean currentIsAlphaNumeric() {
    return Character.isLetterOrDigit(current());
  }

  public boolean currentIsPunctuation() {
    return punctuation.contains(current());
  }

  public boolean isEof() {
    return currentIndex >= text.length();
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

  private static Set<Character> generatePunctuationSet() {
    String punctuation = "~`!@#$%^&*()-_+=[{]}\\|;:,<.>/?'\"";
    Set<Character> punctuationSet = new HashSet<Character>();

    for (char letter : punctuation.toCharArray()) {
      punctuationSet.add(letter);
    }

    return punctuationSet;
  }
}
