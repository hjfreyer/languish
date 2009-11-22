package languish.base;

import languish.util.TermPrinter;

public final class Term {

  public static final Term NULL = new Term(Operations.NOOP, null, null);

  private final Operation operation;
  private final Object first;
  private final Object second;

  public Term(Operation operation, Object first, Object second) {
    this.operation = operation;
    this.first = first;
    this.second = second;
  }

  public Term reduce() {
    return operation.reduce(this);
  }

  public boolean isReduced() {
    return operation.isReduced(this);
  }

  public Term replaceAllReferencesToParam(int id, Term with) {
    return operation.replaceAllReferencesToParam(this, id, with);
  }

  public Term reduceCompletely() {
    Term term = this;
    while (!term.isReduced()) {
      term = term.reduce();
    }
    return term;
  }

  public Operation getOperation() {
    return operation;
  }

  public Object getFirst() {
    return first;
  }

  public Object getSecond() {
    return second;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((first == null) ? 0 : first.hashCode());
    result = prime * result + ((operation == null) ? 0 : operation.hashCode());
    result = prime * result + ((second == null) ? 0 : second.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    Term other = (Term) obj;
    if (first == null) {
      if (other.first != null)
        return false;
    } else if (!first.equals(other.first))
      return false;
    if (operation == null) {
      if (other.operation != null)
        return false;
    } else if (!operation.equals(other.operation))
      return false;
    if (second == null) {
      if (other.second != null)
        return false;
    } else if (!second.equals(other.second))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return TermPrinter.getCodeForTerm(this);
  }

}
