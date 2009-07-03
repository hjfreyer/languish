//package languish.prim.data;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import languish.lambda.Expression;
//
//import org.quenta.tedir.antonius.lex.IStringFilter;
//import org.quenta.tedir.antonius.lex.IWordPredicate;
//import org.quenta.tedir.hadrian.GrammarModule;
//import org.quenta.tedir.hadrian.HTree;
//import org.quenta.tedir.hadrian.HadrianParser;
//import org.quenta.tedir.hadrian.LiteralGrammarModule;
//import org.quenta.tedir.hadrian.Nonterm;
//import org.quenta.tedir.hadrian.Production;
//import org.quenta.tedir.hadrian.lex.DelimitedConfiguration;
//import org.quenta.tedir.hadrian.lex.GreedyConfigurator;
//import org.quenta.tedir.hadrian.lex.ITokenizerConfigurator;
//import org.quenta.tedir.hadrian.lex.WordConfigurator;
//import org.quenta.tedir.nerva.ICharPredicate;
//
//public class LGrammar extends LObject {
//
//  public static class HTreeWrapper extends LObject {
//    private final HTree tree;
//
//    public HTreeWrapper(HTree tree) {
//      this.tree = tree;
//    }
//
//    public HTree getTree() {
//      return tree;
//    }
//
//    @Override
//    public String toString() {
//      return tree.toString();
//    }
//
//    @Override
//    public int hashCode() {
//      final int prime = 31;
//      int result = 1;
//      result = prime * result + ((tree == null) ? 0 : tree.hashCode());
//      return result;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//      if (this == obj) {
//        return true;
//      }
//      if (getClass() != obj.getClass()) {
//        return false;
//      }
//      HTreeWrapper other = (HTreeWrapper) obj;
//      if (tree == null) {
//        if (other.tree != null) {
//          return false;
//        }
//      } else if (!tree.equals(other.tree)) {
//        return false;
//      }
//      return true;
//    }
//
//  }
//
//  public static class GrammarRule {
//    final String type;
//    final HTree tree;
//    final Expression function;
//
//    public GrammarRule(String type, HTree tree, Expression function) {
//      this.type = type;
//      this.tree = tree;
//      this.function = function;
//    }
//  }
//
//  private final List<GrammarRule> rules = new ArrayList<GrammarRule>();
//
//  public HadrianParser getParser() {
//    List<String> typeNames = new ArrayList<String>();
//
//    for (GrammarRule rule : rules) {
//      if (!typeNames.contains(rule.type)) {
//        typeNames.add(rule.type);
//      }
//    }
//
//    List<Nonterm> nonterms = new ArrayList<Nonterm>();
//
//    for (String type : typeNames) {
//
//      nonterms.add(getNontermForType(type));
//
//    }
//
//    GrammarModule grammar =
//        new LiteralGrammarModule(nonterms).setExported("ROOT");
//
//    return grammar.getParser(TOKEN_TYPES);
//  }
//
//  private Nonterm getNontermForType(String type) {
//    Nonterm result = new Nonterm(type);
//
//    for (GrammarRule rule : rules) {
//      if (rule.type.equals(type)) {
//        result.addProduction(new Production(rule.tree).tag(rule.function));
//      }
//    }
//
//    return result;
//  }
//
//  public LGrammar addRule(GrammarRule r) {
//    rules.add(r);
//    return this;
//  }
//
//  private static final IStringFilter STRING_FILTER = new IStringFilter() {
//    public String filter(final String str) {
//      final StringBuilder buf = new StringBuilder();
//      int i = 0;
//      while (i < str.length()) {
//        if (str.charAt(i) == '\\') {
//          i++;
//        }
//        buf.append(str.charAt(i++));
//      }
//      return buf.toString();
//    }
//  };
//
//  private static List<ITokenizerConfigurator> TOKEN_TYPES =
//      Arrays.<ITokenizerConfigurator> asList( //
//          new WordConfigurator("number", new IWordPredicate() {
//            public boolean isWordPart(final char c) {
//              return Character.isDigit(c);
//            }
//
//            public boolean isWordStart(final char c) {
//              return isWordPart(c);
//            };
//          }), //
//          new WordConfigurator("PrimIdent", new IWordPredicate() {
//            public boolean isWordStart(final char c) {
//              return isWordPart(c);
//            }
//
//            public boolean isWordPart(final char c) {
//              return Character.isLetter(c) || c == '_';
//            }
//          }).setInferKeywords(true), //         
//          // new DelimitedConfiguration("comment", "/*", "*/").isIgnored(true),
//          // //
//          new DelimitedConfiguration("string", "\"", "\"").setEscape('\\')
//              .stripDelims(true).setFilter(STRING_FILTER), //
//          // new DelimitedConfiguration("single", "'", "'").stripDelims(true),
//          // //
//          new GreedyConfigurator(ICharPredicate.ANY)) //
//  ;
//
//  /**
//   * Returns true if the specified character is a legal hadrian specification
//   * identifier start character. This can be used by plugins if they want to be
//   * sure they're consistent with the surrounding language.
//   **/
//  public static final boolean isIdentifierStart(final char c) {
//    return Character.isLetter(c) || c == '_';
//  }
//
//  /**
//   * Returns true if the specified character can occur as part of a hadrian
//   * specification identifier. This can be used by plugins if they want to be
//   * sure they're consistent with the surrounding language.
//   **/
//  public static final boolean isIdentifierPart(final char c) {
//    return isIdentifierStart(c) || Character.isDigit(c) || c == '-' || c == '.'
//        || c == '$';
//  }
//
//  @Override
//  public int hashCode() {
//    final int prime = 31;
//    int result = 1;
//    result = prime * result + ((rules == null) ? 0 : rules.hashCode());
//    return result;
//  }
//
//  @Override
//  public boolean equals(Object obj) {
//    if (this == obj) {
//      return true;
//    }
//    if (getClass() != obj.getClass()) {
//      return false;
//    }
//    LGrammar other = (LGrammar) obj;
//    if (rules == null) {
//      if (other.rules != null) {
//        return false;
//      }
//    } else if (!rules.equals(other.rules)) {
//      return false;
//    }
//    return true;
//  }
//
//}
