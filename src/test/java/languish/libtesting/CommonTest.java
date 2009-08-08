package languish.libtesting;

import static languish.base.Lambda.*;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;
import languish.base.LObject;
import languish.base.Tuple;
import languish.testing.LanguishTestList;
import languish.testing.TestUtil;

public class CommonTest extends TestCase{
  public static final Tuple NULL = data(Tuple.of());
  
  public static final Tuple CAAR = abs(car(car(ref(1))));
  public static final Tuple CADR = abs(car(cdr(ref(1))));

  public static final Tuple OMEGA = abs(app(ref(1), ref(1)));
  public static final String OMEGA_CODE = "[ABS [APP [REF 1] [REF 1]]]";

  public enum Tests implements LanguishTestList {
    
    ;

    private final Tuple expression;
    private final String code;
    private final Tuple reducedOnce;
    private final LObject reducedCompletely;

    private Tests(Tuple expression, String code, Tuple reducedOnce,
        LObject reducedCompletely) {
      this.expression = expression;
      this.code = code;
      this.reducedOnce = reducedOnce;
      this.reducedCompletely = reducedCompletely;
    }

    public Tuple getExpression() {
      return expression;
    }

    public String getCode() {
      return code;
    }

    public Tuple getReducedOnce() {
      return reducedOnce;
    }

    public LObject getReducedCompletely() {
      return reducedCompletely;
    }

    public List<?> getListContents() {
      return null;
    }
  }

  public void test() {
    TestUtil.testExpressions(Arrays.asList(Tests.values()));
  }

}
