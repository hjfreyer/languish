package languish.base;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import languish.primitives.DataWrapper;
import languish.primitives.LBoolean;
import languish.primitives.LCharacter;
import languish.primitives.LInteger;
import languish.primitives.LSymbol;

import com.google.common.collect.ImmutableList;

public class Util {

  public static Tuple listify(Tuple... contents) {
    return convertJavaToPrimitive(Arrays.asList(contents));
  }

  public static Tuple convertJavaToPrimitive(Object obj) {
    if (obj instanceof Tuple) {
      return (Tuple) obj;
    } else if (obj instanceof LObject) {
      return Lambda.data((LObject) obj);
    } else if (obj instanceof List) {
      List<?> list = (List<?>) obj;

      Tuple result = Lambda.data(Tuple.of());
      for (int i = list.size() - 1; i >= 0; i--) {
        result = Lambda.cons(convertJavaToPrimitive(list.get(i)), result);
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

  public static Object convertPrimitiveToJava(Tuple tuple) {
    if (!Lambda.isPrimitive(tuple)) {
      throw new IllegalArgumentException("Not primitive: " + tuple);
    }

    Operation op = (Operation) tuple.getFirst();
    if (op == Lambda.DATA) {
      LObject data = tuple.getSecond();

      if (data.equals(Tuple.of())) {
        return null;
      }

      return ((DataWrapper) data).getJavaValue();
    } else if (op == Lambda.CONS) {
      Object car = convertPrimitiveToJava((Tuple) tuple.getSecond());
      List<?> cdr = (List<?>) convertPrimitiveToJava((Tuple) tuple.getThird());

      cdr = (cdr != null) ? cdr : ImmutableList.of();

      List<Object> result = new LinkedList<Object>();
      result.add(car);
      result.addAll(cdr);

      return result;
    } else {
      throw new AssertionError();
    }
  }

  private Util() {
  }
}
