package languish.interpreter;

import languish.lambda.LObject;
import languish.parsing.LParsers;
import languish.primitives.DataFunctions;
import languish.primitives.LBoolean;
import languish.util.Lambda;

public enum Builtins {
  // General Ops

  // Expression reducer
  // UNWRAP(LExpressionWrappers.UNWRAP),
  DATA_EQUALS(DataFunctions.DATA_EQUALS),

  // Arithmetic
  ADD(DataFunctions.ADD),
  MULTIPLY(DataFunctions.MULTIPLY),

  // Boolean ops
  TRUE(LBoolean.TRUE),
  FALSE(LBoolean.FALSE),
  BRANCH(DataFunctions.BRANCH),
  AND(DataFunctions.AND),
  NOT(DataFunctions.NOT),

  // Expression Operations
  ABS(languish.util.ABS),
  APP(languish.util.APP),
  DATA(languish.util.DATA),
  CAR(languish.util.CAR),
  CDR(languish.util.CDR),

  // GET(Lambda.GET),
  PRIM(languish.util.PRIM),
  REF(languish.util.REF),
  CONS(languish.util.CONS),
  IS_PRIMITIVE(languish.util.IS_PRIMITIVE),

  // DEBUG
  PRINT(DataFunctions.PRINT),

  // CONS_AND_DATA_TO_TUPLES(DataFunctions.CONS_AND_DATA_TO_TUPLES),
  // IS_NULL(DataFunctions.IS_NULL),

  // REDUCTION_ERROR(Lambda.REDUCTION_ERROR),

  // LComposite
  // GET_ELEMENT(LComposites.GET_ELEMENT),
  // WRAP(LComposites.WRAP),
  //
  // // Map Expressions
  // EMPTY_MAP(Data.of(LMaps.EMPTY_MAP)),
  // PUT_MAP(LMaps.PUT_MAP),
  //
  // // Hadrian
  // EMPTY_GRAMMAR(data(LGrammars.EMPTY_GRAMMAR)),
  PARSE_TEXT(LParsers.PARSE_TEXT),
  // ADD_RULE(LGrammars.ADD_RULE),
  // // SET_NONTERMS(Wrapper.of(LMaps.EMPTY_MAP)),
  // TERM(LGrammars.TERM),
  // VALUE(LGrammars.VALUE),
  // NON_TERM(LGrammars.NON_TERM),
  //  
  // Primitive Parsing
  PARSE_INT(DataFunctions.PARSE_INT),
  BUILTIN_GET(DataFunctions.BUILTIN_GET),

  ;

  private final LObject expression;

  private Builtins(LObject expression) {
    this.expression = expression;
  }

  public LObject getExpression() {
    return expression;
  }
}
