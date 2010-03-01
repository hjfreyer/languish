package languish.lib;

import languish.base.Term;
import languish.base.Terms;
import languish.lib.testing.LibTestUtil;

public class Visitor {
	private static final Term LIB = LibTestUtil.loadLib("base/visitor");

	public static Term visitTree() {
		return Terms.car(LIB);
	}

	private Visitor() {
	}
}
