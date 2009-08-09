package languish.base;

import java.util.Arrays;
import java.util.List;

import languish.primitives.LBoolean;
import languish.primitives.LCharacter;
import languish.primitives.LInteger;
import languish.primitives.LSymbol;

public class Util {

  public static Tuple listify(Tuple... contents) {
    return convertToLObjectExpression(Arrays.asList(contents));
  }

  public static Tuple convertToLObjectExpression(Object obj) {
    if (obj instanceof Tuple) {
      return (Tuple) obj;
    } else if (obj instanceof LObject) {
      return Lambda.data((LObject) obj);
    } else if (obj instanceof List) {
      List<?> list = (List<?>) obj;

      Tuple result = Lambda.data(Tuple.of());
      for (int i = list.size() - 1; i >= 0; i--) {
        result = Lambda.cons(convertToLObjectExpression(list.get(i)), result);
      }

      return result;
    } else if (obj instanceof Boolean) {
      return Lambda.data(LBoolean.of((Boolean) obj));
    } else if (obj instanceof Character) {
      return Lambda.data(LCharacter.of((Character) obj));
    } else if (obj instanceof Integer) {
      return Lambda.data(LInteger.of((Integer) obj));
    } else if (obj instanceof String) {
      return Lambda.data(LSymbol.of((String) obj));
    } else {
      throw new AssertionError();
    }
  }

  private Util() {}
}
