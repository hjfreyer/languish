package languish.primitives;

import languish.base.DataFunction;
import languish.base.LObject;
import languish.base.Lambda;
import languish.base.Tuple;
import languish.base.DataFunction.SingleArgDataFunction;

public class LEnum extends DataWrapper {
  private static long global_id = 0;

  private static class Enum {
    private final long id;

    private Enum(long id) {
      this.id = id;
    }

    @Override
    public boolean equals(Object obj) {
      return (obj instanceof Enum && ((Enum) obj).id == id);
    }
  }

  public static final DataFunction ENUM = new SingleArgDataFunction() {
    @Override
    public Tuple applySingle(LObject arg) {
      return Lambda.data(new LEnum(new Enum(global_id++)));
    }
  };

  private final Enum e;

  private LEnum(Enum e) {
    this.e = e;
  }

  @Override
  public Object getJavaValue() {
    return e;
  }
}
