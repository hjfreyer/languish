package languish.prim.data;

import junit.framework.TestCase;

public class BranchTest extends TestCase {

  public void testSimpleTwoWayBranch() {}
  //
  // Expression exp1 =
  // Application.of(Application.of(Application.of(BooleanOps.BRANCH,
  // w(LBoolean.TRUE)), w(FOUR)), w(FIVE));
  //
  // Expression[] cases = new Expression[] { w(FOUR), w(FIVE) };
  //
  // assertEquals(FOUR, Reducer.reduceCompletely(Branch.of(ZERO, cases)));
  // assertEquals(FIVE, Reducer.reduceCompletely(Branch.of(ONE, cases)));
  // }
  //
  // public void testSimpleThreeWayBranch() {
  // Expression[] cases = new Expression[] { FOUR, FIVE, THREE };
  //
  // assertEquals(FOUR, Reducer.reduceCompletely(Branch.of(ZERO, cases)));
  // assertEquals(FIVE, Reducer.reduceCompletely(Branch.of(ONE, cases)));
  // assertEquals(THREE, Reducer.reduceCompletely(Branch.of(TWO, cases)));
  // }
  //
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
}
