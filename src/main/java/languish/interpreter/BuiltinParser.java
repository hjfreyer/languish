package languish.interpreter;

import languish.lambda.Abstraction;
import languish.lambda.Application;
import languish.lambda.Expression;
import languish.lambda.Reducer;
import languish.lambda.Reference;
import languish.lambda.Wrapper;
import languish.prim.data.LBoolean;
import languish.prim.data.LInteger;
import languish.prim.data.LObject;
import languish.prim.data.LSymbol;

import org.quenta.tedir.antonius.doc.ITextDocument;
import org.quenta.tedir.antonius.doc.ResourceDocument;
import org.quenta.tedir.antonius.doc.StringDocument;
import org.quenta.tedir.hadrian.HadrianParser;
import org.quenta.tedir.hadrian.HadrianReader;
import org.quenta.tedir.hadrian.INode;
import org.quenta.tedir.hadrian.MessageMonitor;

import com.hjfreyer.util.Pair;

public class BuiltinParser extends Parser {

  public static enum Literal {
    INT,
    BOOL,
    SYMBOL
  }

  private static enum PrimitiveHadrianParser {
    INSTANCE;

    HadrianParser parser = null;

    private PrimitiveHadrianParser() {
      String docPath = "hadrian/parser/BuiltinGrammar.tg";
      ClassLoader classLoader = BuiltinParser.class.getClassLoader();

      final ITextDocument grammarDoc =
          new ResourceDocument(docPath, classLoader);

      final MessageMonitor monitor = new MessageMonitor();
      this.parser = HadrianReader.getDefault().readParser(grammarDoc, monitor);
      IOUtil.failWithInternalMessages(monitor.getMessages(), grammarDoc);
    }
  }

  public BuiltinParser() {

  }

  @Override
  public Statement parseStatement(String statement, LObject env) {

    Pair<Statement.Type, Expression> exp =
        parseStatementToExpression(statement);

    return new Statement(exp.getFirst(), Reducer.reduce(exp.getSecond()));
  }

  public Pair<Statement.Type, Expression> parseStatementToExpression(
      String statement) {
    ITextDocument doc = new StringDocument(statement);

    INode node = getHadrianParser().parse(doc);

    // Unwrap statement
    Statement.Type type = Statement.Type.valueOf(node.getTag().toString());
    node = node.getChildren().get(0);

    Expression exp = expressionFromINode(node);

    return Pair.of(type, exp);
  }

  public static HadrianParser getHadrianParser() {
    return PrimitiveHadrianParser.INSTANCE.parser;
  }

  private static Expression expressionFromINode(INode inode) {
    if (inode.getTag().equals("LITERAL")) {
      return literalFromINode(inode.getChildren().get(0));
    } else if (inode.getTag().equals("APPLICATION")) {
      return applicationFromINode(inode.getChildren().get(0));
    } else if (inode.getTag().equals("ABSTRACTION")) {
      return abstractionFromINode(inode.getChildren().get(0));
    } else if (inode.getTag().equals("PRIM_GET")) {
      return primGetFromINode(inode.getChildren().get(0));
    } else if (inode.getTag().equals("REFERENCE")) {
      return referenceFromINode(inode.getChildren().get(0));
    } else {
      throw new AssertionError();
    }
  }

  private static Expression referenceFromINode(INode node) {
    int value = Integer.parseInt(node.asString());

    return Reference.to(value);
  }

  private static Expression primGetFromINode(INode node) {
    String name = node.asString();

    return Builtins.valueOf(name).getExpression();
  }

  private static Expression abstractionFromINode(INode node) {
    return Abstraction.of(expressionFromINode(node));
  }

  private static Expression applicationFromINode(INode node) {
    Expression function = expressionFromINode(node.getChildren().get(0));
    Expression argument = expressionFromINode(node.getChildren().get(1));

    return Application.of(function, argument);
  }

  //
  // private static BuiltinCall primCallFromINode(INode inode) {
  // String primName = inode.getChildren().get(0).asString();
  //
  // INode argsNode = inode.getChildren().get(1);
  //
  // Expression[] args = new Expression[argsNode.getChildren().size()];
  //
  // for (int i = 0; i < args.length; i++) {
  // args[i] = expressionFromINode(argsNode.getChildren().get(i));
  // }
  //
  // return BuiltinCall.of(Wrapper.of(LSymbol.of(primName)), args);
  // }

  //
  // private static Evalulate unwrapFromINode(INode inode) {
  // return Evalulate.of(expressionFromINode(inode));
  // }

  private static Wrapper literalFromINode(INode inode) {
    String type = inode.getTag().toString();

    return parseLiteral(Literal.valueOf(type), inode.getChildren().get(0));
  }

  private static Wrapper parseLiteral(Literal type, INode content) {
    String strVal;

    switch (type) {
    case INT:
      strVal = content.asString();

      return Wrapper.of(LInteger.of(Integer.parseInt(strVal)));

    case BOOL:
      strVal = content.getTag().toString();

      if (strVal.equals("TRUE")) {
        return Wrapper.of(LBoolean.TRUE);
      } else if (strVal.equals("FALSE")) {
        return Wrapper.of(LBoolean.FALSE);
      } else {
        throw new AssertionError();
      }

    case SYMBOL:
      return Wrapper.of(LSymbol.of(content.asString()));
    }

    throw new AssertionError();
  }

  // public static DataFunction getInterpreter() {
  // return new SingleArgumentDataFunction() {
  // @Override
  // public LObject apply(LObject arg) {
  // LComposite input = (LComposite) arg;
  //
  // char[] asChars = new char[input.size()];
  //
  // for (int i = 0; i < input.size(); i++) {
  // asChars[i] = ((LCharacter) input.get(i)).charValue();
  // }
  //
  // Statement statement = interpret(new String(asChars));
  //
  // return LComposite.of(new LObject[] {
  // LInteger.of(statement.getType().ordinal()),
  // statement.getExpression() });
  // }
  //
  // @Override
  // public String getName() {
  // return "primtive_interpreter";
  // }
  // };
  // }
}
