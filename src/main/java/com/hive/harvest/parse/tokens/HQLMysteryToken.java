package com.hive.harvest.parse.tokens;

import com.hive.harvest.parse.lexer.HQLCharacterStream;

/**
 * In theory, this class should never be used.
 */
public class HQLMysteryToken extends HQLToken {
  private final char character;

  private HQLMysteryToken(HQLCharacterStream stream) {
    super(stream.position());
    this.character = stream.readCurrentAndAdvance();
  }

  public static HQLMysteryToken read(HQLCharacterStream stream) {
    return new HQLMysteryToken(stream);
  }

  @Override
  public String toString() {
    return ((Character)character).toString();
  }

  public char character() { return this.character; }
}