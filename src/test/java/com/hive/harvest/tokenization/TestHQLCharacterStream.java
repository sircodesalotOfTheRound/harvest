package com.hive.harvest.tokenization;

import com.hive.harvest.exceptions.HQLException;
import com.hive.harvest.parse.lexer.HQLCharacterStream;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class TestHQLCharacterStream {
  private final String ALPHA = "ALPHA";
  private final String NUMERIC = "NUMERIC";
  private final String WHITESPACE = "WHITESPACE";
  private final String PUNCTUATION = "PUNCTUATION";

  @Test
  public void testCharacterStreamCounts() {
    Map<String, Integer> counts = new HashMap<String, Integer>();
    HQLCharacterStream tokenizer = new HQLCharacterStream("select 1, second, third.fourth from string where name = 'stuff'");

    // Count the number of times each letter appears.
    while (!tokenizer.isEof()) {
      String currentType;
      if (tokenizer.currentIsAlpha()) {
        currentType = ALPHA;
      } else if (tokenizer.currentIsNumeric()) {
        currentType = NUMERIC;
      } else if (tokenizer.currentIsPunctuation()) {
        currentType = PUNCTUATION;
      } else if (tokenizer.currentIsWhitespace()) {
        currentType = WHITESPACE;
      } else {
        throw new HQLException("Invalid letter type for this test");
      }

      int countOfThisTokenType;
      if (counts.containsKey(currentType)) {
        countOfThisTokenType = counts.get(currentType);
      } else {
        countOfThisTokenType = 0;
      }

      counts.put(currentType, countOfThisTokenType + 1);
      tokenizer.advance();
    }

    assert (counts.get(NUMERIC) == 1);
    assert (counts.get(WHITESPACE) == 9);
    assert (counts.get(ALPHA) == 47);
    assert (counts.get(PUNCTUATION) == 6);
  }

  @Test
  public void testStreamRollback() {
    HQLCharacterStream reader = new HQLCharacterStream("select");

    assert(reader.readCurrentAndAdvance() == 's');
    assert(reader.readCurrentAndAdvance() == 'e');
    assert(reader.readCurrentAndAdvance() == 'l');

    reader.setUndoPoint();
    assert(reader.readCurrentAndAdvance() == 'e');
    assert(reader.readCurrentAndAdvance() == 'c');
    assert(reader.readCurrentAndAdvance() == 't');

    reader.rollbackToUndoPoint();
    assert(reader.readCurrentAndAdvance() == 'e');
    assert(reader.readCurrentAndAdvance() == 'c');
    assert(reader.readCurrentAndAdvance() == 't');
  }

  @Test(expected=HQLException.class)
  public void advancePastEndOfLine() {
    HQLCharacterStream stream = new HQLCharacterStream("select");
    for (int index = 0; index <= "select".length(); index++) {
      stream.advance();
    }
  }

  @Test
  public void testSingleLineLexPosition() {
    HQLCharacterStream stream = new HQLCharacterStream("one two three");
    for (int index = 0; index < 10; index++) {
      stream.advance();
    }

    assert (stream.position().line() == 1);
    assert (stream.position().column() == 11);
    assert (stream.position().offset() == 10);
  }


  @Test
  public void testMultiLineLexPositions() {
    String command =
      "select \n" +
        "first \n" +
        "from \n" +
        "somewhere";

    HQLCharacterStream stream = new HQLCharacterStream(command);

    assert (stream.position().line() == 1);
    assert (stream.position().column() == 1);
    assert (stream.position().offset() == 0);

    advancePast(stream, "select \n");
    assert (stream.position().line() == 2);
    assert (stream.position().column() == 1);
    assert (stream.position().offset() == 8);

    // To make sure that undo works properly.
    stream.setUndoPoint();

    advancePast(stream, "first \n");
    assert (stream.position().line() == 3);
    assert (stream.position().column() == 1);
    assert (stream.position().offset() == 15);

    advancePast(stream, "from \n");
    assert (stream.position().line() == 4);
    assert (stream.position().column() == 1);
    assert (stream.position().offset() == 21);

    stream.rollbackToUndoPoint();
    assert (stream.position().line() == 2);
    assert (stream.position().column() == 1);
    assert (stream.position().offset() == 8);
  }

  public void advancePast(HQLCharacterStream stream, String text) {
    for (int index = 0; index < text.length(); index++) {
      stream.advance();
    }
  }
}
