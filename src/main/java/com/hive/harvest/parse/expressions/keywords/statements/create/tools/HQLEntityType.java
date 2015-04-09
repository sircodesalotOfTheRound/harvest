package com.hive.harvest.parse.expressions.keywords.statements.create.tools;

import com.hive.harvest.exceptions.HQLException;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sircodesalot on 15/4/9.
 */
public enum HQLEntityType {
  TABLE("TABLE");

  private static final Map<String, HQLEntityType> entityTypes = HQLEntityType.generateEntityTypes();
  private final String representation;

  HQLEntityType(String representation) {
    this.representation = representation;
  }

  @Override
  public String toString() {
    return this.representation;
  }

  private static Map<String, HQLEntityType> generateEntityTypes() {
    Map<String, HQLEntityType> set = new HashMap<>();
    set.put(TABLE.toString(), TABLE);

    return set;
  }

  public static boolean isValidEntityType(HQLIdentifierToken token) {
    return entityTypes.containsKey(token.identifier().toUpperCase());
  }

  public static HQLEntityType fromIdentifierToken(HQLIdentifierToken token) {
    if (entityTypes.containsKey(token.identifier().toUpperCase())) {
      return entityTypes.get(token.identifier().toUpperCase());
    } else {
      throw new HQLException("Invalid token type");
    }
  }
}