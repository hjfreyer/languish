//package languish.prim.data;
//
//import languish.lambda.Expression;
//import languish.lambda.DataFunction;
//import languish.lambda.Data;
//
//public class LComposites {
//
//  public static final DataFunction WRAP = new DataFunction("WRAP") {
//    @Override
//    public Expression apply(LObject obj) {
//      final int elements = ((LInteger) obj).intValue();
//
//      final LComposite result = new LComposite(elements);
//
//      return compositePutter(result, 0);
//    }
//  };
//
//  public static final DataFunction GET_ELEMENT =
//      new DataFunction("GET_ELEMENT") {
//        @Override
//        public Expression apply(LObject arg) {
//          final LComposite comp = (LComposite) arg;
//
//          return new DataFunction("GET_ELEMENT*") {
//            @Override
//            public Expression apply(LObject obj) {
//              LInteger index = (LInteger) obj;
//
//              return Data.of(comp.getArray()[index.intValue()]);
//            }
//          };
//        }
//      };
//
//  private static Expression compositePutter(final LComposite result,
//      final int numPutAlready) {
//    return numPutAlready == result.size() ? Data.of(result)
//        : new DataFunction("WRAP*") {
//          @Override
//          public Expression apply(LObject obj) {
//            result.getArray()[numPutAlready] = obj;
//
//            return
//
//            compositePutter(result, numPutAlready + 1);
//          }
//        };
//  }
//
//  private LComposites() {}
//}
