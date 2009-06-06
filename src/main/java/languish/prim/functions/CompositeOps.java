package languish.prim.functions;

import languish.lambda.Expression;
import languish.lambda.NativeFunction;
import languish.lambda.Wrapper;
import languish.prim.data.LComposite;
import languish.prim.data.LInteger;
import languish.prim.data.LObject;

public class CompositeOps {

  public static final NativeFunction WRAP = new NativeFunction("WRAP") {
    @Override
    public Expression apply(LObject obj) {
      return null;
    }
  };

  public static final NativeFunction GET_ELEMENT =
      new NativeFunction("GET_ELEMENT") {
        @Override
        public Expression apply(LObject arg) {
          final LComposite comp = (LComposite) arg;

          return new NativeFunction("GET_ELEMENT*") {
            @Override
            public Expression apply(LObject obj) {
              LInteger index = (LInteger) obj;

              return Wrapper.of(comp.get(index.intValue()));
            }
          };
        }
      };

  private CompositeOps() {}
}
