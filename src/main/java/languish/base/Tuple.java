package languish.base;

import java.util.Arrays;

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

  public LObject getThird() {
    return contents[2];
  }

  public void setThird(LObject third) {
    contents[2] = third;
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
  public Tuple deepClone() {
    LObject[] clones = new LObject[contents.length];

    for (int i = 0; i < clones.length; i++) {
      clones[i] = contents[i].deepClone();
    }

    return Tuple.of(clones);
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

  public void set(int i, LObject obj) {
    contents[i] = obj;
  }
}
