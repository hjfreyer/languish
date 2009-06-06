package languish.interpreter;

import languish.lambda.Expression;
import languish.lambda.Wrapper;
import languish.prim.data.LBoolean;
import languish.prim.functions.BooleanOps;
import languish.prim.functions.IntegerOps;

public enum Builtins {
  // Starting interpreter
  // PRIM_INTERP(BuiltinParser.getInterpreter()),

  // Expression reducer
  // REDUCE_EXP(ExpressionOps.UNWRAP),

  // Arithmetic
  ADD(IntegerOps.ADD),

  // Boolean ops
  TRUE(Wrapper.of(LBoolean.TRUE)),
  FALSE(Wrapper.of(LBoolean.FALSE)),
  BRANCH(BooleanOps.BRANCH),
  //
  // // Expression constructors
  // MK_ABS(ExpressionOps.MK_ABS),
  // MK_APP(ExpressionOps.MK_APP),
  // MK_LITERAL(ExpressionOps.MK_LITERAL),
  // MK_REF(ExpressionOps.MK_REF),
  // MK_UNWRAP(ExpressionOps.MK_UNWRAP),
  //
  // // LComposite
  // LCOMPOSITE_GET(CompositeOps.GET);

  ;

  private Expression expression;

  private Builtins(Expression op) {
    this.expression = op;
  }

  public Expression getExpression() {
    return expression;
  }
}
