package languish.prim.data;


public final class LUnit extends LObject {

  private LUnit() {}

  private static final LUnit INSTANCE = new LUnit();

  public static LUnit instance() {
    return INSTANCE;
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public boolean equals(Object obj) {
    return obj != null && obj instanceof LUnit;
  }

  public String repr() {
    return null;
  }
}
