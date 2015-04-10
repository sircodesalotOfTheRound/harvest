package com.hive.harvest.eventing.tooling;

import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.tools.ClassHierarchyFlattener;
import com.hive.harvest.tools.OneToManyMap;

/**
 * Created by sircodesalot on 15/4/10.
 */

public class EventLauncher<TLaunchEventType extends LaunchableEvent> {
  private final OneToManyMap<Class, TLaunchEventType> rules;

  public EventLauncher() {
    this.rules = new OneToManyMap<Class, TLaunchEventType>();
  }

  public EventLauncher<TLaunchEventType> add(TLaunchEventType rule) {
    // Ensure that we launch this rule for polymorphic derivations of the token type.
    // For example, two tokens may share the same base class. So a backtrack rule may
    // choose to listen for that.
    ClassHierarchyFlattener hierarchy = new ClassHierarchyFlattener(rule.listenForType());

    // Wire the rule up to all of it's interfaces.
    for (Class iface : hierarchy.flattenedHierarchy()) {
      rules.add(iface, rule);
    }

    return this;
  }

  public boolean canLaunch(HQLExpression expression) {
    if (!this.rules.containsKey(expression.getClass())) {
      return false;
    } else {
      for (TLaunchEventType event : this.rules.get(expression.getClass())) {
        if (event.canLaunch(expression)) {
          return true;
        }
      }
    }

    return false;
  }

  public void launch(HQLExpression expression) {
    if (this.rules.containsKey(expression.getClass())) {
      for (TLaunchEventType event : this.rules.get(expression.getClass())) {
        if (event.canLaunch(expression)) {
          event.launch(expression);
        }
      }
    }
  }
}
