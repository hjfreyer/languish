package languish.lambda;

public final class Abstraction extends Expression {

  private final Expression exp;

  private Abstraction(Expression exp) {
    this.exp = exp;
  }

  public Expression getExpression() {
    return exp;
  }

  public static Abstraction of(Expression exp) {
    return new Abstraction(exp);
  }

  @Override
  public Type getType() {
    return Type.ABSTRACTION;
  }

  @Override
  public String toString() {
    return "Abstraction.of(" + exp + ")";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((exp == null) ? 0 : exp.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Abstraction other = (Abstraction) obj;
    if (exp == null) {
      if (other.exp != null) {
        return false;
      }
    } else if (!exp.equals(other.exp)) {
      return false;
    }
    return true;
  }

}