package languish.prim.data;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import languish.lambda.Expression;
import languish.lambda.NativeFunction;
import languish.lambda.Wrapper;

public class LMaps {
  private LMaps() {}

  public static final Wrapper EMPTY_MAP =
      Wrapper.of(LMap.of(Collections.<LSymbol, LObject> emptyMap()));

  public static final NativeFunction PUT_MAP =
      new NativeFunction("PUT_MAP", true) {
        @Override
        public Expression apply(LObject obj) {
          final LMap map = (LMap) obj;

          return new NativeFunction("PUT_MAP*") {
            @Override
            public Expression apply(LObject obj) {
              final LSymbol key = (LSymbol) obj;

              return new NativeFunction("PUT_MAP*") {
                @Override
                public Expression apply(LObject value) {
                  Map<LSymbol, LObject> internal =
                      new HashMap<LSymbol, LObject>(map.getMap());

                  internal.put(key, value);

                  return Wrapper.of(LMap.of(internal));
                }
              };
            }
          };
        }
      };

  public static final NativeFunction GET_MAP =
      new NativeFunction("GET_MAP", true) {
        @Override
        public Expression apply(LObject obj) {
          final LMap map = (LMap) obj;

          return new NativeFunction("GET_MAP*") {
            @Override
            public Expression apply(LObject obj) {
              LSymbol key = (LSymbol) obj;

              return Wrapper.of(map.getMap().get(key));
            }
          };
        }
      };

  public static final NativeFunction DEL_MAP =
      new NativeFunction("DEL_MAP", true) {
        @Override
        public Expression apply(LObject obj) {
          final LMap map = (LMap) obj;

          return new NativeFunction("DEL_MAP*") {
            @Override
            public Expression apply(LObject obj) {
              LSymbol key = (LSymbol) obj;

              Map<LSymbol, LObject> internal =
                  new HashMap<LSymbol, LObject>(map.getMap());

              internal.remove(key);

              return Wrapper.of(LMap.of(internal));
            }
          };
        }
      };
}
