package languish.interpreter;

import java.util.List;

import languish.lambda.Operations;
import languish.lambda.Primitive;
import languish.lambda.Term;
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

  public static final Parser<Integer> INTEGER =
      Terminals.IntegerLiteral.PARSER.map(new Map<String, Integer>() {
        public Integer map(String s) {
          return Integer.parseInt(s);
        }
      });

  public static final Parser<String> STRING = Terminals.StringLiteral.PARSER;

  public static final Parser<? extends Primitive> PRIMITIVE =
      Parsers.or(INTEGER, STRING).map(new Map<Object, Primitive>() {
        public Primitive map(Object from) {
          return new Primitive(from);
        }
      });

  public static final Parser.Reference<Term> TERM_REF = Parser.newReference();

  public static final Parser<Term> NULL_TERM =
      OPERATORS.token("NULL").map(new Map<Token, Term>() {
        public Term map(Token from) {
          return Term.NULL;
        }
      });

  public static final Parser<Term> PRIMITIVE_TERM =
      PRIMITIVE.between(OPERATORS.token("PRIMITIVE"), TERM_REF.lazy()).between(
          OPERATORS.token("["), OPERATORS.token("]")).map(
          new Map<Primitive, Term>() {
            public Term map(Primitive from) {
              return Lambda.primitive(from);
            }
          });

  public static final Parser<Term> REFERENCE_TERM =
      INTEGER.between(OPERATORS.token("REF"), TERM_REF.lazy()).between(
          OPERATORS.token("["), OPERATORS.token("]")).map(
          new Map<Integer, Term>() {
            public Term map(Integer from) {
              return Lambda.ref(from);
            }
          });

  public static final Parser<Term> TERM_PROPER =
      Parsers.list(
          ImmutableList.<Parser<?>> of(STD_OPERATION, TERM_REF.lazy(), TERM_REF
              .lazy())).between(OPERATORS.token("["), OPERATORS.token("]"))
          .map(new Map<List<?>, Term>() {
            public Term map(List<?> from) {
              Token opName = (Token) from.get(0);
              Term first = (Term) from.get(1);
              Term second = (Term) from.get(2);

              return new Term(Operations.fromName(opName.toString()), first,
                  second);
            }
          });

  public static final Parser<Term> TERM_TOKEN =
      Parsers.or(TERM_PROPER, PRIMITIVE_TERM, REFERENCE_TERM, NULL_TERM);

  public static final Parser<Term> TERM = TERM_TOKEN.from(LEXER);

  static { // Ugh... java...
    TERM_REF.set(TERM_TOKEN);
  }

  private TermParser() {
  }
}
