package attic;
//package languish.lambda.eval;
//
//import languish.lambda.code.Abstraction;
//import languish.lambda.code.Application;
//import languish.lambda.code.Expression;
//import languish.lambda.code.NativeFunction;
//import languish.lambda.code.Reference;
//import languish.lambda.code.Wrapper;
//import languish.prim.data.LObject;
//
//public class Reducer {
//  //
//  // public static final PrimitiveFunction REDUCE = new PrimitiveFunction() {
//  // @Override
//  // public Expression apply(Expression[] args) {
//  //
//  // }
//  //
//  // @Override
//  // public String getName() {
//  // // TODO Auto-generated method stub
//  // return null;
//  // }
//  // };
//
//  public static LObject evaluate(Expression exp) {
//
//  }
//
//  public static Wrapper reduceCompletely(Expression exp) {
//    while (true) {
//      switch (exp.getType()) {
//      case APPLICATION:
//        // case BRANCH:
//        // case PRIM_CALL:
//        // case EVAL:
//        exp = reduceOnce(exp);
//        break;
//
//      case NATIVE_FUNC:
//      case ABSTRACTION:
//        return ((Abstraction) exp).asLAbstraction();
//
//      case WRAPPER:
//        return (Wrapper) exp;
//
//      case REFERENCE:
//        throw new IllegalFreeVariableError(exp + " cannot be reduced");
//      }
//    }
//  }
//
//  public static Expression reduceOnce(Expression exp) {
//    switch (exp.getType()) {
//    case ABSTRACTION:
//    case NATIVE_FUNC:
//    case WRAPPER:
//      return exp;
//
//    case APPLICATION:
//      return reduceApplicationOnce((Application) exp);
//      //
//      // case BRANCH:
//      // return reduceBranchOnce((Branch) exp);
//      //
//      // case PRIM_CALL:
//      // return reducePrimCallOnce((BuiltinCall) exp);
//      //
//      // case EVAL:
//      // return reduceUnwrapOnce((Evalulate) exp);
//
//    case REFERENCE:
//      throw new IllegalFreeVariableError();
//
//    default:
//      throw new AssertionError();
//    }
//  }
//
//  private static Expression reduceApplicationOnce(Application app)
//      throws AssertionError {
//    Expression function = app.getFunction();
//    Expression argument = app.getArgument();
//
//    switch (function.getType()) {
//    case WRAPPER:
//    case REFERENCE:
//      throw new IllegalApplicationError(function
//          + " cannot be reduced to abstraction");
//
//    case APPLICATION:
//      // case BRANCH:
//      // case PRIM_CALL:
//      // case EVAL:
//
//      return Application.of(reduceOnce(function), argument);
//
//    case ABSTRACTION:
//      Abstraction funcAbs = (Abstraction) function;
//
//      return replaceAllReferencesToParam(funcAbs.getExpression(), 1, argument);
//
//    case NATIVE_FUNC:
//      NativeFunction call = (NativeFunction) function;
//
//      switch (argument.getType()) {
//      case APPLICATION:
//      case NATIVE_FUNC:
//        return Application.of(call, reduceOnce(argument));
//
//      case REFERENCE:
//        throw new IllegalFreeVariableError();
//
//      case ABSTRACTION:
//      case WRAPPER:
//        return call.apply(app.getArgument());
//      default:
//        throw new AssertionError();
//      }
//    default:
//      throw new AssertionError();
//    }
//  }
//
//  //
//  // private static Expression reduceBranchOnce(Branch b) {
//  // Expression selector = b.getSelector();
//  //
//  // switch (selector.getType()) {
//  // case ABSTRACTION:
//  // case REFERENCE:
//  // throw new IllegalBranchConditionError(selector
//  // + " cannot be reduced to integer");
//  //
//  // case APPLICATION:
//  // case PRIM_CALL:
//  // case BRANCH:
//  // case EVAL:
//  // return Branch.of(reduceOnce(selector), b.getCases());
//  //
//  // case DATA_BLOB:
//  // LInteger sel = (LInteger) ((DataBlob) selector).getObject();
//  //
//  // return b.getCases()[sel.intValue()];
//  //
//  // default:
//  // throw new AssertionError();
//  // }
//  // }
//  //
//  // private static Expression reducePrimCallOnce(BuiltinCall call) {
//  // // First reduce to resolve the operation name
//  // Expression opNameExp = call.getOpName();
//  // Expression[] argExps = call.getArgs();
//  //
//  // switch (opNameExp.getType()) {
//  // case ABSTRACTION:
//  // case REFERENCE:
//  // throw new IllegalPrimitiveCallArgumentError(opNameExp
//  // + " cannot be reduced to blob");
//  //
//  // case APPLICATION:
//  // case BRANCH:
//  // case EVAL:
//  // case PRIM_CALL:
//  // return BuiltinCall.of(reduceOnce(opNameExp), argExps);
//  //
//  // case DATA_BLOB:
//  // break;
//  // default:
//  // throw new AssertionError();
//  // }
//  //
//  // LSymbol opName = (LSymbol) ((DataBlob) opNameExp).getObject();
//  //
//  // // reduce the arguments
//  // Expression[] args = new Expression[argExps.length];
//  //
//  // boolean allLiterals = true;
//  // for (int i = 0; i < args.length; i++) {
//  // Expression arg = argExps[i];
//  //
//  // args[i] = arg;
//  //
//  // if (allLiterals) {
//  // switch (arg.getType()) {
//  // case ABSTRACTION:
//  // case REFERENCE:
//  // throw new IllegalPrimitiveCallArgumentError(arg
//  // + " cannot be reduced to blob");
//  //
//  // case APPLICATION:
//  // case BRANCH:
//  // case PRIM_CALL:
//  // case EVAL:
//  // allLiterals = false;
//  //
//  // args[i] = reduceOnce(arg);
//  // break;
//  //
//  // case DATA_BLOB:
//  // break;
//  //
//  // default:
//  // throw new AssertionError();
//  // }
//  // }
//  // }
//  //
//  // if (!allLiterals) {
//  // return BuiltinCall.of(call.getOpName(), args);
//  // }
//  //
//  // LObject[] objs = new LObject[args.length];
//  //
//  // for (int i = 0; i < objs.length; i++) {
//  // objs[i] = ((DataBlob) args[i]).getObject();
//  // }
//  //
//  // BuiltinFunction function = Builtins.valueOf(opName.stringValue()).getOp();
//  //
//  // return DataBlob.of(function.apply(objs));
//  // }
//  //
//  // private static Expression reduceUnwrapOnce(Evalulate unwrap) {
//  // Expression child = unwrap.getExpression();
//  //
//  // switch (child.getType()) {
//  // case ABSTRACTION:
//  // case REFERENCE:
//  // throw new IllegalPrimitiveCallArgumentError(child
//  // + " cannot be reduced to blob");
//  //
//  // case APPLICATION:
//  // case BRANCH:
//  // case PRIM_CALL:
//  // case EVAL:
//  // return Evalulate.of(reduceOnce(child));
//  //
//  // case DATA_BLOB:
//  // return (Expression) ((DataBlob) child).getObject();
//  //
//  // default:
//  // throw new AssertionError();
//  // }
//  //
//  // }
//
//  private static Expression replaceAllReferencesToParam(Expression in, int id,
//      Expression with) {
//    switch (in.getType()) {
//    case ABSTRACTION:
//      Abstraction abs = (Abstraction) in;
//
//      Expression child =
//          replaceAllReferencesToParam(abs.getExpression(), id + 1, with);
//
//      return Abstraction.of(child);
//
//    case APPLICATION:
//      Application app = (Application) in;
//
//      Expression function =
//          replaceAllReferencesToParam(app.getFunction(), id, with);
//      Expression argument =
//          replaceAllReferencesToParam(app.getArgument(), id, with);
//
//      return Application.of(function, argument);
//
//    case REFERENCE:
//      Reference ref = (Reference) in;
//
//      return (ref.getIndex() == id) ? with : ref;
//      //
//      // case PRIM_CALL:
//      // BuiltinCall prim = (BuiltinCall) in;
//      //
//      // Expression[] newExps = new Expression[prim.getArgs().length];
//      //
//      // for (int i = 0; i < prim.getArgs().length; i++) {
//      // newExps[i] = replaceAllReferencesToParam(prim.getArgs()[i], id, with);
//      // }
//      //
//      // return BuiltinCall.of(prim.getOpName(), newExps);
//      //
//      // case BRANCH:
//      // Branch branch = (Branch) in;
//      //
//      // Expression selector =
//      // replaceAllReferencesToParam(branch.getSelector(), id, with);
//      //
//      // Expression[] cases = new Expression[branch.getCases().length];
//      //
//      // for (int i = 0; i < cases.length; i++) {
//      // cases[i] = replaceAllReferencesToParam(branch.getCases()[i], id, with);
//      // }
//      //
//      // return Branch.of(selector, cases);
//      //
//      // case EVAL:
//      // Evalulate unwrap = (Evalulate) in;
//      //
//      // return Evalulate.of(replaceAllReferencesToParam(unwrap.getExpression(),
//      // id, with));
//
//    case NATIVE_FUNC:
//      return in;
//
//    case WRAPPER:
//      return in;
//
//    default:
//      throw new AssertionError();
//    }
//  }
//}
