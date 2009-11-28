package languish.parsing;

public class Production {
  private final String nonterminal;
  private final String name;
  private final Expression expression;

  public Production(String nonterminal, String name, Expression expression) {
    this.name = name;
    this.nonterminal = nonterminal;
    this.expression = expression;
  }

  public String getName() {
    return name;
  }

  public String getNonterminal() {
    return nonterminal;
  }

  public Expression getExpression() {
    return expression;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((expression == null) ? 0 : expression.hashCode());
    result = prime * result + ((nonterminal == null) ? 0 : nonterminal.hashCode());
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
    Production other = (Production) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (expression == null) {
      if (other.expression != null)
        return false;
    } else if (!expression.equals(other.expression))
      return false;
    if (nonterminal == null) {
      if (other.nonterminal != null)
        return false;
    } else if (!nonterminal.equals(other.nonterminal))
      return false;
    return true;
  }

}
