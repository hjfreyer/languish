package languish.tools.parsing;

import java.util.List;
import java.util.Map;

import languish.base.Operation;
import languish.base.Operations;
import languish.base.Primitive;
import languish.base.Term;
import languish.base.Terms;
import languish.parsing.SemanticModule;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;

public class TermSemantic {

  private static final Map<String, Function<List<Object>, Object>> PRIMITIVES_RULES =
      ImmutableMap.<String, Function<List<Object>, Object>> builder() //
          .put("STRING_PRIM", new Function<List<Object>, Object>() {
            @Override
            public Object apply(List<Object> arg) {
              return new Primitive(arg.get(0));
            }
          }).put("INTEGER_PRIM", new Function<List<Object>, Object>() {
            @Override
            public Object apply(List<Object> arg) {
              return new Primitive(arg.get(0));
            }
          }).build();

  private static final Map<String, Function<List<Object>, Object>> OPERATIONS_RULES =
      ImmutableMap.<String, Function<List<Object>, Object>> builder() //
          .put("ABS_OP", new Function<List<Object>, Object>() {

            @Override
            public Object apply(List<Object> arg) {
              return Operations.ABS;
            }
          }).put("APP_OP", new Function<List<Object>, Object>() {

            @Override
            public Object apply(List<Object> arg) {
              return Operations.APP;
            }
          }).build();

  private static final Map<String, Function<List<Object>, Object>> TERMS_RULES =
      ImmutableMap.<String, Function<List<Object>, Object>> builder() //
          .put("NULL_TERM", new Function<List<Object>, Object>() {

            @Override
            public Object apply(List<Object> arg) {
              return Term.NULL;
            }

          }).put("REF_TERM", new Function<List<Object>, Object>() {

            @Override
            public Object apply(List<Object> arg) {
              Integer num = (Integer) arg.get(2);
              return Terms.ref(num);
            }

          }).put("PRIMITIVE_TERM", new Function<List<Object>, Object>() {
            @Override
            public Object apply(List<Object> arg) {
              Primitive prim = (Primitive) arg.get(2);

              return Terms.primitive(prim);
            }
          }).put("TERM_PROPER", new Function<List<Object>, Object>() {

            @Override
            public Object apply(List<Object> arg) {
              Operation op = (Operation) arg.get(1);
              Term first = (Term) arg.get(2);
              Term second = (Term) arg.get(3);

              return new Term(op, first, second);
            }
          }).build();

  private static final Map<String, Function<List<Object>, Object>> TERM_INODES =
      ImmutableMap.<String, Function<List<Object>, Object>> builder().putAll(
          PRIMITIVES_RULES).putAll(OPERATIONS_RULES).putAll(TERMS_RULES)
          .build();

  private static final Map<String, Function<String, Object>> TERM_LEAVES =
      ImmutableMap.<String, Function<String, Object>> builder() //
          .put("STRING_LIT", new Function<String, Object>() {
            @Override
            public Object apply(String arg) {
              return arg.substring(1, arg.length() - 1) //
                  .replaceAll("\\\\(.)", "\\1");
            }
          }).put("INTEGER_LIT", new Function<String, Object>() {
            @Override
            public Object apply(String arg) {
              return Integer.parseInt(arg);
            }
          }).build();

  public static SemanticModule TERM_SEMANTIC =
      new SemanticModule(TERM_LEAVES, TERM_INODES);

  //
  // public static Term termFromAST(PrimitiveTree prim) {
  // return visitTerm(prim);
  // }
  //
  // public static Term visitTerm(PrimitiveTree prim) {
  // String name = prim.asList().get(0).asPrimitive().asString();
  // PrimitiveTree children = prim.asList().get(1);
  // if (name.equals("NULL_TERM")) {
  // return Term.NULL;
  // } else if (name.equals("PRIMITIVE_TERM")) {
  // return visitPrimitive(children.asList().get(2));
  // } else if (name.equals("REF_TERM")) {
  // return Terms.ref(Integer.parseInt(children.asList().get(2).asPrimitive()
  // .asString()));
  // } else if (name.equals("TERM_PROPER")) {
  // Operation op = visitOperation(children.asList().get(1));
  // Term first = visitTerm(children.asList().get(2));
  // Term second = visitTerm(children.asList().get(3));
  //
  // return new Term(op, first, second);
  // } else {
  // throw new IllegalArgumentException();
  // }
  // }
  //
  // public static Operation visitOperation(PrimitiveTree prim) {
  // String name = prim.asList().get(0).asPrimitive().asString();
  //
  // if (name.equals("ABS_OP")) {
  // return Operations.ABS;
  // } else if (name.equals("APP_OP")) {
  // return Operations.APP;
  // } else if (name.equals("EQUALS_OP")) {
  // return Operations.EQUALS;
  // } else if (name.equals("NATIVE_APPLY_OP")) {
  // return Operations.NATIVE_APPLY;
  // } else {
  // throw new IllegalArgumentException();
  // }
  // }
  //
  // public static Term visitPrimitive(PrimitiveTree prim) {
  // String name = prim.asList().get(0).asPrimitive().asString();
  // PrimitiveTree children = prim.asList().get(1);
  //
  // if (name.equals("STRING")) {
  // String unescaped = children.asPrimitive().asString();
  //
  // String escaped = unescaped.substring(1, unescaped.length() - 1) //
  // .replaceAll("\\\\(.)", "\\1");
  //
  // return Terms.primitive(new Primitive(escaped));
  // } else if (name.equals("INTEGER")) {
  // return Terms.primitive(new Primitive(Integer.parseInt(children
  // .asPrimitive().asString())));
  // } else {
  // throw new IllegalArgumentException();
  // }
  // }

  private TermSemantic() {
  }
}
