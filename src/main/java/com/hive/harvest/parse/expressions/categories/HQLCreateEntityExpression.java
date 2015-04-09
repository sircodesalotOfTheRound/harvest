package com.hive.harvest.parse.expressions.categories;

import com.hive.harvest.parse.expressions.keywords.statements.create.tools.HQLEntityType;

/**
 * Created by sircodesalot on 15/4/9.
 */
public interface HQLCreateEntityExpression extends HQLExpressionCategory {
  HQLEntityType entityType();
}
