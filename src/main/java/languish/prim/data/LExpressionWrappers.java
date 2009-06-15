package languish.prim.data;

import languish.interpreter.Builtins;
import languish.lambda.Abstraction;
import languish.lambda.Application;
import languish.lambda.Expression;
import languish.lambda.NativeFunction;
import languish.lambda.Reference;
import languish.lambda.Wrapper;

public class LExpressionWrappers {
  private LExpressionWrappers() {}

  public static final NativeFunction MK_APP = new NativeFunction("MK_APP") {
    @Override
    public Expression apply(LObject obj) {
      final Expression function = (Expression) obj;

      return new NativeFunction("MK_APP*") {
        @Override
        public Expression apply(LObject obj) {
          Expression argument = (Expression) obj;

          return Wrapper.of(Application.of(function, argument));
        }
      };
    }
  };

  public static final NativeFunction MK_ABS = new NativeFunction("MK_ABS") {
    @Override
    public Expression apply(LObject obj) {
      Expression expression = (Expression) obj;

      return Wrapper.of(Abstraction.of(expression));
    }
  };

  public static final NativeFunction MK_WRAPPER =
      new NativeFunction("MK_WRAPPER") {
        @Override
        public Expression apply(LObject obj) {
          return Wrapper.of(Wrapper.of(obj));
        }
      };

  public static final NativeFunction MK_REF = new NativeFunction("MK_REF") {
    @Override
    public Expression apply(LObject obj) {
      int index = ((LInteger) obj).intValue();

      return Wrapper.of(Reference.to(index));
    }
  };

  public static final NativeFunction MK_BUILTIN_GET =
      new NativeFunction("MK_BUILTIN_GET") {
        @Override
        public Expression apply(LObject obj) {
          LSymbol nat = (LSymbol) obj;

          return Wrapper
              .of(Builtins.valueOf(nat.stringValue()).getExpression());
        }
      };
}
