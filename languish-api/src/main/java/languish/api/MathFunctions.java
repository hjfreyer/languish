package languish.api;

import java.util.Map;

import languish.base.NativeFunction;
import languish.base.Primitive;
import languish.util.PrimitiveTree;

import com.google.common.collect.ImmutableMap;
import com.hjfreyer.util.Tree;

public class MathFunctions {

  // BOOLEAN STUFF
  public static final NativeFunction AND = new TwoArgumentNativeFunction() {
    @Override
    public Tree<Primitive> apply(Primitive arg1, Primitive arg2) {
      return Tree.leaf(new Primitive(arg1.asBoolean()
      && arg2.asBoolean()));
    }
  };

  public static final NativeFunction NOT = new SingleArgumentNativeFunction() {
    @Override
    public Tree<Primitive> apply(Primitive arg1) {
      return Tree.leaf(new Primitive(!arg1.asBoolean()));
    }
  };

  // INTEGER STUFF
  public static final NativeFunction ADD = new TwoArgumentNativeFunction() {
    @Override
    public Tree<Primitive> apply(Primitive arg1, Primitive arg2) {
      return Tree.leaf(new Primitive(arg1.asInteger() + arg2.asInteger()));
    }
  };

  public static final NativeFunction MULTIPLY =
      new TwoArgumentNativeFunction() {
        @Override
        public Tree<Primitive> apply(Primitive arg1, Primitive arg2) {
          return Tree.leaf(new Primitive(arg1.asInteger()
          * arg2.asInteger()));
        }
      };

  // PARSE LITERALS
  public static final NativeFunction PARSE_INT =
      new SingleArgumentNativeFunction() {
        @Override
        public Tree<Primitive> apply(Primitive obj) {

          return Tree.leaf(new Primitive(Integer
          .parseInt(obj.asString())));
        }
      };

  public static final Map<String, ? extends NativeFunction> FUNCTION_MAP =
      ImmutableMap.<String, NativeFunction> builder().put("math/add", ADD).put(
          "math/multiply",
          MULTIPLY).build();

  // public static final NativeFunction DATA_EQUALS = new NativeFunction() {
  // @Override
  // public Term apply(Term tuple) {
  // if (tuple.getFirst() != languish.util.CONS) {
  // throw new IllegalArgumentException("argument must be cons");
  // }
  //
  // Term arg1 = (Term) tuple.getSecond();
  // Term arg2 = (Term) tuple.getThird();
  //
  // return Lambda.primitive(LBoolean.of(arg1.equals(arg2)));
  // }
  // };
  //
  // public static final NativeFunction BUILTIN_GET =
  // new SingleArgumentNativeFunction() {
  // @Override
  // public Term apply(LObject arg) {
  // LSymbol symbol = (LSymbol) arg;
  //
  // return Lambda.primitive(Builtins.valueOf(symbol.stringValue())
  // .getExpression());
  // }
  // };
  //
  // // DEBUGGING
  //
  // public static final NativeFunction PRINT = new NativeFunction() {
  // @Override
  // public Term apply(Term arg) {
  // System.out.println(arg);
  // return arg;
  // }
  // };

  // public static final PrimitiveFunction IS_NULL = new PrimitiveFunction() {
  // @Override
  // public Tuple apply(Tuple tuple) {
  // boolean isNull =
  // tuple.getFirst() == Lambda.DATA
  // && tuple.getSecond().equals(Tuple.of());
  //
  // return Lambda.data(LBoolean.of(isNull));
  // }
  // };

  private MathFunctions() {
  }
}
