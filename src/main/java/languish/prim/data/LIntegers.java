package languish.prim.data;

import languish.lambda.Expression;
import languish.lambda.NativeFunction;
import languish.lambda.Wrapper;

public class LIntegers {
  private LIntegers() {}

  public static final NativeFunction ADD = new NativeFunction("ADD", true) {
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
