package com.hjfreyer.util;

import java.util.Arrays;
import java.util.List;

public class Lists {

  public static <T> List<T> of(T... objs) {
    return Arrays.asList(objs);
  }
}
