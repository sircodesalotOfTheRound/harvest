package com.hive.harvest.tools.collections;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sircodesalot on 15/4/7.
 */
public class HQLAppendableCollection<T>  extends HQLCollection<T> {
  private final List<T> items = new ArrayList<T>();

  public HQLAppendableCollection() {
  }

  public HQLAppendableCollection(Iterable<T> items) {
    this.add(items);
  }

  public HQLAppendableCollection(T... items) {
    for (T item : items) {
      this.add(item);
    }
  }

  public HQLAppendableCollection add(T item) {
    this.items.add(item);
    return this;
  }

  public HQLAppendableCollection add(Iterable<T> items) {
    for (T item : items) {
      this.add(item);
    }

    return this;
  }

  @Override
  public Iterable<T> items() {
    return items;
  }
}

