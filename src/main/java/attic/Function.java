package attic;
//package languish.lambda.primitive;
//
//import java.util.Arrays;
//
//import languish.lambda.expressions.Abstraction;
//
//public class Function extends LObject {
//
//  private final Abstraction abs;
//
//  private Function(Abstraction abs) {
//    this.abs = abs;
//  }
//
//  public static Function of(Abstraction abs) {
//    return new Function(abs);
//  }
//
//  @Override
//  public String toString() {
//    return "{" + abs + "}";
//  }
//
//  @Override
//  public boolean equals(Object obj) {
//    if (obj == null || !(obj instanceof Function)) {
//      return false;
//    }
//    return abs.equals(((Function) obj).abs);
//  }
//
//  @Override
//  public int hashCode() {
//    return Arrays.hashCode(new Object[] { getClass(), abs });
//  }
//}