package languish.interpreter;

import static languish.lambda.Lambda.data;
import languish.lambda.LObject;
import languish.lambda.Lambda;
import languish.prim.data.LBoolean;
import languish.prim.data.LBooleans;
import languish.prim.data.LIntegers;
import languish.prim.data.LParsers;

public enum Builtins {
  // General Ops
  EQUALS(LObject.EQUALS),

  // Expression reducer
//  UNWRAP(LExpressionWrappers.UNWRAP),

  // Arithmetic
  ADD(LIntegers.ADD),

  // Boolean ops
  TRUE(Lambda.data(LBoolean.TRUE)),
  FALSE(data(LBoolean.FALSE)),
  BRANCH(LBooleans.BRANCH),

  // Expression Operations
  ABS(Lambda.ABS),
  APP(Lambda.APP),
  DATA(Lambda.DATA),
  GET(Lambda.GET),
  PRIM(Lambda.PRIM),
  REF(Lambda.REF),
  PAIR(Lambda.PAIR),

  // LComposite
//  GET_ELEMENT(LComposites.GET_ELEMENT),
//  WRAP(LComposites.WRAP),
//
//  // Map Expressions
//  EMPTY_MAP(Data.of(LMaps.EMPTY_MAP)),
//  PUT_MAP(LMaps.PUT_MAP),
//
//  // Hadrian 
//  EMPTY_GRAMMAR(data(LGrammars.EMPTY_GRAMMAR)),
//  INTERPRET_STATEMENT(LGrammars.INTERPRET_STATEMENT),
//  ADD_RULE(LGrammars.ADD_RULE),
//  // SET_NONTERMS(Wrapper.of(LMaps.EMPTY_MAP)),
//  TERM(LGrammars.TERM),
//  VALUE(LGrammars.VALUE),
//  NON_TERM(LGrammars.NON_TERM),
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
