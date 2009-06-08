package languish.prim.data;

import languish.lambda.Expression;
import languish.lambda.NativeFunction;
import languish.lambda.Wrapper;

public class LComposites {

  public static final NativeFunction WRAP = new NativeFunction("WRAP", true) {
    @Override
    public Expression apply(LObject obj) {
      final int elements = ((LInteger) obj).intValue();

      final LComposite result = new LComposite(elements);

      return compositePutter(result, 0);
    }
  };

  public static final NativeFunction GET_ELEMENT =
      new NativeFunction("GET_ELEMENT", true) {
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

  private static Expression compositePutter(final LComposite result,
      final int numPutAlready) {
    return numPutAlready == result.size() ? Wrapper.of(result)
        : new NativeFunction("WRAP*") {
          @Override
          public Expression apply(LObject obj) {
            result.set(numPutAlready, obj);

            return

            compositePutter(result, numPutAlready + 1);
          }
        };
  }

  private LComposites() {}
}
