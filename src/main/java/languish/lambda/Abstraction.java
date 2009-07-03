package languish.lambda;

public final class Abstraction {

  public static Tuple of(Tuple exp) {
    return Tuple.of(Lambda.ABS, Tuple.of(exp));
  }
}