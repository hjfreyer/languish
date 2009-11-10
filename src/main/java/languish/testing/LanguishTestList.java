package languish.testing;

import java.util.List;

import languish.lambda.LObject;
import languish.lambda.Term;

public interface LanguishTestList {

  public String name();

  public Term getExpression();

  public String getCode();

  public Term getReducedOnce();

  public LObject getReducedCompletely();

  public List<?> getListContents();
}
