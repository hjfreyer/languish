//package languish.prim.data;
//
//
//import languish.lambda.Application;
//import languish.lambda.Expression;
//
//import org.quenta.tedir.hadrian.INode;
//
//public class LNodeConverters {
//  public static class ExpressionBasedNodeConverter implements NodeConverter {
//    private final Expression function;
//
//    public ExpressionBasedNodeConverter(Expression function) {
//      this.function = function;
//    }
//
//    public Expression convert(INode node) {
//      return Application.of(function, convertNode(node));
//    }
//
//    @Override
//    public String toString() {
//      return "<NodeConverter: " + function + ">";
//    }
//  }
//
//  public static interface NodeConverter {
//    Expression convert(INode node);
//  }
//
//  private LNodeConverters() {}
//
//}
