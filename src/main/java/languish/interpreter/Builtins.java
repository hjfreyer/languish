package languish.interpreter;

import languish.lambda.Expression;
import languish.lambda.Wrapper;
import languish.prim.data.LBoolean;
import languish.prim.data.LBooleans;
import languish.prim.data.LComposites;
import languish.prim.data.LExpressionWrappers;
import languish.prim.data.LIntegers;
import languish.prim.data.LMaps;

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
  MK_BUILTIN_GET(LExpressionWrappers.MK_BUILTIN_GET),

  // LComposite
  GET_ELEMENT(LComposites.GET_ELEMENT),
  WRAP(LComposites.WRAP),

  // Map Expressions
  EMPTY_MAP(Wrapper.of(LMaps.EMPTY_MAP)),
  PUT_MAP(LMaps.PUT_MAP),

  // Hadrian
  EMPTY_GRAMMAR(Wrapper.of(LMaps.EMPTY_MAP)),
  SET_EXPORT(Wrapper.of(LMaps.EMPTY_MAP)),
  SET_NONTERMS(Wrapper.of(LMaps.EMPTY_MAP)),
  TERM(Wrapper.of(LMaps.EMPTY_MAP)),
  NON_TERM(Wrapper.of(LMaps.EMPTY_MAP)),
  RENDER_GRAMMAR(Wrapper.of(LMaps.EMPTY_MAP)),

  ;

  private Expression expression;

  private Builtins(Expression op) {
    this.expression = op;
  }

  public Expression getExpression() {
    return expression;
  }
}
