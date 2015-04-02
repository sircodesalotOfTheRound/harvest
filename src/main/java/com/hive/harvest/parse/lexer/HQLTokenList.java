package com.hive.harvest.parse.lexer;

import com.hive.harvest.parse.tokens.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLTokenList implements Iterable<HQLTokenBase> {
  private final List<HQLTokenBase> tokens;

  public HQLTokenList(String statement) {
    this.tokens = generateFromString(statement);
  }

  private List<HQLTokenBase> generateFromString(String text) {
    HQLCharacterStream stream = new HQLCharacterStream(text);
    List<HQLTokenBase> tokens = new ArrayList<HQLTokenBase>();

    while (!stream.isEof()) {
      HQLTokenBase token = readToken(stream);
      tokens.add(token);
    }

    return tokens;
  }

  private HQLTokenBase readToken(HQLCharacterStream stream) {
    if (stream.currentIsAlpha()) {
      return HQLIdentifierToken.read(stream);
    } else if (stream.currentIsPunctuation()) {
      return HQLPunctuationToken.read(stream);
    } else if (stream.currentIsWhitespace()) {
      return HQLWhitespaceToken.read(stream);
    } else if (stream.currentIsPunctuation()) {
      return HQLPunctuationToken.read(stream);
    } else if (stream.currentIsNumeric()) {
      return HQLNumericToken.read(stream);
    } else {
      // In practice, this should never be used.
      return HQLMysteryToken.read(stream);
    }
  }

  public Iterable<HQLTokenBase> tokens() {
    return this.tokens;
  }

  public Iterator<HQLTokenBase> iterator() {
    return tokens.iterator();
  }

  public int size() {
    return tokens.size();
  }
}
