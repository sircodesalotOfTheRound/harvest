package com.hive.harvest.tools.collections;

import com.hive.harvest.parse.expressions.HQLExpression;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLTableSet implements Iterable<HQLExpression> {
  private final List<HQLExpression> tables;

  public HQLTableSet(Iterable<HQLExpression> tables) {
    this.tables = freezeColumns(tables);
  }

  private List<HQLExpression> freezeColumns(Iterable<HQLExpression> tables) {
    List<HQLExpression> list = new ArrayList<HQLExpression>();
    for (HQLExpression table : tables) {
      list.add(table);
    }

    return list;
  }

  public int count() {
    return tables.size();
  }

  public Iterable<HQLExpression> tables() { return tables; }

  public Iterator<HQLExpression> iterator() {
    return tables.iterator();
  }
}
