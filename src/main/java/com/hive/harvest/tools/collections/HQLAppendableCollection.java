package com.hive.harvest.tools.collections;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sircodesalot on 15/4/7.
 */
public class HQLAppendableCollection<T>  extends HQLCollection<T> {
  private final List<T> items = new ArrayList<T>();

  public void add(T item) {
    this.items.add(item);
  }

  @Override
  public Iterable<T> items() {
    return items;
  }
}

