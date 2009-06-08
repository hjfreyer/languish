package languish.prim.data;

import languish.lambda.Expression;
import languish.lambda.Wrapper;

public final class LUnit extends LObject {

  private LUnit() {}

  public static final LUnit UNIT = new LUnit();

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public boolean equals(Object obj) {
    return obj != null && obj instanceof LUnit;
  }

  @Override
  public Expression getGeneratingExpression() {
    return Wrapper.of(this);
  }

  public String repr() {
    return null;
  }
}
