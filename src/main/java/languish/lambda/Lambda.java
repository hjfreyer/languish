package languish.lambda;

public class Lambda {
  public static Term abs(Term exp) {
    return new Term(Operations.ABS, exp, Term.NULL);
  }

  public static Term app(Term func, Term arg) {
    return new Term(Operations.APP, func, arg);
  }

  public static Term primitive(Primitive obj) {
    return new Term(Operations.PRIMITIVE, obj, Term.NULL);
  }

  public static Term ref(int i) {
    return new Term(Operations.REF, i, Term.NULL);
  }

  public static Term nativeApply(final NativeFunction func, Term arg) {
    Primitive funcPrimitive = new Primitive() {
      public Object getJavaObject() {
        return func;
      }
    };

    return new Term(Operations.NATIVE_APPLY, funcPrimitive, arg);
  }

  public static Term cons(Term obj1, Term obj2) {
    return new Term(Operations.CONS, obj1, obj2);
  }

  public static Term car(Term obj) {
    return new Term(Operations.CAR, obj, Term.NULL);
  }

  public static Term cdr(Term obj) {
    return new Term(Operations.CDR, obj, Term.NULL);
  }

  private Lambda() {
  }
}
