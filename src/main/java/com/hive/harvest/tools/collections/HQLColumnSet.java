package com.hive.harvest.tools.collections;

import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.HQLWildcardExpression;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLColumnSet implements Iterable<HQLExpression> {
  private final List<HQLExpression> columns;

  public HQLColumnSet(Iterable<HQLExpression> columns) {
    this.columns = freezeColumns(columns);
  }

  private List<HQLExpression> freezeColumns(Iterable<HQLExpression> columns) {
    List<HQLExpression> list = new ArrayList<HQLExpression>();
    for (HQLExpression column : columns) {
      list.add(column);
    }

    return list;
  }

  public int count() {
    return columns.size();
  }

  public boolean containsWildcardColumn() {
    for (HQLExpression column : columns) {
      if (column instanceof HQLWildcardExpression) {
        return true;
      }
    }

    return false;
  }

  public Iterator<HQLExpression> iterator() {
    return columns.iterator();
  }
}
