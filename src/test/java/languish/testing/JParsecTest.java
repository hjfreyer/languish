package languish.testing;

import junit.framework.TestCase;

import org.codehaus.jparsec.OperatorTable;
import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.Scanners;
import org.codehaus.jparsec.Terminals;
import org.codehaus.jparsec.functors.Binary;
import org.codehaus.jparsec.functors.Map;
import org.codehaus.jparsec.functors.Unary;

public class JParsecTest extends TestCase {
  public void testCalc() {
    Calculator.CALCULATOR.parse("3+    4");
  }
}

class Calculator {

  enum BinaryOperator implements Binary<Double> {
    PLUS {
      public Double map(Double a, Double b) {
        return a + b;
      }
    },
    MINUS {
      public Double map(Double a, Double b) {
        return a - b;
      }
    },
    MUL {
      public Double map(Double a, Double b) {
        return a * b;
      }
    },
    DIV {
      public Double map(Double a, Double b) {
        return a / b;
      }
    }
  }

  enum UnaryOperator implements Unary<Double> {
    NEG {
      public Double map(Double n) {
        return -n;
      }
    }
  }

  static final Parser<Double> NUMBER =
      Terminals.DecimalLiteral.PARSER.map(new Map<String, Double>() {
        public Double map(String s) {
          return Double.valueOf(s);
        }
      });

  private static final Terminals OPERATORS =
      Terminals.operators("+", "-", "*", "/", "(", ")");

  static final Parser<Void> IGNORED =
      Parsers.or(Scanners.JAVA_LINE_COMMENT, Scanners.JAVA_BLOCK_COMMENT,
          Scanners.WHITESPACES).skipMany();

  static final Parser<?> TOKENIZER =
      Parsers.or(Terminals.DecimalLiteral.TOKENIZER, OPERATORS.tokenizer());

  static Parser<?> term(String... names) {
    return OPERATORS.token(names);
  }

  static final Parser<BinaryOperator> WHITESPACE_MUL =
      term("+", "-", "*", "/").not().retn(BinaryOperator.MUL);

  static <T> Parser<T> op(String name, T value) {
    return term(name).retn(value);
  }

  static Parser<Double> calculator(Parser<Double> atom) {
    Parser.Reference<Double> ref = Parser.newReference();
    Parser<Double> unit = ref.lazy().between(term("("), term(")")).or(atom);
    Parser<Double> parser =
        new OperatorTable<Double>().infixl(op("+", BinaryOperator.PLUS), 10)
            .infixl(op("-", BinaryOperator.MINUS), 10).infixl(
                op("*", BinaryOperator.MUL).or(WHITESPACE_MUL), 20).infixl(
                op("/", BinaryOperator.DIV), 20).prefix(
                op("-", UnaryOperator.NEG), 30).build(unit);
    ref.set(parser);
    return parser;
  }

  public static final Parser<Double> CALCULATOR =
      calculator(NUMBER).from(TOKENIZER, IGNORED);
}
//
// class Calculator {
// private interface Operator {
// double cal(double a, double b);
// }
//
// private static Map2 toMap2(final Operator op) {
// return new Map2() {
// public Object map(Object o1, Object o2) {
// final Number i1 = (Number) o1;
// final Number i2 = (Number) o2;
// return new Double(op.cal(i1.doubleValue(), i2.doubleValue()));
// }
// };
// }
//
// public Parser getParser() {
// final Pattern digits = Patterns.range('0', '9').many(1);
// final Pattern number =
// digits.seq(Patterns.isChar('.').seq(digits).optional());
// final Parser s_number = Scanners.pattern(number, "number");
//
// final Parser s_line_comment = Scanners.JAVA_LINE_COMMENT;
// final Parser s_block_comment = Scanners.blockComment("/*", "*/");
// final Parser s_whitespace = Scanners.WHITESPACES;
//
// final Parser l_number =
// Scanners.lexer(s_number, TokenDecimal.getTokenizer());
// final Words ops =
// Lexers.getOperators(new String[] { "+", "-", "*", "/", "(", ")" });
// final Parser s_delim =
// Parsers.sum(
// new Parser[] { s_line_comment, s_block_comment, s_whitespace })
// .many();
// final Parser l_tok = Parsers.plus(ops.getLexer(), l_number);
// final Parser lexer =
// Scanners.lexeme(s_delim, l_tok).followedBy(Parsers.eof());
//
// final Operator plus = new Operator() {
// public double cal(double n1, double n2) {
// return n1 + n2;
// }
// };
// final Operator minus = new Operator() {
// public double cal(double n1, double n2) {
// return n1 - n2;
// }
// };
// final Operator mul = new Operator() {
// public double cal(double n1, double n2) {
// return n1 * n2;
// }
// };
// final Operator div = new Operator() {
// public double cal(double n1, double n2) {
// return n1 / n2;
// }
// };
// final Map neg = new Map() {
// public Object map(Object o) {
// return new Double(((Number) o).doubleValue());
// }
// };
// final Parser p_plus = getOperator2(ops, "+", plus);
// final Parser p_minus = getOperator2(ops, "-", minus);
// final Parser p_mul = getOperator2(ops, "*", mul);
// final Parser p_div = getOperator2(ops, "/", div);
// final Parser p_neg = getOperator(ops, "-", neg);
// final Parser p_lparen = istoken(ops, "(");
// final Parser p_rparen = istoken(ops, ")");
// final Parser p_number = Terms.decimalParser(new FromString() {
// public Object fromString(int from, int len, String s) {
// return Double.valueOf(s);
// }
// });
// final Parser[] lazy_expr = new Parser[1];
// final Parser p_lazy_expr = Parsers.lazy(new ParserEval() {
// public Parser eval() {
// return lazy_expr[0];
// }
// });
// final Parser p_term =
// Parsers
// .plus(Parsers.between(p_lparen, p_rparen, p_lazy_expr), p_number);
// final OperatorTable optable =
// new OperatorTable().infixl(p_plus, 10).infixl(p_minus, 10).infixl(
// p_mul, 20).infixl(p_div, 20).prefix(p_neg, 30);
// final Parser p_expr = Expressions.buildExpressionParser(p_term, optable);
// lazy_expr[0] = p_expr;
// return Parsers.parseTokens(lexer, p_expr.followedBy(Parsers.eof()),
// "calculator");
// }
//
// private static Parser getOperator2(Words ops, String op, Operator v) {
// return getOperator(ops, op, toMap2(v));
// }
//
// private static Parser getOperator(Words ops, String op, Object v) {
// return istoken(ops, op).seq(Parsers.retn(v));
// }
//
// private static Parser istoken(Words ops, String op) {
// return Parsers.token(ops.getToken(op));
// }
// }