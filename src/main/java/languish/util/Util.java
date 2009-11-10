package languish.util;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import languish.lambda.Lambda;
import languish.lambda.Operation;
import languish.lambda.Term;
import languish.primitives.DataWrapper;
import languish.primitives.LBoolean;
import languish.primitives.LCharacter;
import languish.primitives.LInteger;
import languish.primitives.LSymbol;

import com.google.common.collect.ImmutableList;

public class Util {

  public static Term listify(Term... contents) {
    return convertJavaToPrimitive(Arrays.asList(contents));
  }

  public static Term convertJavaToPrimitive(Object obj) {
    if (obj instanceof Term) {
      return (Term) obj;
    } else if (obj instanceof LObject) {
      return Lambda.primitive((LObject) obj);
    } else if (obj instanceof List<?>) {
      List<?> list = (List<?>) obj;

      Term result = Lambda.primitive(Term.of());
      for (int i = list.size() - 1; i >= 0; i--) {
        result = Lambda.cons(convertJavaToPrimitive(list.get(i)), result);
      }

      return result;
    } else if (obj instanceof Boolean) {
      return Lambda.primitive(LBoolean.of((Boolean) obj));
    } else if (obj instanceof Character) {
      return Lambda.primitive(LCharacter.of((Character) obj));
    } else if (obj instanceof Integer) {
      return Lambda.primitive(LInteger.of((Integer) obj));
    } else if (obj instanceof String) {
      return Lambda.primitive(LSymbol.of((String) obj));
    } else {
      throw new AssertionError();
    }
  }

  public static Object convertPrimitiveToJava(Term tuple) {
    if (!Lambda.isPrimitive(tuple)) {
      throw new IllegalArgumentException("Not primitive: " + tuple);
    }

    Operation op = (Operation) tuple.getFirst();
    if (op == Lambda.DATA) {
      LObject data = tuple.getSecond();

      if (data.equals(Term.of())) {
        return null;
      }

      return ((DataWrapper) data).getJavaValue();
    } else if (op == Lambda.CONS) {
      Object car = convertPrimitiveToJava((Term) tuple.getSecond());
      List<?> cdr = (List<?>) convertPrimitiveToJava((Term) tuple.getThird());

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
