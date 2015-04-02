package com.hive.harvest.parse.expressions.backtracking.interfaces;

import com.hive.harvest.exceptions.HQLException;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLToken;
import com.hive.harvest.tools.OneToManyMap;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class BacktrackRuleSet {
  private final OneToManyMap<Class, BacktrackRule> rules;

  public BacktrackRuleSet() {
    this.rules = new OneToManyMap<Class, BacktrackRule>();
  }

  public BacktrackRuleSet add(BacktrackRule rule) {
    rules.add(rule.launchForTokensOfType(), rule);
    return this;
  }

  public HQLExpression read(HQLExpression parent, HQLLexer lexer) {
    if (lexer.isEof()) {
      throw new HQLException("Attempt to read past end of stream");
    }

    HQLToken current = lexer.current();
    Class type = current.getClass();
    if (rules.containsKey(type)) {
      for (BacktrackRule rule : this.rules.get(type)) {
        if (rule.isMatch(parent, lexer)) {
          return rule.read(parent, lexer);
        }
      }
    }

    return null;
  }
}
