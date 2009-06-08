package languish.prim.data;

import java.util.Arrays;

import languish.lambda.Application;
import languish.lambda.Expression;
import languish.lambda.Wrapper;

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

  public static LComposite of(LObject[] array) {
    return new LComposite(array);
  }

  public int size() {
    return array.length;
  }

  @Override
  public String toString() {
    return getCanonicalForm().toString();
  }

  @Override
  public Expression getCanonicalForm() {
    Application result =
        Application.of(LComposites.WRAP, Wrapper.of(LInteger.of(array.length)));

    for (LObject element : array) {
      result = Application.of(result, Wrapper.of(element));
    }

    return result;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(array);
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    LComposite other = (LComposite) obj;
    if (!Arrays.equals(array, other.array)) {
      return false;
    }
    return true;
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
