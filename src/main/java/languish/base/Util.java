package languish.base;

public class Util {

  public static Tuple listify(Tuple... contents) {
    Tuple result = Lambda.data(Tuple.of());

    for (int i = contents.length - 1; i >= 0; i--) {
      result = Lambda.cons(contents[i], result);
    }

    return result;
  }

  private Util() {}
}
