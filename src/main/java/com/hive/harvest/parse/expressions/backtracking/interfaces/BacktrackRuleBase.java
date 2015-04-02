package com.hive.harvest.parse.expressions.backtracking.interfaces;

import com.hive.harvest.parse.tokens.HQLToken;

/**
 * Created by sircodesalot on 15/4/2.
 */
public abstract class BacktrackRuleBase<TListensFor extends HQLToken> implements BacktrackRule {
  private final Class<TListensFor> launchForTokensOfType;

  protected BacktrackRuleBase(Class <TListensFor> launchForTokensOfType) {
    this.launchForTokensOfType = launchForTokensOfType;
  }

  public Class launchForTokensOfType() {
    return this.launchForTokensOfType;
  }
}
