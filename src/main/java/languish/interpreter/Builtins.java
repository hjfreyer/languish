package languish.interpreter;

import languish.lambda.LObject;
import languish.lambda.Lambda;
import languish.prim.data.LBoolean;
import languish.prim.data.LBooleans;
import languish.prim.data.LGrammars;
import languish.prim.data.LIntegers;
import languish.prim.data.LParsers;

public enum Builtins {
  // Starting interpreter
  // PRIM_INTERP(BuiltinParser.getInterpreter()),

  // Expression reducer
//  UNWRAP(LExpressionWrappers.UNWRAP),

  // Arithmetic
  ADD(LIntegers.ADD),

  // Boolean ops
  TRUE(Lambda.data(LBoolean.TRUE)),
  FALSE(Lambda.data(LBoolean.FALSE)),
  BRANCH(LBooleans.BRANCH),

  // Expression constructors
  ABS(Lambda.ABS),
  APP(Lambda.APP),
  LITERAL(Lambda.DATA),
  GET(Lambda.GET),
  PRIM(Lambda.PRIM),
  REF(Lambda.REF),
  TUPLE(Lambda.TUPLE),

  // LComposite
//  GET_ELEMENT(LComposites.GET_ELEMENT),
//  WRAP(LComposites.WRAP),
//
//  // Map Expressions
//  EMPTY_MAP(Data.of(LMaps.EMPTY_MAP)),
//  PUT_MAP(LMaps.PUT_MAP),
//
  // Hadrian
  EMPTY_GRAMMAR(Lambda.data(LGrammars.EMPTY_GRAMMAR)),
  INTERPRET_STATEMENT(LGrammars.INTERPRET_STATEMENT),
  ADD_RULE(LGrammars.ADD_RULE),
//  // SET_NONTERMS(Wrapper.of(LMaps.EMPTY_MAP)),
//  TERM(LGrammars.TERM),
//  VALUE(LGrammars.VALUE),
  NON_TERM(LGrammars.NON_TERM),
//  
  // Primitive Parsing
  PARSE_INT(LParsers.PARSE_INT);
  ;

  private final LObject expression;

  private Builtins(LObject expression) {
    this.expression = expression;
  }

  public LObject getExpression() {
    return expression;
  }
}
