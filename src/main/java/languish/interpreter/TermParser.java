package languish.interpreter;

import java.util.List;

import languish.lambda.Operation;
import languish.lambda.Primitive;
import languish.lambda.Term;
import languish.primitives.LInteger;
import languish.primitives.LSymbol;
import languish.util.Lambda;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Scanners;
import org.codehaus.jparsec.Terminals;
import org.codehaus.jparsec.Token;
import org.codehaus.jparsec.Tokens.Fragment;
import org.codehaus.jparsec.functors.Map;

import com.google.common.collect.ImmutableList;

public class TermParser {
  public static final Terminals OPERATORS =
      Terminals.operators("[", "]", "ABS", "APP", "EQUALS", "NATIVE_APPLY",
          "NOOP", "PRIMITIVE", "REF", "NULL");

  public static final Parser<String> STRING_LIT =
      Terminals.StringLiteral.DOUBLE_QUOTE_TOKENIZER;
  public static final Parser<Fragment> INTEGER_LIT =
      Terminals.IntegerLiteral.TOKENIZER;

  public static final Parser<Void> DELIM =
      Parsers.or(Scanners.JAVA_LINE_COMMENT, Scanners.JAVA_BLOCK_COMMENT,
          Scanners.WHITESPACES).skipMany();

  public static final Parser<List<Token>> LEXER =
      Parsers.or(STRING_LIT, INTEGER_LIT, OPERATORS.tokenizer()).lexer(DELIM);

  public static final Parser<Token> STD_OPERATION =
      OPERATORS.token("ABS", "APP", "EQUALS", "NATIVE_APPLY", "REF");

  public static final Parser<LInteger> LINTEGER =
      Terminals.IntegerLiteral.PARSER.map(new Map<String, LInteger>() {
        public LInteger map(String s) {
          return LInteger.of(Integer.parseInt(s));
        }
      });

  public static final Parser<LSymbol> LSYMBOL =
      Terminals.StringLiteral.PARSER.map(new Map<String, LSymbol>() {
        public LSymbol map(String s) {
          return LSymbol.of(s);
        }
      });

  public static final Parser<? extends Primitive> PRIMITIVE =
      Parsers.or(LINTEGER, LSYMBOL);

  public static final Parser<Term> PRIMITIVE_TERM =
      PRIMITIVE.between(OPERATORS.token("PRIMITIVE"), OPERATORS.token("NULL"))
          .between(OPERATORS.token("["), OPERATORS.token("]")).map(
              new Map<Primitive, Term>() {
                public Term map(Primitive from) {
                  return Lambda.primitive(from);
                }
              });

  public static final Parser.Reference<Term> TERM_REF = Parser.newReference();

  public static final Parser<Term> TERM_PROPER =
      Parsers.list(
          ImmutableList.<Parser<?>> of(STD_OPERATION, TERM_REF.lazy(), TERM_REF
              .lazy())).between(OPERATORS.token("["), OPERATORS.token("]"))
          .map(new Map<List<?>, Term>() {
            public Term map(List<?> from) {
              Operation op = (Operation) from.get(0);
              Term first = (Term) from.get(1);
              Term second = (Term) from.get(2);

              return new Term(op, first, second);
            }
          });

  public static final Parser<Term> TERM_TOKEN = TERM_PROPER.or(PRIMITIVE_TERM);

  public static final Parser<Term> TERM = TERM_TOKEN.from(LEXER);

  static { // Ugh... java...
    TERM_REF.set(TERM_TOKEN);
  }

  private TermParser() {
  }
}