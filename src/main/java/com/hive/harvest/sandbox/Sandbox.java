package com.hive.harvest.sandbox;

import com.hive.harvest.command.HQLCommand;

/**
 * Created by sircodesalot on 15/4/9.
 */
public class Sandbox {
  public static void main(String[] args) {
    HQLCommand command = new HQLCommand("create table simple (" +
      "first INT COMMENT 'this is the comment', " +
      "second STRING, " +
      "third MAP<STRING, INT>, " +
      "fourth STRUCT<first:STRING, second:INT, third:ARRAY<TIMESTAMP>>" +
      ")"
    );

    System.out.println(command.stringTree());
  }
}
