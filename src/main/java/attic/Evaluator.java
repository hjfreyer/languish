//package attic;
//
//import languish.lambda.code.Abstraction;
//import languish.lambda.code.Application;
//import languish.lambda.code.DataBlob;
//import languish.lambda.code.Expression;
//import languish.lambda.code.PrimitiveCall;
//import languish.lambda.code.ReducedExpression;
//import languish.lambda.code.Reference;
//import languish.lambda.code.Expression.Type;
//import languish.lambda.eval.IllegalApplicationError;
//import languish.lambda.eval.IllegalFreeVariableError;
//import languish.lambda.eval.MaxIterationsExceededError;
//
///**
// * @author hjfreyer
// * 
// */
//public class Evaluator {
//
//  public static ReducedExpression reduce(Expression exp) {
//    return reduceCompletely(exp, -1);
//
//  }
//
//  public static ReducedExpression reduce(Expression exp, int max_loops) {
//    return reduceCompletely(exp, max_loops);
//  }
//
//  public static Abstraction reduceToAbstraction(Expression exp) {
//    ReducedExpression reducedExp = reduce(exp);
//
//    if (reducedExp.getType() != Type.ABSTRACTION) {
//      throw new IllegalApplicationError(
//          "Expression cannot be reduced to Abstraction: " + exp);
//    }
//
//    return (Abstraction) reducedExp;
//  }
//
//  public static DataBlob reduceToLiteral(Expression exp) {
//    ReducedExpression reducedExp = reduce(exp);
//
//    if (reducedExp.getType() != Type.LITERAL) {
//      throw new IllegalApplicationError(
//          "Expression cannot be reduced to Literal: " + exp);
//    }
//
//    return (DataBlob) reducedExp;
//  }
//
//  private static ReducedExpression reduceCompletely(Expression exp,
//      int max_loops) {
//    if (Validator.hasFreeVariables(exp)) {
//      throw new IllegalFreeVariableError(
//          "Expression with free variables cannot be reduced: " + exp);
//    }
//
//    for (int count = 0; max_loops == -1 || count < max_loops; count++) {
//      switch (exp.getType()) {
//      case ABSTRACTION:
//        return (Abstraction) exp;
//        // return Literal.of(exp);
//      case LITERAL:
//        return (DataBlob) exp;
//      case REFERENCE:
//        throw new AssertionError("I thought there were no free variables!!");
//      case PRIM_CALL:
//        PrimitiveCall primCall = (PrimitiveCall) exp;
//
//        exp = doApplyPrimitive(primCall);
//        break;
//      // return doApplyPrimitive(primCall);
//      // case BRANCH:
//      // Branch branch = (Branch) exp;
//      //        
//      // LObject condition = reduce(branch.getCondition()).getObject();
//      // if (!(condition instanceof LBoolean)) {
//      // throw new IllegalBranchConditionError(
//      // "Branch condition does not evaluate to boolean!");
//      // }
//      //
//      // return ((LBoolean) condition).booleanValue() ? reduce(branch.getThen())
//      // : reduce(branch.getElse());
//      case APPLICATION:
//        Application app = (Application) exp;
//
//        Abstraction function = reduceToAbstraction(app.getFunction());
//        exp = doApply(function, app.getArgument());
//        break;
//      default:
//        throw new AssertionError();
//      }
//    }
//
//    throw new MaxIterationsExceededError();
//  }
//
//  /**
//   * Preconditions:
//   * 
//   * - exp has no free variables
//   * 
//   * Post conditions:
//   * 
//   * - if exp is a terminating expression, returns a Literal or Abstraction
//   * 
//   * @param exp
//   * @return
//   */
//
//  private static Expression doApplyPrimitive(PrimitiveCall primCall) {
//    return primCall.getOp().apply(primCall.getArgs());
//  }
//
//  /**
//   * Precondition:
//   * 
//   * - exp has no free variables
//   * 
//   * @param exp
//   * @return
//   */
//
//  private static Expression doApply(Abstraction func, Expression argument) {
//    Expression child = func.getExpression();
//
//    return replaceAllReferencesToParam(child, 1, argument);
//  }
//
//  static Expression replaceAllReferencesToParam(Expression in, int id,
//      Expression with) {
//
//    switch (in.getType()) {
//    case ABSTRACTION:
//      Abstraction abs = (Abstraction) in;
//
//      Expression child =
//          replaceAllReferencesToParam(abs.getExpression(), id + 1, with);
//
//      return Abstraction.of(child);
//    case APPLICATION:
//      Application app = (Application) in;
//
//      Expression function =
//          replaceAllReferencesToParam(app.getFunction(), id, with);
//      Expression argument =
//          replaceAllReferencesToParam(app.getArgument(), id, with);
//
//      return Application.of(function, argument);
//    case REFERENCE:
//      Reference r = (Reference) in;
//
//      return (r.getIndex() == id) ? with : r;
//
//    case PRIM_CALL:
//      PrimitiveCall prim = (PrimitiveCall) in;
//
//      Expression[] newExps = new Expression[prim.getArgs().length];
//
//      for (int i = 0; i < prim.getArgs().length; i++) {
//        newExps[i] = replaceAllReferencesToParam(prim.getArgs()[i], id, with);
//      }
//
//      return PrimitiveCall.of(prim.getOp(), newExps);
//      // } else if (in instanceof Branch) {
//      // Branch branch = (Branch) in;
//      //
//      // Expression condition = replace(branch.getCondition(), id, with);
//      // Expression then = replace(branch.getThen(), id, with);
//      // Expression els = replace(branch.getElse(), id, with);
//      //
//      // return new Branch(condition, then, els);
//    case LITERAL:
//      return in;
//    default:
//      throw new AssertionError();
//    }
//  }
//
//}