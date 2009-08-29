package languish.interpreter;

import java.util.List;

import languish.base.Tuple;

public class Module {

  private final Tuple expression;
  private final List<String> dependencies;

  public Module(Tuple expression, List<String> dependencies) {
    this.expression = expression;
    this.dependencies = dependencies;
  }

  public Tuple getExpression() {
    return expression;
  }

  public List<String> getDependencies() {
    return dependencies;
  }

}
