package com.hive.harvest.exceptions;

/**
 * Created by sircodesalot on 15/4/2.
 */
public class HQLException extends RuntimeException {
  public HQLException(String format, Object ... args) {
    super(String.format(format, args));
  }
}
