package languish.util;

import languish.lambda.Operation;
import languish.lambda.Operations;
import languish.lambda.Primitive;
import languish.lambda.Term;

public class TermPrinter {

  public static String getCodeForTerm(Term exp) {
    if (exp == Term.NULL) {
      return "NULL";
    }

    StringBuilder out = new StringBuilder();

    Operation op = exp.getOperation();
    Object first = exp.getFirst();
    Object second = exp.getSecond();

    out.append('[');

    if (op == Operations.ABS) {
      out.append("ABS");
    } else if (op == Operations.APP) {
      out.append("APP");
    } else if (op == Operations.EQUALS) {
      out.append("EQUALS");
    } else if (op == Operations.NATIVE_APPLY) {
      out.append("NATIVE_APPLY");
    } else if (op == Operations.NOOP) {
      throw new AssertionError("Non null term with NOOP: " + op);
    } else if (op == Operations.PRIMITIVE) {
      out.append("PRIMITIVE");
    } else if (op == Operations.REF) {
      out.append("REF");
    }

    out.append(' ');

    if (op == Operations.PRIMITIVE) {
      out.append(getCodeForPrimitive((Primitive) first));
    } else if (op == Operations.REF) {
      out.append(first);
    } else {
      out.append(getCodeForTerm((Term) first));
    }

    out.append(' ');

    out.append(getCodeForTerm((Term) second));

    out.append(']');

    return out.toString();
  }

  public static String getCodeForPrimitive(Primitive p) {
    if (p.isBoolean()) {
      return p.asBoolean() ? "TRUE" : "FALSE";
    }

    if (p.isCharacter()) {
      return "'" + p.getJavaObject() + "'";
    }

    if (p.isInteger()) {
      return "" + p.asInteger();
    }

    if (p.isString()) {
      return '"' + p.asString() + '"';
    }

    throw new AssertionError();
  }
}
