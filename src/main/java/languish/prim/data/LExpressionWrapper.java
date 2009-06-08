package languish.prim.data;

import languish.interpreter.Builtins;
import languish.lambda.Abstraction;
import languish.lambda.Application;
import languish.lambda.Expression;
import languish.lambda.NativeFunction;
import languish.lambda.Reference;
import languish.lambda.Wrapper;

public class LExpressionWrapper extends LObject {

  private final Expression expression;

  LExpressionWrapper(Expression expression) {
    this.expression = expression;
  }

  public Expression getExpression() {
    return expression;
  }

  public static Expression getGeneratingExpressionFor(Expression exp) {
    switch (exp.getType()) {
    case ABSTRACTION:
      Abstraction abs = (Abstraction) exp;

      return Application.of(Builtins.MK_ABS.getExpression(),
          getGeneratingExpressionFor(abs.getExpression()));
    case APPLICATION:
      Application app = (Application) exp;

      return Application.of(Application.of(LExpressionWrappers.MK_APP,
          getGeneratingExpressionFor(app.getFunction())), //
          getGeneratingExpressionFor(app.getArgument()));
    case NATIVE_FUNC:
      NativeFunction nat = (NativeFunction) exp;

      if (!nat.isCanonizable()) {
        throw new UnsupportedOperationException();
      }

      return Application.of(LExpressionWrappers.MK_NAT, Wrapper.of(LSymbol
          .of(nat.getName())));
    case REFERENCE:
      Reference ref = (Reference) exp;

      return Application.of(LExpressionWrappers.MK_REF, Wrapper.of(LInteger
          .of(ref.getIndex())));
    case WRAPPER:
      Wrapper wrap = (Wrapper) exp;

      return Application.of(LExpressionWrappers.MK_WRAPPER, wrap.getContents()
          .getGeneratingExpression());

    default:
      throw new AssertionError();
    }
  }

  @Override
  public Expression getGeneratingExpression() {
    return getGeneratingExpressionFor(expression);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result + ((expression == null) ? 0 : expression.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    LExpressionWrapper other = (LExpressionWrapper) obj;
    if (expression == null) {
      if (other.expression != null) {
        return false;
      }
    } else if (!expression.equals(other.expression)) {
      return false;
    }
    return true;
  }

}
