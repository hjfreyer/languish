package languish.prim.functions;


public class ExpressionOps {
  //
  // private ExpressionOps() {
  //
  // }
  //
  // public static final BuiltinFunction MK_ABS =
  // new SingleArgumentDataFunction() {
  // @Override
  // public LObject apply(LObject arg) {
  // return Abstraction.of((Expression) arg);
  // }
  //
  // @Override
  // public String getName() {
  // return "MK_ABS";
  // }
  // };
  //
  // public static final BuiltinFunction MK_APP = new TwoArgumentDataFunction()
  // {
  // @Override
  // public LObject apply(LObject arg1, LObject arg2) {
  // return Application.of((Expression) arg1, (Expression) arg2);
  // }
  //
  // @Override
  // public String getName() {
  // return "MK_APP";
  // }
  // };
  //
  // public static final BuiltinFunction MK_LITERAL = new BuiltinFunction() {
  // @Override
  // public LObject apply(LObject[] args) {
  // return Wrapper.of(args[0]);
  // }
  //
  // @Override
  // public String getName() {
  // return "MK_LITERAL";
  // }
  // };
  //
  // public static final BuiltinFunction MK_BRANCH = new BuiltinFunction() {
  // @Override
  // public LObject apply(LObject[] args) {
  // Expression[] cases = new Expression[args.length - 1];
  //
  // for (int i = 0; i < cases.length; i++) {
  // cases[i] = (Expression) args[i + 1];
  // }
  //
  // return Branch.of((Expression) args[0], cases);
  // }
  //
  // @Override
  // public String getName() {
  // return "MK_BRANCH";
  // }
  // };
  //
  // public static final BuiltinFunction MK_BUILTIN = new BuiltinFunction() {
  // @Override
  // public LObject apply(LObject[] args) {
  // Expression[] allArgs = new Expression[args.length - 1];
  //
  // for (int i = 0; i < allArgs.length; i++) {
  // allArgs[i] = (Expression) args[i + 1];
  // }
  //
  // return BuiltinCall.of(Wrapper.of(args[0]), allArgs);
  // }
  //
  // @Override
  // public String getName() {
  // return "MK_BUILTIN";
  // }
  // };
  //
  // public static final BuiltinFunction MK_REF =
  // new SingleArgumentDataFunction() {
  // @Override
  // public LObject apply(LObject arg) {
  // return Reference.to(((LInteger) arg).intValue());
  // }
  //
  // @Override
  // public String getName() {
  // return "MK_REF";
  // }
  // };
  //
  // public static final BuiltinFunction MK_UNWRAP =
  // new SingleArgumentDataFunction() {
  // @Override
  // public LObject apply(LObject arg) {
  // return Evalulate.of((Expression) arg);
  // }
  //
  // @Override
  // public String getName() {
  // return "MK_UNWRAP";
  // }
  // };
  //
  // // public static final DataFunction UNWRAP = new DataFunction() {
  // // @Override
  // // public Expression apply(Expression[] args) {
  // // LObject content = ((DataBlob) args[0]).getObject();
  // //
  // // return (Expression) content;
  // // }
  // //
  // // @Override
  // // public String getName() {
  // // return "UNWRAP";
  // // }
  // // };
  // // //
  // // public static DataFunction oneArgCall(final String name,
  // // final Expression exp) {
  // // return new DataFunction() {
  // // @Override
  // // public LObject apply(LObject[] args) {
  // // return Application.of(exp, Blob.of(args[0]));
  // // }
  // //
  // // @Override
  // // public String getName() {
  // // return name;
  // // }
  // // };
  // // }
}
