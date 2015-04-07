package com.hive.harvest.tools.collections;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sircodesalot on 15/4/7.
 */
public class HQLFixedCollection<T> extends HQLCollection<T> {
  private final List<T> items;

  public HQLFixedCollection(Iterable<T> items) {
    this.items = this.collectItems(items);
  }

  private List<T> collectItems(Iterable<T> items) {
    List<T> list = new ArrayList<T>();
    for (T item : items) {
      list.add(item);
    }

    return list;
  }

  @Override
  public Iterable<T> items() {
    return items;
  }
}
