package languish.lambda;

import languish.lambda.error.IllegalApplicationError;
import languish.lambda.error.IllegalReductionError;
import languish.prim.data.LObject;

public class Reducer {

  public static LObject reduce(Expression exp) {
    return reduceCompletely(convertExpressionToExp(exp));
  }

  public static Expression reduceOnce(Expression exp) {
    return convertExpToExpression(reduceOnce(convertExpressionToExp(exp)));
  }

  private static enum Type {
    ABSTRACTION,
    APPLICATION,
    NATIVE_FUNC,
    REFERENCE,
    WRAPPER
  }

  private static class Exp {
    Type type;
  }

  public static class Abs extends Exp {
    Type type = Type.ABSTRACTION;
    Exp expression;
  }

  private static class App extends Exp {
    Type type = Type.ABSTRACTION;
    Exp function;
    Exp argument;
  }

  private static class Nat extends Exp {
    Type type = Type.NATIVE_FUNC;
    NativeFunction nativeFunction;
  }

  private static class Ref extends Exp {
    Type type = Type.REFERENCE;
    int id;
  }

  private static class Wrap extends Exp {
    Type type = Type.WRAPPER;
    LObject object;
  }

  private static Exp convertExpressionToExp(Expression exp) {
    switch (exp.getType()) {
    case ABSTRACTION:
      Abstraction abs = (Abstraction) exp;

      Abs absOut = new Abs();
      absOut.expression = convertExpressionToExp(abs.getExpression());

      return absOut;
    case APPLICATION:
      Application app = (Application) exp;

      App appOut = new App();
      appOut.function = convertExpressionToExp(app.getFunction());
      appOut.argument = convertExpressionToExp(app.getArgument());

      return appOut;
    case NATIVE_FUNC:
      NativeFunction nat = (NativeFunction) exp;

      Nat natOut = new Nat();
      natOut.nativeFunction = nat;

      return natOut;
    case REFERENCE:
      Reference ref = (Reference) exp;

      Ref refOut = new Ref();
      refOut.id = ref.getIndex();

      return refOut;
    case WRAPPER:
      Wrapper wrap = (Wrapper) exp;

      Wrap wrapOut = new Wrap();
      wrapOut.object = wrap.getContents();

      return wrapOut;
    }
    throw new AssertionError();
  }

  private static Expression convertExpToExpression(Exp exp) {
    return null;
  }

  private static LObject reduceCompletely(Exp exp) {
    while (true) {
      if (exp.type != Type.WRAPPER) {
        exp = reduceOnce(exp);
      } else {
        return ((Wrap) exp).object;
      }
    }
  }

  private static Exp reduceOnce(Exp exp) {
    if (exp.type != Type.APPLICATION) {
      throw new IllegalReductionError(exp + " cannot be reduced to wrapper");
    }

    App app = (App) exp;

    switch (app.function.type) {
    case WRAPPER:
    case REFERENCE:
      throw new IllegalApplicationError(app.function
          + " cannot be reduced to abstraction");

    case APPLICATION:
      app.function = reduceOnce(app.function);
      return app;

    case ABSTRACTION:
      Abs funcAbs = (Abs) app.function;

      return replaceAllReferencesToParam(funcAbs.expression, 1, app.argument);

    case NATIVE_FUNC:
      Nat natFunc = (Nat) app.function;

      switch (app.argument.type) {
      case NATIVE_FUNC:
      case REFERENCE:
      case ABSTRACTION:
        throw new IllegalReductionError();

      case APPLICATION:
        app.argument = reduceOnce(app.argument);
        return app;

      case WRAPPER:
        Wrap wrapper = (Wrap) app.argument;

        return convertExpressionToExp(natFunc.nativeFunction
            .apply(wrapper.object));
      }
    }
    throw new AssertionError();
  }

  private static Exp replaceAllReferencesToParam(Exp exp, int id, Exp with) {
    switch (exp.type) {
    case WRAPPER:
    case NATIVE_FUNC:
      return exp;

    case ABSTRACTION:
      Abs abs = (Abs) exp;
      abs.expression =
          replaceAllReferencesToParam(abs.expression, id + 1, with);

      return abs;

    case APPLICATION:
      App app = (App) exp;

      app.function = replaceAllReferencesToParam(app.function, id, with);
      app.argument = replaceAllReferencesToParam(app.argument, id, with);

      return app;

    case REFERENCE:
      Ref ref = (Ref) exp;

      return id == ref.id ? with : ref;
    }
    throw new AssertionError();
  }
}
