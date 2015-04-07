package com.hive.harvest.parse.tokens;

import com.hive.harvest.parse.lexer.HQLCharacterStream;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLWhitespaceToken extends HQLToken {
  private char whitespaceCharacter;
  private final int length;

  private HQLWhitespaceToken(HQLCharacterStream stream) {
    super(stream.position());
    this.whitespaceCharacter = stream.current();
    this.length = parseWhitespaceLength(stream);
  }

  private int parseWhitespaceLength(HQLCharacterStream stream) {
    char whitespaceCharacter = stream.current();
    int length = 0;

    while (!stream.isEof() && stream.currentIs(whitespaceCharacter)) {
      stream.readCurrentAndAdvance();
      length += 1;
    }

    return length;
  }

  public static HQLWhitespaceToken read(HQLCharacterStream stream) {
    return new HQLWhitespaceToken(stream);
  }

  public char whitespaceCharacter() { return whitespaceCharacter; }
  public int length() { return length; }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    for (int index = 0; index < length; index++) {
      builder.append(whitespaceCharacter);
    }
    return builder.toString();
  }
}
