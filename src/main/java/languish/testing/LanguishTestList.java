package languish.testing;

import java.util.List;

import languish.base.LObject;
import languish.base.Tuple;

public interface LanguishTestList {

  public String name();

  public Tuple getExpression();

  public String getCode();

  public Tuple getReducedOnce();

  public LObject getReducedCompletely();

  public List<?> getListContents();
}
