package languish.prim.data;

public abstract class LObject {
  // public abstract Type getType();

  public final LObject getValue() {
    return this;
  }

  @Override
  public abstract boolean equals(Object obj);

  @Override
  public abstract int hashCode();
  //
  // public static LObject of(ReducedExpression exp) {
  // switch (exp.getType()) {
  // case ABSTRACTION:
  // return Function.of(exp);
  // case LITERAL:
  // return ((Literal) exp).getObject();
  // default:
  // throw new AssertionError();
  // }
  // }

  // public abstract String repr();
}