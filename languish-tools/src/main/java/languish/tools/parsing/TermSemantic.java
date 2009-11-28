package languish.tools.parsing;

import languish.base.Operation;
import languish.base.Operations;
import languish.base.Primitive;
import languish.base.Term;
import languish.base.Terms;
import languish.util.PrimitiveTree;

public class TermSemantic {

  public static Term termFromAST(PrimitiveTree prim) {
    return visitTerm(prim);
  }

  public static Term visitTerm(PrimitiveTree prim) {
    String name = prim.asList().get(0).asPrimitive().asString();
    PrimitiveTree children = prim.asList().get(1);
    if (name.equals("NULL_TERM")) {
      return Term.NULL;
    } else if (name.equals("PRIMITIVE_TERM")) {
      return visitPrimitive(children.asList().get(2));
    } else if (name.equals("REF_TERM")) {
      return Terms.ref(Integer.parseInt(children.asList().get(2).asPrimitive()
          .asString()));
    } else if (name.equals("TERM_PROPER")) {
      Operation op = visitOperation(children.asList().get(1));
      Term first = visitTerm(children.asList().get(2));
      Term second = visitTerm(children.asList().get(3));

      return new Term(op, first, second);
    } else {
      throw new IllegalArgumentException();
    }
  }

  public static Operation visitOperation(PrimitiveTree prim) {
    String name = prim.asList().get(0).asPrimitive().asString();

    if (name.equals("ABS_OP")) {
      return Operations.ABS;
    } else if (name.equals("APP_OP")) {
      return Operations.APP;
    } else if (name.equals("EQUALS_OP")) {
      return Operations.EQUALS;
    } else if (name.equals("NATIVE_APPLY_OP")) {
      return Operations.NATIVE_APPLY;
    } else {
      throw new IllegalArgumentException();
    }
  }

  public static Term visitPrimitive(PrimitiveTree prim) {
    String name = prim.asList().get(0).asPrimitive().asString();
    PrimitiveTree children = prim.asList().get(1);

    if (name.equals("STRING")) {
      String unescaped = children.asPrimitive().asString();

      String escaped = unescaped.substring(1, unescaped.length() - 1) //
          .replaceAll("\\\\(.)", "\\1");

      return Terms.primitive(new Primitive(escaped));
    } else if (name.equals("INTEGER")) {
      return Terms.primitive(new Primitive(Integer.parseInt(children
          .asPrimitive().asString())));
    } else {
      throw new IllegalArgumentException();
    }
  }

  private TermSemantic() {
  }
}
