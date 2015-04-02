package com.hive.harvest.tools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class OneToManyMap<T, UMany> {
  private final Map<T, List<UMany>> map = new HashMap<T, List<UMany>>();

  public void add(T key, UMany value) {
    List<UMany> list = this.getListForKey(key);
    list.add(value);
  }

  public void addMultiple(T key, Iterable<UMany> items) {
    List<UMany> list = this.getListForKey(key);
    for (UMany item : items) {
      list.add(item);
    }
  }

  private List<UMany> getListForKey(T key) {
    if (map.containsKey(key)) {
      return map.get(key);
    } else {
      List<UMany> items = new ArrayList<UMany>();
      map.put(key, items);
      return items;
    }
  }

  public Iterable<T> keys() {
    return this.map.keySet();
  }

  public boolean containsKey(T key) {
    return map.containsKey(key);
  }

  public Iterable<UMany> get(T key) {
    return map.get(key);
  }
}
