package languish.prim.functions;

import languish.lambda.Expression;
import languish.lambda.NativeFunction;
import languish.lambda.Wrapper;
import languish.prim.data.LInteger;
import languish.prim.data.LObject;

public class IntegerOps {
  private IntegerOps() {}

  public static final NativeFunction ADD = new NativeFunction("ADD") {
    @Override
    public Expression apply(LObject obj) {
      final LInteger intVal1 = (LInteger) obj;

      return new NativeFunction("ADD*") {
        @Override
        public Expression apply(LObject obj) {
          final LInteger intVal2 = (LInteger) obj;

          return Wrapper.of(LInteger
              .of(intVal1.intValue() + intVal2.intValue()));
        }
      };
    }
  };
  //
  // public static BuiltinFunction addPrim() {
  // return ADD;
  // }
  //
  // public static Abstraction add() {
  // return Abstraction.of(Abstraction.of(BuiltinCall.of(Wrapper.of(LSymbol
  // .of("ADD")), new Expression[] { Reference.to(1), Reference.to(2) })));
  // }
}
