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
import com.google.common.collect.ImmutableList;
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
          }).put("TRUE_LIT", new Function<List<Object>, Object>() {
            @Override
            public Object apply(List<Object> arg) {
              return new Primitive(true);
            }
          }).put("FALSE_LIT", new Function<List<Object>, Object>() {
            @Override
            public Object apply(List<Object> arg) {
              return new Primitive(false);
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
          }).put("NATIVE_APPLY_OP", new Function<List<Object>, Object>() {

            @Override
            public Object apply(List<Object> arg) {
              return Operations.NATIVE_APPLY;
            }
          }).put("EQUALS_OP", new Function<List<Object>, Object>() {

            @Override
            public Object apply(List<Object> arg) {
              return Operations.EQUALS;
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

  private static final List<String> UNWRAP_RULES =
      ImmutableList.of("BOOLEAN_PRIM");

  private static final Map<String, Function<List<Object>, Object>> TERM_INODES =
      ImmutableMap.<String, Function<List<Object>, Object>> builder().putAll(
          PRIMITIVES_RULES).putAll(OPERATIONS_RULES).putAll(TERMS_RULES)
          .putAll(SemanticModule.getUnwrapRules(UNWRAP_RULES)).build();

  private static final List<String> IDENTITY_LEAVES =
      ImmutableList.of("[", "]");

  private static final Map<String, Function<String, Object>> TERM_LEAVES =
      ImmutableMap.<String, Function<String, Object>> builder()
          //
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

          }).putAll(SemanticModule.getIdentityLeafRules(IDENTITY_LEAVES))
          .build();

  public static SemanticModule TERM_SEMANTIC =
      new SemanticModule(TERM_LEAVES, TERM_INODES);

  private TermSemantic() {
  }
}
