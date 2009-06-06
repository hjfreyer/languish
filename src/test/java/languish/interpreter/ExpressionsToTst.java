package languish.interpreter;

import static languish.testing.TestConstants.*;
import languish.lambda.Application;
import languish.lambda.Expression;
import languish.prim.data.LBoolean;
import languish.prim.data.LObject;
import languish.prim.functions.BooleanOps;
import languish.prim.functions.IntegerOps;

public enum ExpressionsToTst {

  SINGLE_ADD(Application.of(Application.of(IntegerOps.ADD, w(FIVE)), w(FOUR)), //
      "(APP (APP (~ADD~) (!5!)) (!4!))",
      false,
      null,
      true,
      NINE),

  DOUBLE_ADD(Application.of(Application.of(IntegerOps.ADD, Application.of(
      Application.of(IntegerOps.ADD, w(FIVE)), w(FOUR))), w(THREE)), //
      "(APP (APP (~ADD~) (APP (APP (~ADD~) (!5!)) (!4!))) (!3!))",
      false,
      null,
      true,
      TWELVE),

  /** BOOLEAN TESTS **/
  SIMPLE_BRANCH(Application.of(Application.of(Application.of(BooleanOps.BRANCH,
      w(LBoolean.TRUE)), w(FOUR)), w(FIVE)), //
      "(APP (APP (APP (~BRANCH~) (!TRUE!)) (!4!)) (!5!))",
      true,
      Application.of(Application.of(BooleanOps.BRANCH_THEN, w(FOUR)), w(FIVE)),
      true,
      FOUR),

  SIMPLE_BRANCH_FALSE(Application.of(Application.of(Application.of(
      BooleanOps.BRANCH, w(LBoolean.FALSE)), w(FOUR)), w(FIVE)), //
      "(APP (APP (APP (~BRANCH~) (!FALSE!)) (!4!)) (!5!))",
      true,
      Application.of(Application.of(BooleanOps.BRANCH_ELSE, w(FOUR)), w(FIVE)),
      true,
      FIVE),

  // public void testApplicationOnBranch() {
  // Expression[] cases = new Expression[] { Application.of(IDENT, FOUR), FIVE
  // };
  //
  // assertEquals(FOUR, Reducer.reduceCompletely(Branch.of(ZERO, cases)));
  // assertEquals(FIVE, Reducer.reduceCompletely(Branch.of(ONE, cases)));
  // }
  //
  // public void testLoopOnOffBranch() {
  // Expression[] cases = new Expression[] { FOUR, LOOP };
  //
  // assertEquals(FOUR, Reducer.reduceCompletely(Branch.of(ZERO, cases)));
  // }
  //
  // public void testLoopOnOnBranch() {
  // Expression[] cases = new Expression[] { FOUR, LOOP };
  //
  // assertEquals(LOOP, Reducer.reduceOnce(Branch.of(ONE, cases)));
  // }
  //
  // public void testApplicationOnSelector() {
  // Expression[] cases = new Expression[] { FOUR, FIVE };
  //
  // Expression zeroFunc = Application.of(IDENT, ZERO);
  // Expression oneFunc = Application.of(IDENT, ONE);
  //
  // assertEquals(FOUR, Reducer.reduceCompletely(Branch.of(zeroFunc, cases)));
  // assertEquals(FIVE, Reducer.reduceCompletely(Branch.of(oneFunc, cases)));
  // }
  //
  // public void testNestedBranches_onCases() {
  // Expression[] cases1 = new Expression[] { ONE, TWO };
  // Branch branch1 = Branch.of(ONE, cases1);
  //
  // Expression[] cases2 = new Expression[] { branch1, TWO };
  //
  // assertEquals(TWO, Reducer.reduceCompletely(Branch.of(ZERO, cases2)));
  // assertEquals(TWO, Reducer.reduceCompletely(Branch.of(ONE, cases2)));
  // }
  //
  // public void testNestedBranches_onSelector() {
  // Expression[] cases1 = new Expression[] { ONE, TWO };
  // Branch branch1 = Branch.of(ZERO, cases1);
  // Branch branch2 = Branch.of(ONE, cases1);
  //
  // Expression[] cases2 = new Expression[] { THREE, FOUR, FIVE };
  //
  // assertEquals(FOUR, Reducer.reduceCompletely(Branch.of(branch1, cases2)));
  // assertEquals(FIVE, Reducer.reduceCompletely(Branch.of(branch2, cases2)));
  // }
  //
  // public void testNestedBranches_onBoth() {
  // Expression[] cases1 = new Expression[] { ONE, TWO };
  // Branch branch1 = Branch.of(ZERO, cases1);
  // Branch branch2 = Branch.of(ONE, cases1);
  //
  // Expression[] cases2 = new Expression[] { FOUR, branch2, FIVE };
  //
  // assertEquals(TWO, Reducer.reduceCompletely(Branch.of(branch1, cases2)));
  // }
  //
  // public void testNestedBranches_doubly() {
  // Expression[] cases1 = new Expression[] { ONE, TWO };
  // Branch branch1 = Branch.of(ZERO, cases1);
  //
  // Expression[] cases2 = new Expression[] { ONE, ZERO };
  // Branch branch2 = Branch.of(branch1, cases2);
  //
  // Expression[] cases3 = new Expression[] { FOUR, THREE, FIVE };
  // Branch branch3 = Branch.of(branch2, cases3);
  //
  // assertEquals(FOUR, Reducer.reduceCompletely(branch3));
  // }
  //      
  ;

  public Expression expForm;
  public String codeForm;
  public boolean reduceOnce;
  public Expression reducedOnce;
  public boolean reduceCompletely;
  public LObject reducedCompletely;

  private ExpressionsToTst(Expression expForm, String codeForm,
      boolean reduceOnce, Expression reducedOnce, boolean reduceCompletely,
      LObject reducedCompletely) {
    this.expForm = expForm;
    this.codeForm = codeForm;
    this.reduceOnce = reduceOnce;
    this.reducedOnce = reducedOnce;
    this.reduceCompletely = reduceCompletely;
    this.reducedCompletely = reducedCompletely;
  }

}
