package languish.tools.parsing;

import java.util.List;
import java.util.Map;

import languish.base.Primitive;
import languish.base.Term;
import languish.base.Terms;
import languish.parsing.SemanticModule;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

public class LambdaPlusSemantic {

  public static final Map<String, Function<String, Object>> LEAF_RULES =
      SemanticModule.getIdentityLeafRules(ImmutableList
          .of("CONS", "CAR", "CDR"));

  public static final Map<String, Function<List<Object>, Object>> INODE_RULES =
      ImmutableMap.<String, Function<List<Object>, Object>> builder() //
          .put("ABS_TERM", new Function<List<Object>, Object>() {
            @Override
            public Object apply(List<Object> arg) {
              return Terms.abs((Term) arg.get(2));
            }
          }).put("CONS_TERM", new Function<List<Object>, Object>() {
            @Override
            public Object apply(List<Object> arg) {
              return Terms.cons((Term) arg.get(2), (Term) arg.get(3));
            }
          }).put("CAR_TERM", new Function<List<Object>, Object>() {
            @Override
            public Object apply(List<Object> arg) {
              return Terms.car((Term) arg.get(2));
            }
          }).put("CDR_TERM", new Function<List<Object>, Object>() {
            @Override
            public Object apply(List<Object> arg) {
              return Terms.cdr((Term) arg.get(2));
            }
          }).put("SHORT_PRIM_TERM", new Function<List<Object>, Object>() {
            @Override
            public Object apply(List<Object> arg0) {
              return Terms.primitive((Primitive) arg0.get(2));
            }
          }).put("SHORT_REF_TERM", new Function<List<Object>, Object>() {
            @Override
            public Object apply(List<Object> arg0) {
              return Terms.ref((Integer) arg0.get(2));
            }
          }).build();

  public static final SemanticModule PREPROCESSOR_SEMANTIC =
      TermSemantic.TERM_SEMANTIC.extend(new SemanticModule(LEAF_RULES,
          INODE_RULES));

}
