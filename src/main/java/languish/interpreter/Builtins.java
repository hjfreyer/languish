package languish.interpreter;

import languish.base.LObject;
import languish.base.Lambda;
import languish.parsing.LGrammars;
import languish.primitives.DataFunctions;
import languish.primitives.LBoolean;

public enum Builtins {
  // General Ops

  // Expression reducer
  // UNWRAP(LExpressionWrappers.UNWRAP),
  DATA_EQUALS(DataFunctions.DATA_EQUALS),

  // Arithmetic
  ADD(DataFunctions.ADD),

  // Boolean ops
  TRUE(LBoolean.TRUE),
  FALSE(LBoolean.FALSE),
  BRANCH(DataFunctions.BRANCH),

  // Expression Operations
  ABS(Lambda.ABS),
  APP(Lambda.APP),
  DATA(Lambda.DATA),
  CAR(Lambda.CAR),
  CDR(Lambda.CDR),

  // GET(Lambda.GET),
  PRIM(Lambda.PRIM),
  REF(Lambda.REF),
  CONS(Lambda.CONS),

  CONS_AND_DATA_TO_TUPLES(DataFunctions.CONS_AND_DATA_TO_TUPLES),
  IS_NULL(DataFunctions.IS_NULL),

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
  PARSE_STATEMENT(LGrammars.PARSE_STATEMENT),
  // ADD_RULE(LGrammars.ADD_RULE),
  // // SET_NONTERMS(Wrapper.of(LMaps.EMPTY_MAP)),
  // TERM(LGrammars.TERM),
  // VALUE(LGrammars.VALUE),
  // NON_TERM(LGrammars.NON_TERM),
  //  
  // Primitive Parsing
  PARSE_INT(DataFunctions.PARSE_INT),
  // BUILTIN_GET(DataFunctions.BUILTIN_GET),

  ;

  private final LObject expression;

  private Builtins(LObject expression) {
    this.expression = expression;
  }

  public LObject getExpression() {
    return expression;
  }
}
