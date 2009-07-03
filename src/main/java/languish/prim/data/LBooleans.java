package languish.prim.data;

import languish.lambda.Abstraction;
import languish.lambda.DataFunction;
import languish.lambda.Reference;
import languish.lambda.Tuple;

public class LBooleans {
  private LBooleans() {

  }

  public static final Tuple BRANCH_THEN =
      Abstraction.of(Abstraction.of(Reference.to(2)));
  public static final Tuple BRANCH_ELSE =
      Abstraction.of(Abstraction.of(Reference.to(1)));

  public static final DataFunction BRANCH = new DataFunction() {
    @Override
    public LObject apply(LObject obj) {
      return ((LBoolean) obj).booleanValue() ? BRANCH_THEN : BRANCH_ELSE;
    }
  };

  //
  // public static final BuiltinFunction MK_BRANCH =
  // new SingleArgumentDataFunction() {
  // @Override
  // public LObject apply(LObject arg) {
  // boolean bool = ((LBoolean) arg).booleanValue();
  //
  // // return PrimitiveCall.of(Builtins.REDUCE_EXP.getOp(),
  // // new Expression[] { bool ? BRANCH_THEN : BRANCH_ELSE });
  //
  // return bool ? BRANCH_THEN : BRANCH_ELSE;
  // }
  //
  // @Override
  // public String getName() {
  // return "MK_BRANCH";
  // }
  // };
}
