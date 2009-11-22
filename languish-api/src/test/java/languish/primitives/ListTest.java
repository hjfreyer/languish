//package languish.primitives;
//
//import static languish.lang.CommonTest.NULL;
//import static languish.testing.TestUtil.FOUR;
//import static languish.testing.TestUtil.SIX;
//import static languish.testing.TestUtil.THREE;
//import static languish.testing.TestUtil.TWO;
//import static languish.util.Lambda.*;
//
//import java.util.Arrays;
//import java.util.List;
//
//import junit.framework.TestCase;
//import languish.lambda.LObject;
//import languish.lambda.Term;
//import languish.testing.LanguishTestCase;
//import languish.testing.TestUtil;
//import languish.util.Util;
//
//import com.google.common.collect.ImmutableList;
//
//public class ListTest extends TestCase {
//
//  public enum Tests implements LanguishTestCase {
//    // IDENITY TESTS
//    TEST_EMPTY_LIST(primitive(Term.of()), //
//        "[DATA []]", null, ImmutableList.of()),
//
//    SINGLE_ELEMENT_LIST(cons(primitive(TWO), primitive(Term.of())), //
//        "[CONS [DATA 2] [DATA []]]", null, ImmutableList.of(TWO)),
//
//    DOUBLE_ELEMENT_LIST(cons(primitive(THREE), cons(primitive(TWO), primitive(Term.of()))), //
//        "[CONS [DATA 3] [CONS [DATA 2] [DATA []]]]", null, ImmutableList.of(
//            THREE, TWO)),
//
//    TRIPLE_ELEMENT_LIST(
//        cons(primitive(THREE), cons(primitive(FOUR), cons(primitive(TWO), primitive(Term.of())))), //
//        "[CONS [DATA 3] [CONS [DATA 4] [CONS [DATA 2] [DATA []]]]]", null,
//        ImmutableList.of(THREE, FOUR, TWO)),
//
//    NESTED_LIST(
//        cons(SINGLE_ELEMENT_LIST.expression, cons(primitive(SIX), primitive(Term.of()))), //
//        "[CONS [CONS [DATA 2] [DATA []]] [CONS [DATA 6] [DATA []]]]", null,
//        ImmutableList.of(ImmutableList.of(TWO), SIX)),
//
//    TEST_CAR(car(TRIPLE_ELEMENT_LIST.expression), //
//        "[CAR " + TRIPLE_ELEMENT_LIST.code + "]", primitive(THREE), null, THREE),
//
//    TEST_CDR(cdr(TRIPLE_ELEMENT_LIST.expression), //
//        "[CDR " + TRIPLE_ELEMENT_LIST.code + "]", Util.listify(primitive(FOUR),
//            primitive(TWO)), ImmutableList.of(FOUR, TWO)),
//
//    TEST_CADR(car(cdr(TRIPLE_ELEMENT_LIST.expression)), //
//        "[CAR [CDR " + TRIPLE_ELEMENT_LIST.code + "]]", car(Util.listify(
//            primitive(FOUR), primitive(TWO))), null, FOUR),
//
//    TEST_CAAR(
//        car(car(NESTED_LIST.expression)), //
//        "[CAR [CAR " + NESTED_LIST.code + "]]", car(cons(primitive(TWO), NULL)),
//        null, TWO),
//
//    ;
//
//    private final Term expression;
//    private final String code;
//    private final Term reducedOnce;
//    private final List<?> listContents;
//    private final LObject reducedCompletely;
//
//    private Tests(Term expression, String code, Term reducedOnce,
//        List<?> listContents) {
//      this(expression, code, reducedOnce, listContents, null);
//    }
//
//    private Tests(Term expression, String code, Term reducedOnce,
//        List<?> listContents, LObject reducedCompletely) {
//      this.expression = expression;
//      this.code = code;
//      this.reducedOnce = reducedOnce;
//      this.listContents = listContents;
//      this.reducedCompletely = reducedCompletely;
//    }
//
//    public Term getExpression() {
//      return expression;
//    }
//
//    public String getCode() {
//      return code;
//    }
//
//    public Term getReducedOnce() {
//      return reducedOnce;
//    }
//
//    public List<?> getListContents() {
//      return listContents;
//    }
//
//    public LObject getReducedCompletely() {
//      return reducedCompletely;
//    }
//  }
//
//  public void test() {
//    TestUtil.assertLanguishTestCase(Arrays.asList(Tests.values()));
//  }
//  //
//  // public void assertListContents(Tuple list, LObject... contents) {
//  // for (int i = 0; i < contents.length; i++) {
//  // Tuple car = Lambda.get(1, list);
//  //
//  // LObject obj = Lambda.reduce(car);
//  // list = Lambda.get(2, list);
//  //
//  // assertEquals("list[" + i + "] invalid", contents[i], obj);
//  // }
//  //
//  // assertEquals("list was longer than expected", Tuple.of(), Lambda
//  // .reduce(list));
//  // }
// }
