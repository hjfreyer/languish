package languish.interpreter;

import java.util.HashMap;
import java.util.Map;

import languish.base.LObject;
import languish.parsing.BuiltinParser;
import languish.parsing.ParserUtil;

import org.quenta.tedir.antonius.doc.ITextDocument;
import org.quenta.tedir.antonius.doc.ResourceDocument;
import org.quenta.tedir.antonius.doc.StringDocument;
import org.quenta.tedir.hadrian.HadrianParser;
import org.quenta.tedir.hadrian.HadrianReader;
import org.quenta.tedir.hadrian.INode;
import org.quenta.tedir.hadrian.MessageMonitor;

import com.hjfreyer.util.Pair;

public class BaseParser {
  public static enum Literal {
    INT,
    BOOL,
    SYMBOL
  }

  private static enum PrimitiveHadrianParser {
    INSTANCE;

    HadrianParser parser = null;

    private PrimitiveHadrianParser() {
      String docPath = "hadrian/parser/BaseGrammar.tg";
      ClassLoader classLoader = BuiltinParser.class.getClassLoader();

      final ITextDocument grammarDoc =
          new ResourceDocument(docPath, classLoader);

      final MessageMonitor monitor = new MessageMonitor();
      this.parser = HadrianReader.getDefault().readParser(grammarDoc, monitor);
      ParserUtil.failWithInternalMessages(monitor.getMessages(), grammarDoc);
    }
  }

  private final Map<String, LObject> macros = new HashMap<String, LObject>();

  private BaseParser() {}

  public static Pair<String, String> parseProgramToParserAndBody(String program) {
    ITextDocument doc = new StringDocument(program);

    INode node = PrimitiveHadrianParser.INSTANCE.parser.parse(doc);

    String tag = node.getTag().toString();
    node = node.getChildren().get(0);

    if (tag.equals("MACRO")) {

    } else {

    }
    return Pair.of(null, null);
  }

}
