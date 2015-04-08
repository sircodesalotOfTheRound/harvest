package com.hive.harvest.parse.expressions.keywords.statements;

import com.hive.harvest.exceptions.HQLException;
import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.keywords.HQLKeywordExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLIdentifierToken;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sircodesalot on 15/4/7.
 */
public class HQLCreateEntityStatement extends HQLExpression implements HQLStatementExpression {
  public enum EntityType {
    TABLE("TABLE");

    private static final Map<String, EntityType> entityTypes = EntityType.generateEntityTypes();
    private final String representation;
    EntityType(String representation) {
      this.representation = representation;
    }

    @Override
    public String toString() {
      return this.representation;
    }

    private static Map<String, EntityType> generateEntityTypes() {
      Map<String, EntityType> set = new HashMap<>();
      set.put(TABLE.toString(), TABLE);

      return set;
    }

    public static boolean isValidEntityType(HQLIdentifierToken token) {
      return entityTypes.containsKey(token.identifier().toUpperCase());
    }

    public static EntityType fromIdentifierToken(HQLIdentifierToken token) {
      if (entityTypes.containsKey(token.identifier().toUpperCase())) {
        return entityTypes.get(token.identifier().toUpperCase());
      } else {
        throw new HQLException("Invalid token type");
      }
    }
  }


  private final EntityType entityType;
  private final HQLIdentifierToken identifier;

  public HQLCreateEntityStatement(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.entityType = this.readEntityType(lexer);
    this.identifier = this.readIdentifier(lexer);
  }

  private EntityType readEntityType(HQLLexer lexer) {
    lexer.readCurrentAndAdvance(HQLIdentifierToken.class, HQLKeywordExpression.CREATE);

    if (EntityType.isValidEntityType((HQLIdentifierToken) lexer.current())) {
      return EntityType.fromIdentifierToken(lexer.readCurrentAndAdvance(HQLIdentifierToken.class));
    } else {
      return null;
    }
  }

  private HQLIdentifierToken readIdentifier(HQLLexer lexer) {
    return lexer.readCurrentAndAdvance(HQLIdentifierToken.class);
  }

  @Override
  public void accept(HQLNoReturnVisitor visitor) {

  }

  public EntityType entityType() {
    return this.entityType;
  }

  public String identifier() {
    return this.identifier.identifier();
  }

  public static HQLCreateEntityStatement read(HQLExpression parent, HQLLexer lexer) {
    return new HQLCreateEntityStatement(parent, lexer);
  }
}
