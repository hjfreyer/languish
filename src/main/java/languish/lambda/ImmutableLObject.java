package languish.lambda;


public abstract class ImmutableLObject extends LObject {
  @Override
  public final LObject deepClone() {
    return this;
  }
}
