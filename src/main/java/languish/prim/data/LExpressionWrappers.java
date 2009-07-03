//package languish.prim.data;
//
//import languish.interpreter.Builtins;
//import languish.lambda.Abstraction;
//import languish.lambda.Application;
//import languish.lambda.Expression;
//import languish.lambda.DataFunction;
//import languish.lambda.Reference;
//import languish.lambda.Data;
//
//public class LExpressionWrappers {
//  private LExpressionWrappers() {}
//
//  public static final DataFunction MK_APP = new DataFunction("MK_APP") {
//    @Override
//    public Expression apply(LObject obj) {
//      final Expression function = (Expression) obj;
//
//      return new DataFunction("MK_APP*") {
//        @Override
//        public Expression apply(LObject obj) {
//          Expression argument = (Expression) obj;
//
//          return Data.of(Application.of(function, argument));
//        }
//      };
//    }
//  };
//
//  public static final DataFunction MK_ABS = new DataFunction("MK_ABS") {
//    @Override
//    public Expression apply(LObject obj) {
//      Expression expression = (Expression) obj;
//
//      return Data.of(Abstraction.of(expression));
//    }
//  };
//
//  public static final DataFunction MK_WRAPPER =
//      new DataFunction("MK_WRAPPER") {
//        @Override
//        public Expression apply(LObject obj) {
//          return Data.of(Data.of(obj));
//        }
//      };
//
//  public static final DataFunction MK_REF = new DataFunction("MK_REF") {
//    @Override
//    public Expression apply(LObject obj) {
//      int index = ((LInteger) obj).intValue();
//
//      return Data.of(Reference.to(index));
//    }
//  };
//
//  public static final DataFunction MK_BUILTIN_GET =
//      new DataFunction("MK_BUILTIN_GET") {
//        @Override
//        public Expression apply(LObject obj) {
//          LSymbol nat = (LSymbol) obj;
//
//          return Data
//              .of(Builtins.valueOf(nat.stringValue()).getExpression());
//        }
//      };
//
//  public static final DataFunction UNWRAP = new DataFunction("UNWRAP") {
//    @Override
//    public Expression apply(LObject obj) {
//      return (Expression) obj;
//    }
//  };
//}
