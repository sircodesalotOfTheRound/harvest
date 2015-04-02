package com.hive.harvest.parse.lexer;

import com.hive.harvest.parse.tokens.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLTokenList implements Iterable<HQLToken> {
  private final List<HQLToken> tokens;

  public HQLTokenList(String statement) {
    this.tokens = generateFromString(statement);
  }

  private List<HQLToken> generateFromString(String text) {
    HQLCharacterStream stream = new HQLCharacterStream(text);
    List<HQLToken> tokens = new ArrayList<HQLToken>();

    while (!stream.isEof()) {
      HQLToken token = readToken(stream);
      tokens.add(token);
    }

    return tokens;
  }

  private HQLToken readToken(HQLCharacterStream stream) {
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

  public Iterable<HQLToken> tokens() {
    return this.tokens;
  }

  public Iterator<HQLToken> iterator() {
    return tokens.iterator();
  }

  public int size() {
    return tokens.size();
  }

  public HQLToken get(int index) {
    return tokens.get(index);
  }
}
