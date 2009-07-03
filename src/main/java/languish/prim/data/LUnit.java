package languish.prim.data;


public final class LUnit extends LObject {

  private LUnit() {}

  public static final LUnit UNIT = new LUnit();

  public String getLiteral() {
    return "(~UNIT~)";
  }

  @Override
  public String toString() {
    return "LUnit.UNIT";
  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public boolean equals(Object obj) {
    return obj != null && obj instanceof LUnit;
  }
}
