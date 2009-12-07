package languish.parsing;

import java.util.List;
import java.util.Map;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.hjfreyer.util.Tree;

public class SemanticModule<T> {
  private final Map<String, Function<String, T>> leafRules;
  private final Map<String, Function<List<T>, T>> inodeRules;

  public T process(Tree<String> ast) {
    String ruleName = ast.asList().get(0).asLeaf();
    Tree<String> arg = ast.asList().get(1);

    if (arg.isLeaf()) {
      return leafRules.get(ruleName).apply(arg.asLeaf());
    }

    List<T> processed =
        Lists.transform(arg.asList(), new Function<Tree<String>, T>() {
          @Override
          public T apply(Tree<String> arg) {
            return process(arg);
          }
        });

    return inodeRules.get(ruleName).apply(processed);
  }

  public SemanticModule(Map<String, Function<String, T>> leafRules,
      Map<String, Function<List<T>, T>> inodeRules) {
    super();
    this.leafRules = leafRules;
    this.inodeRules = inodeRules;
  }

  public Map<String, Function<String, T>> getLeafRules() {
    return leafRules;
  }

  public Map<String, Function<List<T>, T>> getInodeRules() {
    return inodeRules;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result =
        prime * result + ((inodeRules == null) ? 0 : inodeRules.hashCode());
    result = prime * result + ((leafRules == null) ? 0 : leafRules.hashCode());
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
    SemanticModule other = (SemanticModule) obj;
    if (inodeRules == null) {
      if (other.inodeRules != null)
        return false;
    } else if (!inodeRules.equals(other.inodeRules))
      return false;
    if (leafRules == null) {
      if (other.leafRules != null)
        return false;
    } else if (!leafRules.equals(other.leafRules))
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "SemanticModule [inodeRules=" + inodeRules + ", leafRules="
        + leafRules + "]";
  }

}
