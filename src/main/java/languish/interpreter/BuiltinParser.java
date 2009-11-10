package languish.interpreter;

import java.util.List;

import languish.lambda.LObject;
import languish.lambda.Term;
import languish.primitives.LInteger;
import languish.primitives.LSymbol;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Scanners;
import org.codehaus.jparsec.Terminals;
import org.codehaus.jparsec.Token;
import org.codehaus.jparsec.Tokens;
import org.codehaus.jparsec.Tokens.Fragment;
import org.codehaus.jparsec.functors.Map;

public class BuiltinParser {

  public static final Parser<Tokens.Fragment> IDENTIFIER =
      Terminals.Identifier.TOKENIZER;
  public static final Parser<String> STRING_LIT =
      Terminals.StringLiteral.DOUBLE_QUOTE_TOKENIZER;
  public static final Parser<Fragment> INTEGER_LIT =
      Terminals.IntegerLiteral.TOKENIZER;

  public static final Terminals OPERATORS = Terminals.operators("[", "]");

  public static final Parser<Void> DELIM =
      Parsers.or(Scanners.JAVA_LINE_COMMENT, Scanners.JAVA_BLOCK_COMMENT,
          Scanners.WHITESPACES).skipMany();

  public static final Parser<List<Token>> LEXER =
      Parsers.or(IDENTIFIER, STRING_LIT, INTEGER_LIT, OPERATORS.tokenizer())
          .lexer(DELIM);

  public static final Parser<LInteger> LINTEGER =
      Terminals.IntegerLiteral.PARSER.map(new Map<String, LInteger>() {
        public LInteger map(String s) {
          return LInteger.of(Integer.parseInt(s));
        }

        @Override
        public String toString() {
          return "LINTEGER";
        }
      });

  public static final Parser<LSymbol> LSYMBOL =
      Terminals.StringLiteral.PARSER.map(new Map<String, LSymbol>() {
        public LSymbol map(String s) {
          return LSymbol.of(s);
        }
      });

  public static final Parser<LObject> BUILTIN_IDENT =
      Terminals.Identifier.PARSER.map(new Map<String, LObject>() {
        public LObject map(String from) {
          return Builtins.valueOf(from).getExpression();
        }
      });

  public static final Parser<LObject> LEAF =
      Parsers.or(LINTEGER, LSYMBOL, BUILTIN_IDENT);

  public static final Parser.Reference<LObject> LOBJ_REF =
      Parser.newReference();

  public static final Parser<Term> TUPLE =
      LOBJ_REF.lazy().many()
          .between(OPERATORS.token("["), OPERATORS.token("]")).map(
              new Map<List<LObject>, Term>() {
                public Term map(List<LObject> from) {
                  LObject[] objs = new LObject[from.size()];
                  objs = from.toArray(objs);

                  return Term.of(objs);
                }
              });

  public static final Parser<LObject> LOBJECT = LEAF.or(TUPLE);

  public static final Parser<Term> SINGLE_TUPLE = TUPLE.from(LEXER);

  public static final Parser<List<Term>> PROGRAM = TUPLE.many().from(LEXER);

  static { // Ugh... java...
    LOBJ_REF.set(LOBJECT);
  }

  private BuiltinParser() {
  }
}
