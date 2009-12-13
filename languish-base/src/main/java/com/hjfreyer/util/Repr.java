package com.hjfreyer.util;

import com.google.common.base.Function;

public class Repr {

  public static final Function<Object, String> TO_REPR =
      new Function<Object, String>() {
        @Override
        public String apply(Object obj) {
          return Repr.repr(obj);
        }
      };

  public static String repr(Object obj) {
    if (obj instanceof Reprable) {
      return ((Reprable) obj).repr();
    }

    throw new IllegalArgumentException("Object not reprable: " + obj);
  }

  private Repr() {

  }
}
