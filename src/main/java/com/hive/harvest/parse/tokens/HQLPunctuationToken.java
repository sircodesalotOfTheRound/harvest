package com.hive.harvest.parse.tokens;

import com.hive.harvest.parse.lexer.HQLCharacterStream;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLPunctuationToken extends HQLToken {
  private final String token;

  private HQLPunctuationToken(HQLCharacterStream stream) {
    super(stream.position());
    this.token = parseFromString(stream);
  }

  private String parseFromString(HQLCharacterStream stream) {
    StringBuilder builder = new StringBuilder();
    builder.append(stream.readCurrentAndAdvance());
    return builder.toString();
  }


  public static HQLPunctuationToken read(HQLCharacterStream stream) {
    return new HQLPunctuationToken(stream);
  }

  @Override
  public String toString() {
    return token;
  }

  public String token() { return this.token; }
}
