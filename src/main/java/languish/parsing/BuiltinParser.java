package languish.parsing;

import java.util.HashMap;
import java.util.Map;

import languish.base.LObject;
import languish.base.Lambda;
import languish.base.Tuple;
import languish.interpreter.Builtins;
import languish.interpreter.Statement;
import languish.interpreter.Statement.Type;
import languish.prim.data.LInteger;
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
      ParserUtil.failWithInternalMessages(monitor.getMessages(), grammarDoc);
    }
  }

  private final Map<String, LObject> macros = new HashMap<String, LObject>();

  public BuiltinParser() {}

  @Override
  public Statement parseStatement(String statement, LObject env) {

    Pair<Statement.Type, LObject> exp = parseStatementToExpression(statement);

    return new Statement(exp.getFirst(), exp.getSecond());
  }

  public Pair<Statement.Type, LObject> parseStatementToExpression(
      String statement) {
    ITextDocument doc = new StringDocument(statement);

    INode node = getHadrianParser().parse(doc);

    Statement.Type type;
    LObject result;

    String tag = node.getTag().toString();
    node = node.getChildren().get(0);

    if (tag.equals("MACRO")) {
      String name = node.getChildren().get(0).asString();
      LObject exp = expressionFromINode(node.getChildren().get(1));

      macros.put(name, exp);

      type = Type.REDUCE;
      result = Lambda.data(Tuple.of());
    } else {
      type = Statement.Type.valueOf(tag);
      result = expressionFromINode(node);
    }
    return Pair.of(type, result);
  }

  public static HadrianParser getHadrianParser() {
    return PrimitiveHadrianParser.INSTANCE.parser;
  }

  private LObject expressionFromINode(INode inode) {
    if (inode.getTag().equals("LITERAL")) {
      return literalFromINode(inode.getChildren().get(0));
    } else if (inode.getTag().equals("PRIM_GET")) {
      return primGetFromINode(inode.getChildren().get(0));
    } else if (inode.getTag().equals("TUPLE")) {
      return tupleFromINode(inode.getChildren().get(0));
    } else if (inode.getTag().equals("MACRO_GET")) {
      String macroName = inode.getChildren().get(0).asString();

      if (!macros.containsKey(macroName)) {
        throw new IllegalArgumentException("macro " + macroName + " undefined.");
      }

      return macros.get(macroName);
    } else {
      throw new AssertionError();
    }
  }

  public LObject primGetFromINode(INode node) {
    String name = node.asString();

    return Builtins.valueOf(name).getExpression();
  }

  private Tuple tupleFromINode(INode node) {
    int size = node.getChildren().size();

    LObject[] tuple = new LObject[size];

    for (int i = 0; i < size; i++) {
      tuple[i] = expressionFromINode(node.getChildren().get(i));
    }

    return Tuple.of(tuple);
  }

  private LObject literalFromINode(INode inode) {
    String type = inode.getTag().toString();
    INode content = inode.getChildren().get(0);

    if (type.equals("SYMBOL")) {

      return LSymbol.of(content.asString());
    } else if (type.equals("INT")) {
      String strVal = content.asString();

      return LInteger.of(Integer.parseInt(strVal));

    } else {
      throw new AssertionError();
    }

    // return parseLiteral(Literal.valueOf(type), content);
  }
}
