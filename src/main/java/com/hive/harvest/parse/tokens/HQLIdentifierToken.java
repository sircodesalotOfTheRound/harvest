package com.hive.harvest.parse.tokens;

import com.hive.harvest.exceptions.HQLException;
import com.hive.harvest.parse.lexer.HQLCharacterStream;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLIdentifierToken implements HQLToken {
  private final String identifier;

  private HQLIdentifierToken(HQLCharacterStream stream) {
    this.identifier = parseIdentifier(stream);
  }

  private String parseIdentifier(HQLCharacterStream stream) {
    if (!stream.currentIsAlpha()) {
      throw new HQLException("First character of identifier must be alpha.");
    }

    StringBuilder builder = new StringBuilder();
    while (!stream.isEof()
      && (stream.currentIsAlphaNumeric() || stream.currentIs('_')))
    {
      builder.append(stream.readCurrentAndAdvance());
    }

    return builder.toString();
  }

  public String identifier() { return this.identifier; }

  @Override
  public String toString() {
    return identifier;
  }

  public static HQLIdentifierToken read(HQLCharacterStream stream) {
    return new HQLIdentifierToken(stream);
  }
}
