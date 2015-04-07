package com.hive.harvest.parse.tokens;

import com.hive.harvest.parse.lexer.HQLCharacterStream;

import java.math.BigInteger;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLNumericToken extends HQLToken {
  private BigInteger value;

  private HQLNumericToken(HQLCharacterStream stream) {
    super(stream.position());
    this.value = parseValueFromString(stream);
  }

  private BigInteger parseValueFromString(HQLCharacterStream stream) {
    StringBuilder builder = new StringBuilder();
    while (!stream.isEof() && (stream.currentIsNumeric() || stream.currentIs('.'))) {
      builder.append(stream.readCurrentAndAdvance());
    }

    return new BigInteger(builder.toString());
  }

  public BigInteger value() { return this.value; }

  @Override
  public String toString() {
    return value.toString();
  }

  public static HQLNumericToken read(HQLCharacterStream stream) {
    return new HQLNumericToken(stream);
  }
}
