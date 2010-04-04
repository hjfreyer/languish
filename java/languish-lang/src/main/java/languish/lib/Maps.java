package languish.lib;

import languish.base.Term;
import languish.base.Terms;
import languish.lib.testing.LibTestUtil;

public class Maps {
	private static final Term LIB = LibTestUtil.loadLib("base/maps");

	public static Term put() {
		return Terms.car(LIB);
	}

	public static Term get() {
		return Terms.car(Terms.cdr(LIB));
	}
}
