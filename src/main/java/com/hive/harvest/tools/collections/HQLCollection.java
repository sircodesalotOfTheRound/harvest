package com.hive.harvest.tools.collections;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by sircodesalot on 15/4/3.
 */
public abstract class HQLCollection<T> implements Iterable<T> {

  public abstract Iterable<T> items();

  public boolean any() {
    return this.items().iterator().hasNext();
  }

  public boolean any(Predicate<T> test) {
    for (T item : this.items()) {
      if (test.test(item)) {
        return true;
      }
    }

    return false;
  }

  public boolean all(Predicate<T> test) {
    for (T item : this.items()) {
      if (!test.test(item)) {
        return false;
      }
    }

    return true;
  }

  public <U> HQLCollection<U> ofType(Class<U> type) {
    HQLAppendableCollection<U> itemsOfType = new HQLAppendableCollection<U>();
    for (T item : this.items()) {
      if (type.isAssignableFrom(item.getClass())) {
        itemsOfType.add((U) item);
      }
    }

    return itemsOfType;
  }

  public <U> HQLCollection<U> map(Function<T, U> projection) {
    HQLAppendableCollection<U> items = new HQLAppendableCollection<U>();

    for (T item : this.items()) {
      items.add(projection.apply(item));
    }

    return items;
  }

  public HQLCollection<T> where(Predicate<T> test) {
    HQLAppendableCollection<T> items = new HQLAppendableCollection<T>();

    for (T item : this.items()) {
      if (test.test(item)) {
        items.add(item);
      }
    }

    return items;
  }

  public T first() {
    Iterator<T> iterator = this.items().iterator();
    return iterator.next();
  }

  public T second() {
    Iterator<T> iterator = this.items().iterator();
    iterator.next();
    return this.iterator().next();
  }

  public int size() {
    if (this.items() instanceof List) {
      return ((List)this.items()).size();

    } else {
      int size = 0;
      Iterator<T> iterator = this.items().iterator();
      while(iterator.hasNext()) {
        size += 1;
      }
      return size;

    }
  }

  @Override
  public Iterator<T> iterator() {
    return this.items().iterator();
  }
}
