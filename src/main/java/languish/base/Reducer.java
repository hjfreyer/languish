package languish.base;


public class Reducer {
  // private final DependencyManager depman;
  //
  // public Reducer() {
  // this(null);
  // }
  //
  // public Reducer(DependencyManager depman) {
  // this.depman = depman;
  // }
  //
  // public Tuple reduce(Tuple tuple) {
  // tuple = tuple.deepClone();
  //
  // while (!isPrimitive(tuple)) {
  // tuple = reduceTupleOnce(tuple);
  // }
  //
  // return tuple;
  // }
  //
  // private Tuple reduceTupleOnce(Tuple tuple) {
  // Tuple t = tuple;
  // Operation op = (Operation) t.getFirst();
  //
  // if (op == Lambda.ABS || op == Lambda.DATA) {
  // throw new AlreadyReducedError(tuple);
  // } else if (op == Lambda.APP) {
  // Tuple func = (Tuple) tuple.getSecond();
  // Tuple arg = (Tuple) tuple.getThird();
  //
  // if (func.getFirst() != Lambda.ABS) {
  // tuple.setSecond(reduceTupleOnce(func));
  // return tuple;
  // }
  //
  // return replaceAllReferencesToParam((Tuple) func.getSecond(), 1, arg);
  // } else if (op == Lambda.CAR) {
  // Tuple arg = (Tuple) tuple.getSecond();
  //
  // if (arg.getFirst() != Lambda.CONS) {
  // tuple.setSecond(reduceTupleOnce(arg));
  // return tuple;
  // }
  //
  // return (Tuple) arg.getSecond();
  // } else if (op == Lambda.CDR) {
  // Tuple arg = (Tuple) tuple.getSecond();
  //
  // if (arg.getFirst() != Lambda.CONS) {
  // tuple.setSecond(reduceTupleOnce(arg));
  // return tuple;
  // }
  //
  // return (Tuple) arg.getThird();
  // } else if (op == Lambda.CONS) {
  // Tuple car = (Tuple) tuple.getSecond();
  // Tuple cdr = (Tuple) tuple.getThird();
  //
  // if (isReducible(car)) {
  // tuple.setSecond(reduceTupleOnce(car));
  // return tuple;
  // } else if (isReducible(cdr)) {
  // tuple.setThird(reduceTupleOnce(cdr));
  // return tuple;
  // } else {
  // throw new AlreadyReducedError(tuple);
  // }
  // } else if (op == Lambda.IS_PRIMITIVE) {
  // Tuple arg = (Tuple) tuple.getSecond();
  //
  // if (isReducible(arg)) {
  // tuple.setSecond(reduceTupleOnce(arg));
  // return tuple;
  // }
  //
  // return Lambda.data(isPrimitive(arg) ? LBoolean.TRUE : LBoolean.FALSE);
  // } else if (op == Lambda.PRIM) {
  // Tuple func = (Tuple) tuple.getSecond();
  // Tuple arg = (Tuple) tuple.getThird();
  //
  // if (func.getFirst() != Lambda.DATA) {
  // tuple.setSecond(reduceTupleOnce(func));
  // return tuple;
  // }
  // PrimitiveFunction primFunc = (PrimitiveFunction) func.getSecond();
  //
  // if (isReducible(arg)) {
  // tuple.setThird(reduceTupleOnce(arg));
  // return tuple;
  // } else if (isPrimitive(arg)) {
  // return primFunc.applyWithCopy(arg);
  // } else {
  // throw new IllegalPrimitiveFunctionApplicationError(arg);
  // }
  // } else if (op == Lambda.REF) {
  // throw new IllegalReductionError();
  // }
  //
  // throw new AssertionError();
  // }
  //
  // public static boolean isReducible(Tuple tuple) {
  // Operation op = (Operation) tuple.getFirst();
  //
  // if (op == Lambda.APP || op == Lambda.CAR || op == Lambda.CDR
  // || op == Lambda.PRIM || op == Lambda.IS_PRIMITIVE) {
  // return true;
  // } else if (op == Lambda.DATA || op == Lambda.REF || op == Lambda.ABS) {
  // return false;
  // } else if (op == Lambda.CONS) {
  // return isReducible((Tuple) tuple.getSecond())
  // || isReducible((Tuple) tuple.getThird());
  // } else {
  // throw new AssertionError();
  // }
  // }
  //
  // // Is the function reduced all the way, with only CONS and DATA left?
  // public static boolean isPrimitive(Tuple tuple) {
  // Operation op = (Operation) tuple.getFirst();
  //
  // if (op == Lambda.DATA) {
  // return true;
  // } else if (op == Lambda.CONS) {
  // return isPrimitive((Tuple) tuple.getSecond())
  // && isPrimitive((Tuple) tuple.getThird());
  // } else if (op == Lambda.REF || op == Lambda.ABS || op == Lambda.CAR
  // || op == Lambda.CDR || op == Lambda.APP || op == Lambda.PRIM
  // || op == Lambda.IS_PRIMITIVE) {
  // return false;
  // } else {
  // throw new AssertionError();
  // }
  // }
  //
  // private static Tuple replaceAllReferencesToParam(Tuple exp, int id, Tuple
  // with) {
  // Operation op = (Operation) exp.getFirst();
  //
  // if (op == Lambda.DATA) {
  // return exp;
  // }
  // if (op == Lambda.REF) {
  // return id == ((LInteger) exp.getSecond()).intValue()
  // ? with.deepClone()
  // : exp;
  // }
  // if (op == Lambda.ABS) {
  // exp.setSecond(replaceAllReferencesToParam((Tuple) exp.getSecond(),
  // id + 1, with));
  // return exp;
  // }
  // if (op == Lambda.CONS || op == Lambda.APP || op == Lambda.PRIM) {
  // exp.setSecond( //
  // replaceAllReferencesToParam((Tuple) exp.getSecond(), id, with));
  // exp.setThird( //
  // replaceAllReferencesToParam((Tuple) exp.getThird(), id, with));
  // return exp;
  // }
  // if (op == Lambda.CAR || op == Lambda.CDR || op == Lambda.IS_PRIMITIVE) {
  // exp.setSecond( //
  // replaceAllReferencesToParam((Tuple) exp.getSecond(), id, with));
  // return exp;
  // }
  // throw new AssertionError();
  // }
}
