package languish.parsing;

import static languish.base.Lambda.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import languish.base.DataFunction;
import languish.base.LObject;
import languish.base.Tuple;
import languish.primitives.LSymbol;

import org.quenta.tedir.antonius.doc.StringDocument;
import org.quenta.tedir.antonius.lex.IStringFilter;
import org.quenta.tedir.antonius.lex.IWordPredicate;
import org.quenta.tedir.hadrian.GrammarModule;
import org.quenta.tedir.hadrian.HTree;
import org.quenta.tedir.hadrian.HadrianParser;
import org.quenta.tedir.hadrian.INode;
import org.quenta.tedir.hadrian.LiteralGrammarModule;
import org.quenta.tedir.hadrian.Nonterm;
import org.quenta.tedir.hadrian.Production;
import org.quenta.tedir.hadrian.lex.DelimitedConfiguration;
import org.quenta.tedir.hadrian.lex.ITokenizerConfigurator;
import org.quenta.tedir.hadrian.lex.WordConfigurator;

public class LGrammars {

  private LGrammars() {}

  public static final DataFunction INTERPRET_STATEMENT =
      new DataFunction.TwoArgDataFunction() {
        @Override
        public Tuple applyPair(LObject obj1, LObject obj2) {
          Tuple grammarSpec = (Tuple) obj1;
          LSymbol inputString = (LSymbol) obj2;

          HadrianParser parser = renderGrammar(grammarSpec);

          INode root =
              parser.parse(new StringDocument(inputString.stringValue()));

          return convertNode(root);
        }
      };

  private static class GrammarRule {
    final String type;
    final HTree tree;
    final Tuple function;

    public GrammarRule(String type, HTree tree, Tuple function) {
      this.type = type;
      this.tree = tree;
      this.function = function;
    }
  }

  private static HadrianParser renderGrammar(Tuple grammarSpec) {
    List<GrammarRule> rules = getRulesFromSpec(grammarSpec);

    List<String> typeNames = new ArrayList<String>();

    for (GrammarRule rule : rules) {
      if (!typeNames.contains(rule.type)) {
        typeNames.add(rule.type);
      }
    }

    List<Nonterm> nonterms = new ArrayList<Nonterm>();

    for (String type : typeNames) {
      nonterms.add(getNontermForType(rules, type));
    }

    GrammarModule grammar =
        new LiteralGrammarModule(nonterms).setExported("ROOT");

    return grammar.getParser(TOKEN_TYPES);
  }

  private static List<GrammarRule> getRulesFromSpec(Tuple grammarSpec) {
    List<GrammarRule> rules = new ArrayList<GrammarRule>();

    while (!grammarSpec.equals(Tuple.of())) {
      Tuple rule = (Tuple) grammarSpec.getFirst();
      grammarSpec = (Tuple) grammarSpec.getSecond();

      LSymbol type = (LSymbol) rule.getFirst();
      HTree tree = hTreeFromSpec((Tuple) rule.getSecond());
      Tuple function = (Tuple) rule.getThird();

      tree.tag(function);

      rules.add(0, new GrammarRule(type.stringValue(), tree, function));
    }
    return rules;
  }

  private static Nonterm getNontermForType(List<GrammarRule> rules, String type) {
    Nonterm result = new Nonterm(type);

    for (GrammarRule rule : rules) {
      if (rule.type.equals(type)) {
        result.addProduction(new Production(rule.tree).tag(rule.function));
      }
    }

    return result;
  }

  private static HTree hTreeFromSpec(Tuple spec) {
    String nodeType = ((LSymbol) spec.getFirst()).stringValue();
    LObject arg = spec.getSecond();

    if (nodeType.equals("TERM")) {
      return HTree.term(((LSymbol) arg).stringValue());
    } else if (nodeType.equals("VALUE")) {
      return HTree.value(((LSymbol) arg).stringValue());
    } else if (nodeType.equals("NON_TERM")) {
      return HTree.nonterm(((LSymbol) arg).stringValue());
    } else if (nodeType.equals("SEQ")) {
      List<HTree> nodes = new ArrayList<HTree>();

      Tuple argTuple = (Tuple) arg;
      while (!argTuple.equals(Tuple.of())) {
        nodes.add(hTreeFromSpec((Tuple) argTuple.getFirst()));
        argTuple = (Tuple) argTuple.getSecond();
      }

      HTree[] nodesArray = new HTree[nodes.size()];

      for (int i = 0; i < nodesArray.length; i++) {
        nodesArray[i] = nodes.get(i);
      }

      return HTree.seq(nodesArray);
    } else {
      throw new AssertionError();
    }
  }

  private static final IStringFilter STRING_FILTER = new IStringFilter() {
    public String filter(final String str) {
      final StringBuilder buf = new StringBuilder();
      int i = 0;
      while (i < str.length()) {
        if (str.charAt(i) == '\\') {
          i++;
        }
        buf.append(str.charAt(i++));
      }
      return buf.toString();
    }
  };

  private static List<ITokenizerConfigurator> TOKEN_TYPES =
      Arrays.<ITokenizerConfigurator> asList( //
          new WordConfigurator("number", new IWordPredicate() {
            public boolean isWordPart(final char c) {
              return Character.isDigit(c);
            }

            public boolean isWordStart(final char c) {
              return isWordPart(c);
            };
          }), //
          new WordConfigurator("PrimIdent", new IWordPredicate() {
            public boolean isWordStart(final char c) {
              return isWordPart(c);
            }

            public boolean isWordPart(final char c) {
              return Character.isLetter(c) || c == '_';
            }
          }).setInferKeywords(true), //
          // new DelimitedConfiguration("comment", "/*", "*/").isIgnored(true),
          // //
          new DelimitedConfiguration("string", "\"", "\"").setEscape('\\')
              .stripDelims(true).setFilter(STRING_FILTER)// , //
          // new DelimitedConfiguration("single", "'", "'").stripDelims(true),
          // //
          // new GreedyConfigurator(ICharPredicate.ANY)
          ) //
  ;

  /**
   * Returns true if the specified character is a legal hadrian specification
   * identifier start character. This can be used by plugins if they want to be
   * sure they're consistent with the surrounding language.
   **/
  public static final boolean isIdentifierStart(final char c) {
    return Character.isLetter(c) || c == '_';
  }

  /**
   * Returns true if the specified character can occur as part of a hadrian
   * specification identifier. This can be used by plugins if they want to be
   * sure they're consistent with the surrounding language.
   **/
  public static final boolean isIdentifierPart(final char c) {
    return isIdentifierStart(c) || Character.isDigit(c) || c == '-' || c == '.'
        || c == '$';
  }

  public static Tuple convertNode(INode node) {
    if (node.isText()) {
      return data(LSymbol.of(node.asString()));
    } else if (node.isTagged()) {
      Tuple function = (Tuple) node.getTag();

      return app(function, convertNode(node.getValue()));
    } else if (node.isList()) {
      List<INode> childNodes = node.getChildren();
      Tuple[] children = new Tuple[childNodes.size()];

      for (int i = 0; i < children.length; i++) {
        children[i] = convertNode(childNodes.get(i));
      }

      return arrayToList(children);

    } else {
      throw new AssertionError();
    }
  }

  private static Tuple arrayToList(Tuple[] items) {
    Tuple result = data(Tuple.of());

    for (int i = items.length - 1; i >= 0; i--) {
      result = cons(items[i], result);
    }

    return result;
  }
}
