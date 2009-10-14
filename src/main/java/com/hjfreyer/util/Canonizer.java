package com.hjfreyer.util;

import java.util.HashMap;
import java.util.Map;

public class Canonizer {
  private static final Map<Object, Object> canon =
      new HashMap<Object, Object>();

  @SuppressWarnings("unchecked")
  public static <T> T canonize(T obj) {
    if (!canon.containsKey(obj)) {
      canon.put(obj, obj);
    }

    return (T) canon.get(obj);
  }
}
