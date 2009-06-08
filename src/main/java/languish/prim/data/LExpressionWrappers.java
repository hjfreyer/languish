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

  public static final NativeFunction MK_APP =
      new NativeFunction("MK_APP", true) {
        @Override
        public Expression apply(LObject obj) {
          final LExpressionWrapper function = (LExpressionWrapper) obj;

          return new NativeFunction("MK_APP*") {
            @Override
            public Expression apply(LObject obj) {
              LExpressionWrapper argument = (LExpressionWrapper) obj;

              return Wrapper.of(new LExpressionWrapper(Application.of(function
                  .getExpression(), argument.getExpression())));
            }
          };
        }
      };

  public static final NativeFunction MK_ABS =
      new NativeFunction("MK_ABS", true) {
        @Override
        public Expression apply(LObject obj) {
          LExpressionWrapper expression = (LExpressionWrapper) obj;

          return Wrapper.of(new LExpressionWrapper(Abstraction.of(expression
              .getExpression())));
        }
      };

  public static final NativeFunction MK_WRAPPER =
      new NativeFunction("MK_WRAPPER", true) {
        @Override
        public Expression apply(LObject obj) {
          return Wrapper.of(new LExpressionWrapper(Wrapper.of(obj)));
        }
      };

  public static final NativeFunction MK_REF =
      new NativeFunction("MK_REF", true) {
        @Override
        public Expression apply(LObject obj) {
          int index = ((LInteger) obj).intValue();

          return Wrapper.of(new LExpressionWrapper(Reference.to(index)));
        }
      };

  public static final NativeFunction MK_NAT =
      new NativeFunction("MK_NAT", true) {
        @Override
        public Expression apply(LObject obj) {
          LSymbol nat = (LSymbol) obj;

          return Wrapper.of(new LExpressionWrapper(Builtins.valueOf(
              nat.stringValue()).getExpression()));
        }
      };
}
