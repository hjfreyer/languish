package languish.prim.data;

import java.util.Map;

import languish.lambda.Expression;

public class LMap extends LObject {

  private final Map<LSymbol, LObject> map;

  private LMap(Map<LSymbol, LObject> map) {
    this.map = map;
  }

  public static LMap of(Map<LSymbol, LObject> map) {
    return new LMap(map);
  }

  public Map<LSymbol, LObject> getMap() {
    return map;
  }

  @Override
  public Expression getGeneratingExpression() {
    throw new UnsupportedOperationException();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((map == null) ? 0 : map.hashCode());
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
    LMap other = (LMap) obj;
    if (map == null) {
      if (other.map != null) {
        return false;
      }
    } else if (!map.equals(other.map)) {
      return false;
    }
    return true;
  }

}
