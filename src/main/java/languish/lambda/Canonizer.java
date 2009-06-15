package languish.lambda;

import languish.lambda.Expression.Type;
import languish.prim.data.LComposite;
import languish.prim.data.LComposites;
import languish.prim.data.LExpressionWrappers;
import languish.prim.data.LInteger;
import languish.prim.data.LObject;
import languish.prim.data.LSymbol;

public class Canonizer {

  public static String getCodeForExpression(Expression exp) {
    switch (exp.getType()) {
    case ABSTRACTION:
      Abstraction abs = (Abstraction) exp;
      return "(ABS " + getCodeForExpression(abs.getExpression()) + ")";

    case APPLICATION:
      Application app = (Application) exp;
      return "(APP " + getCodeForExpression(app.getFunction()) + " "
          + getCodeForExpression(app.getArgument()) + ")";

    case NATIVE_FUNC:
      NativeFunction nat = (NativeFunction) exp;

      if (nat.getName().indexOf('*') != -1) {
        throw new UnsupportedOperationException();
      }

      return "(~" + nat.getName() + "~)";

    case REFERENCE:
      Reference ref = (Reference) exp;

      return "+" + ref.getIndex();

    case WRAPPER:
      Wrapper wrap = (Wrapper) exp;

      Expression gen = getGeneratingExpressionFor(wrap.getContents());

      return gen.getType() != Type.WRAPPER ? getCodeForExpression(gen) : //
          "(!" + ((Wrapper) gen).getContents().toString() + "!)";
    }

    throw new AssertionError();
  }

  public static Expression getGeneratingExpressionFor(LObject obj) {
    if (obj instanceof LComposite) {
      LComposite comp = (LComposite) obj;

      Application result =
          Application
              .of(LComposites.WRAP, Wrapper.of(LInteger.of(comp.size())));

      for (LObject element : comp.getArray()) {
        result = Application.of(result, getGeneratingExpressionFor(element));
      }

      return result;

    } else if (obj instanceof Abstraction) {
      Abstraction abs = (Abstraction) obj;

      return Application.of(LExpressionWrappers.MK_ABS,
          getGeneratingExpressionFor(abs.getExpression()));

    } else if (obj instanceof Application) {
      Application app = (Application) obj;

      return Application.of(Application.of(LExpressionWrappers.MK_APP,
          getGeneratingExpressionFor(app.getFunction())), //
          getGeneratingExpressionFor(app.getArgument()));

    } else if (obj instanceof NativeFunction) {
      NativeFunction nat = (NativeFunction) obj;

      return Application.of(LExpressionWrappers.MK_BUILTIN_GET, Wrapper
          .of(LSymbol.of(nat.getName())));

    } else if (obj instanceof Reference) {
      Reference ref = (Reference) obj;

      return Application.of(LExpressionWrappers.MK_REF, Wrapper.of(LInteger
          .of(ref.getIndex())));

    } else if (obj instanceof Wrapper) {
      Wrapper wrap = (Wrapper) obj;

      return Application.of(LExpressionWrappers.MK_WRAPPER, Canonizer
          .getGeneratingExpressionFor(wrap.getContents()));

    } else {
      return Wrapper.of(obj);
    }
  }
}
