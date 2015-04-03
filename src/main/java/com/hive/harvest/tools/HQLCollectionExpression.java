package com.hive.harvest.tools;

import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.lexer.HQLLexer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Created by sircodesalot on 15/4/3.
 */
public abstract class HQLCollectionExpression<T> extends HQLExpression implements Iterable<T> {
  // This class stores the contents of an immediate transformation.
  public static class HQLDefaultCollection<T> extends HQLCollectionExpression<T> {
    private Iterable<T> contents;

    public HQLDefaultCollection(Iterable<T> contents) {
      super (null, null);
      this.contents = contents;
    }

    @Override
    protected Iterable<T> contents() {
      return this.contents;
    }
  }

  public HQLCollectionExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);
  }

  protected abstract Iterable<T> contents();

  public boolean any() {
    return this.contents().iterator().hasNext();
  }

  public boolean any(Predicate<T> test) {
    for (T item : this.contents()) {
      if (test.test(item)) {
        return true;
      }
    }

    return false;
  }

  public boolean all(Predicate<T> test) {
    for (T item : this.contents()) {
      if (!test.test(item)) {
        return false;
      }
    }

    return true;
  }

  public <U> HQLCollectionExpression<U> ofType(Class<U> type) {
    List<U> itemsOfType = new ArrayList<>();
    for (T item : this.contents()) {
      if (type.isAssignableFrom(item.getClass())) {
        itemsOfType.add((U) item);
      }
    }

    return new HQLDefaultCollection<U>(itemsOfType);
  }

  public <U> HQLCollectionExpression<U> map(Function<T, U> projection) {
    List<U> items = new ArrayList<U>();

    for (T item : this.contents()) {
      items.add(projection.apply(item));
    }

    return new HQLDefaultCollection<>(items);
  }

  public HQLCollectionExpression<T> where(Predicate<T> test) {
    List<T> items = new ArrayList<T>();

    for (T item : this.contents()) {
      if (test.test(item)) {
        items.add(item);
      }
    }

    return new HQLDefaultCollection<>(items);
  }

  public T first() {
    Iterator<T> iterator = this.contents().iterator();
    return iterator.next();
  }

  public T second() {
    Iterator<T> iterator = this.contents().iterator();
    iterator.next();
    return this.iterator().next();
  }

  public int size() {
    if (this.contents() instanceof List) {
      return ((List)contents()).size();
    } else {
      Iterator<T> iterator = this.contents().iterator();
      int count = 0;
      while (iterator.hasNext()) {
        count += 1;
        iterator.next();
      }

      return count;
    }
  }

  @Override
  public Iterator<T> iterator() {
    return this.contents().iterator();
  }
}
