package com.hive.harvest.parse.expressions.backtracking.interfaces;

import com.hive.harvest.exceptions.HQLException;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.tools.ClassHierarchyFlattener;
import com.hive.harvest.tools.OneToManyMap;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class BacktrackRuleSet<TExpressionType> {
  private final OneToManyMap<Class, HQLBacktrackRule> rules;

  public BacktrackRuleSet() {
    this.rules = new OneToManyMap<Class, HQLBacktrackRule>();
  }

  public BacktrackRuleSet<TExpressionType> add(HQLBacktrackRule rule) {
    // Ensure that we launch this rule for polymorphic derivations of the token type.
    // For example, two tokens may share the same base class. So a backtrack rule may
    // choose to listen for that.
    ClassHierarchyFlattener hierarchy = new ClassHierarchyFlattener(rule.launchForTokensOfType());

    // Wire the rule up to all of it's interfaces.
    for (Class iface : hierarchy.flattenedHierarchy()) {
      rules.add(iface, rule);
    }

    return this;
  }

  public TExpressionType read(HQLExpression parent, HQLLexer lexer) {
    if (lexer.isEof()) {
      throw new HQLException("Attempt to read past end of stream");
    }

    // Try to find a backtrack rule that matches the current token.
    // If that isn't found, launch all of the backtrack rules (all rules
    // listening to Object.class) -- which is all rules.
    if (canParse(parent, lexer)) {
      Class typeOfCurrentToken = lexer.current().getClass();
      return this.findMatch(typeOfCurrentToken, parent, lexer);
    } else {
      return this.findMatch(Object.class, parent, lexer);
    }
  }

  public boolean canParse(HQLExpression parent, HQLLexer lexer) {
    if (lexer.isEof()) {
      return false;
    }

    Class typeOfCurrentToken = lexer.current().getClass();
    if (!rules.containsKey(typeOfCurrentToken)) {
      return false;
    } else {
      for (HQLBacktrackRule rule : this.rules.get(typeOfCurrentToken)) {
        if (rule.isMatch(parent, lexer)) {
          return true;
        }
      }
    }

    // All else fails, return false.
    return false;
  }

  private TExpressionType findMatch (Class type, HQLExpression parent, HQLLexer lexer) {
    if (!canParse(parent, lexer)) {
      return null;
    }

    for (HQLBacktrackRule rule : this.rules.get(type)) {
      if (rule.isMatch(parent, lexer)) {
        return (TExpressionType)rule.read(parent, lexer);
      }
    }

    return null;
  }
}
