package languish.lambda;

import java.util.Arrays;

import languish.prim.data.LObject;

public class Tuple extends LObject {

  private final LObject[] contents;

  public Tuple(int i) {
    this(new LObject[i]);
  }

  public Tuple(LObject... contents) {
    this.contents = contents;
  }

  public LObject[] getContents() {
    return contents;
  }

  public LObject getFirst() {
    return contents[0];
  }

  public void setFirst(LObject first) {
    contents[0] = first;
  }

  public LObject getSecond() {
    return contents[1];
  }

  public void setSecond(LObject second) {
    contents[1] = second;
  }

  public LObject get(int i) {
    return contents[i];
  }

  public int size() {
    return contents.length;
  }

  public static Tuple of(LObject... contents) {
    return new Tuple(contents);
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + Arrays.hashCode(contents);
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
    Tuple other = (Tuple) obj;
    if (!Arrays.equals(contents, other.contents)) {
      return false;
    }
    return true;
  }
}
