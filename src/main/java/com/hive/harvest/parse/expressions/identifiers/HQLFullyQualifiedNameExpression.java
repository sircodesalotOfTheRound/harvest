package com.hive.harvest.parse.expressions.identifiers;

import com.hive.harvest.graph.HQLNoReturnVisitor;
import com.hive.harvest.parse.expressions.HQLExpression;
import com.hive.harvest.parse.expressions.backtracking.HQLIdentifierExpressionBacktrackRule;
import com.hive.harvest.parse.expressions.backtracking.HQLWildcardExpressionBacktrackRule;
import com.hive.harvest.parse.expressions.backtracking.interfaces.HQLBacktrackingRuleSet;
import com.hive.harvest.parse.expressions.categories.HQLMemberExpression;
import com.hive.harvest.parse.expressions.delimiters.HQLDotExpression;
import com.hive.harvest.parse.lexer.HQLLexer;
import com.hive.harvest.parse.tokens.HQLToken;
import com.hive.harvest.tools.collections.HQLAppendableCollection;
import com.hive.harvest.tools.collections.HQLCollection;

/**
 * Created by sircodesalot on 15/4/9.
 */
public class HQLFullyQualifiedNameExpression extends HQLExpression {
  private static final HQLBacktrackingRuleSet<HQLMemberExpression> memberTypeRules = new HQLBacktrackingRuleSet<HQLMemberExpression>()
    .add(new HQLIdentifierExpressionBacktrackRule())
    .add(new HQLWildcardExpressionBacktrackRule());

  private final HQLCollection<HQLMemberExpression> members;

  public HQLFullyQualifiedNameExpression(HQLExpression parent, HQLLexer lexer) {
    super(parent, lexer);

    this.members = this.readMembers(lexer);
  }

  private HQLCollection<HQLMemberExpression> readMembers(HQLLexer lexer) {
    HQLAppendableCollection<HQLMemberExpression> identifiers = new HQLAppendableCollection<HQLMemberExpression>();
    while (!lexer.isEof()) {
      if (memberTypeRules.canParse(this, lexer)) {
        identifiers.add(memberTypeRules.read(this, lexer));
      } else {
        break;
      }

      if (HQLDotExpression.canRead(this, lexer)) {
        HQLDotExpression.read(this, lexer);
      } else {
        break;
      }
    }

    return identifiers;
  }

  public HQLCollection<HQLMemberExpression> members() {
    return this.members;
  }


  @Override
  public void accept(HQLNoReturnVisitor visitor) {

  }

  @Override
  public HQLCollection<HQLToken> children() {
    return null;
  }

  public static HQLFullyQualifiedNameExpression read(HQLExpression parent, HQLLexer lexer) {
    return new HQLFullyQualifiedNameExpression(parent, lexer);
  }

}
