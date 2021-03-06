package com.hive.harvest.tools.collections;

import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.tokens.HQLToken;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by sircodesalot on 15/4/3.
 */
public abstract class HQLCollection<T> implements Iterable<T> {
  public static final HQLCollection<HQLExpression> EMPTY = new HQLCollection<HQLExpression>() {
    private final List<HQLExpression> emptyList = new ArrayList<HQLExpression>();
    @Override
    public Iterable<HQLExpression> items() {
      return emptyList;
    }
  };

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

  public <U> HQLCollection<U> castTo(Class<U> toType) {
    HQLAppendableCollection<U> castedItems = new HQLAppendableCollection<U>();
    for (T item : this.items()) {
      castedItems.add((U)item);
    }

    return castedItems;
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

  public <U> U firstAs(Class<U> type) {
    return (U)this.first();
  }

  public <U> U secondAs(Class<U> type) {
    return (U)this.first();
  }

  public T first() {
    if (this.items() instanceof List) {
      return (T)((List<T>)this.items()).get(0);
    } else {
      Iterator<T> iterator = this.items().iterator();
      return iterator.next();
    }
  }

  public T second() {
    if (this.items() instanceof List) {
      return (T)((List<T>)this.items()).get(1);
    } else {
      Iterator<T> iterator = this.items().iterator();
      iterator.next();
      return iterator.next();
    }
  }

  public T get(int at) {
    if (this.items() instanceof List) {
      return (T)((List<T>)this.items()).get(at);
    } else {
      Iterator<T> iterator = this.items().iterator();
      for (int index = 0; index < (index - 1); index++) {
        iterator.next();
      }

      return iterator.next();
    }
  }

  public int count() {
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
