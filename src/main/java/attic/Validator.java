//package attic;
//
//import languish.lambda.code.Abstraction;
//import languish.lambda.code.Application;
//import languish.lambda.code.Expression;
//import languish.lambda.code.PrimitiveCall;
//import languish.lambda.code.Reference;
//
//public class Validator {
//  private Validator() {
//
//  }
//
//  public static int getMaximalFreeVariableIndex(Expression exp) {
//    int result;
//
//    switch (exp.getType()) {
//    case ABSTRACTION:
//      Abstraction abs = (Abstraction) exp;
//
//      result = getMaximalFreeVariableIndex(abs.getExpression()) - 1;
//      break;
//    case APPLICATION:
//      Application application = (Application) exp;
//
//      result =
//          Math.max(getMaximalFreeVariableIndex(application.getFunction()),
//              getMaximalFreeVariableIndex(application.getArgument()));
//      break;
//    case LITERAL:
//      result = 0;
//      break;
//    // case BRANCH:
//    // Branch branch = (Branch) exp;
//    //
//    // result = 0;
//    //
//    // result =
//    // Math.max(result, getMaximalFreeVariableIndex(branch.getCondition()));
//    // result = Math.max(result, getMaximalFreeVariableIndex(branch.getThen()));
//    // result = Math.max(result, getMaximalFreeVariableIndex(branch.getElse()));
//    //
//    // break;
//    case PRIM_CALL:
//      PrimitiveCall primCall = (PrimitiveCall) exp;
//
//      result = 0;
//      for (Expression exp2 : primCall.getArgs()) {
//        result = Math.max(result, getMaximalFreeVariableIndex(exp2));
//      }
//      break;
//    case REFERENCE:
//      Reference ref = (Reference) exp;
//
//      result = ref.getIndex();
//      break;
//    default:
//      throw new AssertionError();
//    }
//
//    return result;
//  }
//
//  public static boolean hasFreeVariables(Expression exp) {
//    return getMaximalFreeVariableIndex(exp) > 0;
//  }
//
//}
