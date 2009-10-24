package languish.lang;

import static languish.base.Lambda.*;
import junit.framework.TestCase;
import languish.base.Tuple;
import languish.base.Util;
import languish.lang.Integers;
import languish.lang.Lists;
import languish.lang.Visitor;
import languish.primitives.DataFunctions;
import languish.primitives.LBoolean;
import languish.primitives.LInteger;
import languish.testing.TestUtil;

import com.google.common.collect.ImmutableList;

public class VisitorTest extends TestCase {

  private static final Tuple ADD_FORTYTWO =
      abs(prim(DataFunctions.ADD, cons(data(LInteger.of(42)), ref(1))));

  private static final Tuple SUM_UP =
      abs(app(app(app(Lists.reduce(), Integers.add()), ref(1)), data(LInteger
          .of(0))));

  @SuppressWarnings("unchecked")
  public void testLeaf() {
    Tuple functionMap =
        Util.convertJavaToPrimitive(ImmutableList.of(ImmutableList.of("func1",
            ADD_FORTYTWO)));
    Tuple isLeaf = abs(data(LBoolean.TRUE));
    Tuple tree = Util.convertJavaToPrimitive(ImmutableList.of("func1", 3));

    Tuple visitorCall =
        app(app(app(Visitor.visitTree(), functionMap), isLeaf), tree);

    TestUtil.assertReducesToData(LInteger.of(45), visitorCall);
  }

  public void testSimpleTree() {
    Tuple functionMap =
        Util.convertJavaToPrimitive(ImmutableList.of(ImmutableList.of("func1",
            ADD_FORTYTWO)));
    Tuple isLeaf = abs(data(LBoolean.TRUE));
    Tuple tree = Util.convertJavaToPrimitive(ImmutableList.of("func1", 3));

    Tuple visitorCall =
        app(app(app(Visitor.visitTree(), functionMap), isLeaf), tree);

    TestUtil.assertReducesToData(LInteger.of(45), visitorCall);
  }
}
