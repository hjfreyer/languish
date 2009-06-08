package languish.interpreter;

import languish.lambda.Expression;
import languish.lambda.Wrapper;
import languish.prim.data.LBoolean;
import languish.prim.data.LBooleans;
import languish.prim.data.LComposites;
import languish.prim.data.LExpressionWrappers;
import languish.prim.data.LIntegers;

public enum Builtins {
  // Starting interpreter
  // PRIM_INTERP(BuiltinParser.getInterpreter()),

  // Expression reducer
  // REDUCE_EXP(ExpressionOps.UNWRAP),

  // Arithmetic
  ADD(LIntegers.ADD),

  // Boolean ops
  TRUE(Wrapper.of(LBoolean.TRUE)),
  FALSE(Wrapper.of(LBoolean.FALSE)),
  BRANCH(LBooleans.BRANCH),

  // Expression constructors
  MK_ABS(LExpressionWrappers.MK_ABS),
  MK_APP(LExpressionWrappers.MK_APP),
  MK_WRAPPER(LExpressionWrappers.MK_WRAPPER),
  MK_REF(LExpressionWrappers.MK_REF),
  MK_NAT(LExpressionWrappers.MK_NAT),

  // LComposite
  GET_ELEMENT(LComposites.GET_ELEMENT),
  WRAP(LComposites.WRAP),

  ;

  private Expression expression;

  private Builtins(Expression op) {
    this.expression = op;
  }

  public Expression getExpression() {
    return expression;
  }
}
