//package languish.prim.data;
//
//import java.util.Collections;
//import java.util.HashMap;
//import java.util.Map;
//
//import languish.lambda.Expression;
//import languish.lambda.DataFunction;
//import languish.lambda.Data;
//
//public class LMaps {
//  private LMaps() {}
//
//  public static final LMap EMPTY_MAP =
//      LMap.of(Collections.<LSymbol, LObject> emptyMap());
//
//  public static final DataFunction PUT_MAP = new DataFunction("PUT_MAP") {
//    @Override
//    public Expression apply(LObject obj) {
//      final LMap map = (LMap) obj;
//
//      return new DataFunction("PUT_MAP*") {
//        @Override
//        public Expression apply(LObject obj) {
//          final LSymbol key = (LSymbol) obj;
//
//          return new DataFunction("PUT_MAP*") {
//            @Override
//            public Expression apply(LObject value) {
//              Map<LSymbol, LObject> internal =
//                  new HashMap<LSymbol, LObject>(map.getMap());
//
//              internal.put(key, value);
//
//              return Data.of(LMap.of(internal));
//            }
//          };
//        }
//      };
//    }
//  };
//
//  public static final DataFunction GET_MAP = new DataFunction("GET_MAP") {
//    @Override
//    public Expression apply(LObject obj) {
//      final LMap map = (LMap) obj;
//
//      return new DataFunction("GET_MAP*") {
//        @Override
//        public Expression apply(LObject obj) {
//          LSymbol key = (LSymbol) obj;
//
//          return Data.of(map.getMap().get(key));
//        }
//      };
//    }
//  };
//
//  public static final DataFunction DEL_MAP = new DataFunction("DEL_MAP") {
//    @Override
//    public Expression apply(LObject obj) {
//      final LMap map = (LMap) obj;
//
//      return new DataFunction("DEL_MAP*") {
//        @Override
//        public Expression apply(LObject obj) {
//          LSymbol key = (LSymbol) obj;
//
//          Map<LSymbol, LObject> internal =
//              new HashMap<LSymbol, LObject>(map.getMap());
//
//          internal.remove(key);
//
//          return Data.of(LMap.of(internal));
//        }
//      };
//    }
//  };
//}
