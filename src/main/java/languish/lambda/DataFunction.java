package languish.lambda;

import languish.prim.data.LObject;

public abstract class DataFunction extends Operation {

  @Override
  public Tuple reduceOnce(Tuple tuple) {
    if (tuple.getFirst() == Lambda.DATA) {
      tuple.setSecond(apply(tuple.getSecond()));

      return tuple;
    }

    return Lambda.reduceTupleOnce(tuple);
  }

  public abstract LObject apply(LObject arg);

}
