Harvest HQL Analyzer
------------------

The Harvest analyzer is a fast, extensible, yet easy to use tool used to analyze and optimize HQL statements.

```
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

output:
create table simple (first INT COMMENT 'this is the comment', second STRING, third MAP<STRING, INT>, fourth STRUCT<first:STRING, second:INT, third:ARRAY<TIMESTAMP>>)

(ROOT)               : HQLTreeRootExpression
  (CREATE TABLE)       : HQLCreateTableExpression
    +-simple               : HQLFullyQualifiedNameExpression
      +-simple               : HQLIdentifierExpression
    +-(COLUMN GROUP)       : HQLCreateColumnGroupExpression
      +-first:INTCOMMENT 'this is the comment' : HQLCreateTypedColumnExpression
        +-first                : HQLIdentifierExpression
        +-INT                  : HQLTypeConstraintExpression
          +-INT                  : HQLIdentifierExpression
        +-COMMENT 'this is the comment' : HQLColumnCommentExpression
          +-this is the comment  : HQLStringExpression
      +-second:STRING        : HQLCreateTypedColumnExpression
        +-second               : HQLIdentifierExpression
        +-STRING               : HQLTypeConstraintExpression
          +-STRING               : HQLIdentifierExpression
      +-third:MAP<STRING, INT> : HQLCreateTypedColumnExpression
        +-third                : HQLIdentifierExpression
        +-MAP<STRING, INT>     : HQLTypeConstraintExpression
          +-MAP<STRING, INT>     : HQLIdentifierExpression
            +-<STRING, INT>        : HQLGenericParameterListExpression
              +-STRING               : HQLGenericParameterExpression
                +-STRING               : HQLIdentifierExpression
              +-INT                  : HQLGenericParameterExpression
                +-INT                  : HQLIdentifierExpression
      +-fourth:STRUCT<first:STRING, second:INT, third:ARRAY<TIMESTAMP>> : HQLCreateTypedColumnExpression
        +-fourth               : HQLIdentifierExpression
        +-STRUCT<first:STRING, second:INT, third:ARRAY<TIMESTAMP>> : HQLTypeConstraintExpression
          +-STRUCT<first:STRING, second:INT, third:ARRAY<TIMESTAMP>> : HQLIdentifierExpression
            +-<first:STRING, second:INT, third:ARRAY<TIMESTAMP>> : HQLGenericParameterListExpression
              +-first:STRING         : HQLGenericParameterExpression
                +-first                : HQLIdentifierExpression
                +-STRING               : HQLTypeConstraintExpression
                  +-STRING               : HQLIdentifierExpression
              +-second:INT           : HQLGenericParameterExpression
                +-second               : HQLIdentifierExpression
                +-INT                  : HQLTypeConstraintExpression
                  +-INT                  : HQLIdentifierExpression
              +-third:ARRAY<TIMESTAMP> : HQLGenericParameterExpression
                +-third                : HQLIdentifierExpression
                +-ARRAY<TIMESTAMP>     : HQLTypeConstraintExpression
                  +-ARRAY<TIMESTAMP>     : HQLIdentifierExpression
                    +-<TIMESTAMP>          : HQLGenericParameterListExpression
                      +-TIMESTAMP            : HQLGenericParameterExpression
                        +-TIMESTAMP            : HQLIdentifierExpression

```

## Building 
To build harvest, simply use the following shell script:

```
git clone git@github.com:sircodesalotOfTheRound/harvest.git
cd harvest
mvn clean install harvest
```