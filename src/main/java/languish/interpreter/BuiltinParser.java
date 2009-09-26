package languish.interpreter;

import java.util.List;

import languish.base.LObject;
import languish.base.Tuple;
import languish.primitives.LInteger;
import languish.primitives.LSymbol;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Scanners;
import org.codehaus.jparsec.Terminals;
import org.codehaus.jparsec.functors.Map;

public class BuiltinParser {

  public static final Terminals OPERATORS = Terminals.operators("[", "]");

  public static final Parser<Void> DELIM =
      Parsers.or(Scanners.JAVA_LINE_COMMENT, Scanners.JAVA_BLOCK_COMMENT,
          Scanners.WHITESPACES).skipMany();

  public static final Parser<LInteger> LINTEGER =
      Scanners.INTEGER.map(new Map<String, LInteger>() {
        public LInteger map(String s) {
          return LInteger.of(Integer.parseInt(s));
        }

        @Override
        public String toString() {
          return "LINTEGER";
        }
      });

  public static final Parser<LSymbol> LSYMBOL =
      Terminals.StringLiteral.DOUBLE_QUOTE_TOKENIZER
          .map(new Map<String, LSymbol>() {
            public LSymbol map(String s) {
              return LSymbol.of(s);
            }
          });

  public static final Parser<LObject> BUILTIN_IDENT =
      Scanners.IDENTIFIER.map(new Map<String, LObject>() {
        public LObject map(String from) {
          return Builtins.valueOf(from).getExpression();
        }
      });

  public static final Parser<LObject> LEAF =
      Parsers.or(LINTEGER, LSYMBOL, BUILTIN_IDENT);

  public static final Parser.Reference<LObject> LOBJ_REF =
      Parser.newReference();

  public static final Parser<Tuple> TUPLE =
      LOBJ_REF.lazy().many()
          .between(Scanners.string("["), Scanners.string("]")).map(
              new Map<List<LObject>, Tuple>() {
                public Tuple map(List<LObject> from) {
                  LObject[] objs = new LObject[from.size()];
                  objs = from.toArray(objs);

                  return Tuple.of(objs);
                }
              });

  public static final Parser<LObject> LOBJECT =
      LEAF.or(TUPLE).between(DELIM, DELIM);

  static { // Ugh... java...
    LOBJ_REF.set(LOBJECT);
  }

  private BuiltinParser() {}
}
