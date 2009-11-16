package languish.util;

import languish.lambda.Operation;
import languish.lambda.Operations;
import languish.lambda.Primitive;
import languish.lambda.Term;
import languish.primitives.LBoolean;
import languish.primitives.LCharacter;
import languish.primitives.LInteger;
import languish.primitives.LSymbol;

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
    if (p instanceof LBoolean) {
      return ((Boolean) p.getJavaObject()) ? "TRUE" : "FALSE";
    }

    if (p instanceof LCharacter) {
      return "'" + p.getJavaObject() + "'";
    }

    if (p instanceof LInteger) {
      return p.getJavaObject().toString();
    }

    if (p instanceof LSymbol) {
      return '"' + p.getJavaObject().toString() + '"';
    }

    throw new IllegalArgumentException("Unknown primitive: " + p);
  }
}
