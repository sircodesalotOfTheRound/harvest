package com.hive.harvest.tools;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class ClassHierarchyFlattener {
  private final Class type;
  private final Iterable<Class> flattenedHierarchy;

  public ClassHierarchyFlattener(Class type) {
    this.type = type;
    this.flattenedHierarchy = collectInterfacesForType(type);
  }

  private Iterable<Class> collectInterfacesForType(Class type) {
    return collectInterfacesForType(type, new ArrayList<Class>());
  }

  private Iterable<Class> collectInterfacesForType(Class type, List<Class> interfaces) {
    if (type == null) {
      return interfaces;
    }

    collectInterfacesForType(type.getSuperclass(), interfaces);
    for (Class iface : type.getInterfaces()) {
      collectInterfacesForType(iface, interfaces);
    }

    interfaces.add(type);
    return interfaces;
  }

  public Class type() { return this.type; }
  public Iterable<Class> flattenedHierarchy() { return this.flattenedHierarchy; }
}
