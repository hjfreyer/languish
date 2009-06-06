package languish.prim.data;

import java.util.Arrays;

public final class LComposite extends LObject {
  private final LObject[] array;

  private LComposite(LObject[] array) {
    this.array = array;
  }

  public LComposite(int elements) {
    this(new LObject[elements]);
  }

  public LObject get(int i) {
    return array[i];
  }

  void set(int i, LObject obj) {
    array[i] = obj;
  }

  @Override
  public boolean equals(Object obj) {
    return obj != null && obj instanceof LComposite
        && Arrays.equals(((LComposite) obj).array, array);
  }

  @Override
  public int hashCode() {
    return Arrays.deepHashCode(new Object[] { getClass(), array });
  }

  public static LComposite of(LObject[] array) {
    return new LComposite(array);
  }

  public int size() {
    return array.length;
  }

  public static LComposite compositeFromString(String input) {

    char[] chars = input.toCharArray();
    LCharacter[] lchars = new LCharacter[chars.length];

    for (int i = 0; i < chars.length; i++) {
      lchars[i] = new LCharacter(chars[i]);
    }

    return LComposite.of(lchars);
  }

  public static String compositeToString(LComposite input) {

    char[] chars = new char[input.size()];

    for (int i = 0; i < chars.length; i++) {
      chars[i] = ((LCharacter) input.get(i)).charValue();
    }

    return new String(chars);
  }
}
