package languish.testing;

import languish.interpreter.Builtins;
import languish.lambda.LObject;
import languish.lambda.Term;
import languish.primitives.LInteger;
import languish.primitives.LSymbol;

public class Canonizer {

  public static String getCodeForExpression(LObject exp) {
    for (Builtins b : Builtins.values()) {
      if (b.getExpression().equals(exp)) {
        return b.name();
      }
    }

    if (exp instanceof Term) {
      Term tuple = (Term) exp;

      StringBuilder sb = new StringBuilder();

      sb.append('[');

      if (tuple.size() > 0) {
        sb.append(getCodeForExpression(tuple.getFirst()));
      }

      for (int i = 1; i < tuple.size(); i++) {
        sb.append(' ') //
            .append(getCodeForExpression(tuple.get(i)));
      }

      sb.append(']');

      return sb.toString();
    } else if (exp instanceof LInteger) {
      return "" + ((LInteger) exp).intValue();
    } else if (exp instanceof LSymbol) {
      return "\"" + ((LSymbol) exp).stringValue() + "\"";
    }

    return "<UNKNOWN>";
  }
  //
  // public static Expression getGeneratingExpressionFor(LObject obj) {
  // if (obj instanceof LComposite) {
  // LComposite comp = (LComposite) obj;
  //
  // Application result =
  // Application
  // .of(LComposites.WRAP, Wrapper.of(LInteger.of(comp.size())));
  //
  // for (LObject element : comp.getArray()) {
  // result = Application.of(result, getGeneratingExpressionFor(element));
  // }
  //
  // return result;
  //
  // } else if (obj instanceof LMap) {
  // Map<LSymbol, LObject> map = ((LMap) obj).getMap();
  //
  // Expression result = Wrapper.of(LMaps.EMPTY_MAP);
  //
  // for (LSymbol key : map.keySet()) {
  // result =
  // Application.of(Application.of(
  // Application.of(LMaps.PUT_MAP, result),
  // getGeneratingExpressionFor(key)), //
  // getGeneratingExpressionFor(map.get(key)));
  // }
  //
  // return result;
  //
  // } else if (obj instanceof Abstraction) {
  // Abstraction abs = (Abstraction) obj;
  //
  // return Application.of(LExpressionWrappers.MK_ABS,
  // getGeneratingExpressionFor(abs.getExpression()));
  //
  // } else if (obj instanceof Application) {
  // Application app = (Application) obj;
  //
  // return Application.of(Application.of(LExpressionWrappers.MK_APP,
  // getGeneratingExpressionFor(app.getFunction())), //
  // getGeneratingExpressionFor(app.getArgument()));
  //
  // } else if (obj instanceof NativeFunction) {
  // NativeFunction nat = (NativeFunction) obj;
  //
  // return Application.of(LExpressionWrappers.MK_BUILTIN_GET, Wrapper
  // .of(LSymbol.of(nat.getName())));
  //
  // } else if (obj instanceof Reference) {
  // Reference ref = (Reference) obj;
  //
  // return Application.of(LExpressionWrappers.MK_REF, Wrapper.of(LInteger
  // .of(ref.getIndex())));
  //
  // } else if (obj instanceof Wrapper) {
  // Wrapper wrap = (Wrapper) obj;
  //
  // return Application.of(LExpressionWrappers.MK_WRAPPER, Canonizer
  // .getGeneratingExpressionFor(wrap.getContents()));
  //
  // } else {
  // return Wrapper.of(obj);
  // }
  // }
}
