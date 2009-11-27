package languish.parsing;

public class GrammarRule {
  private final String type;
  private final String name;
  private final ParserTree tree;

  public GrammarRule(String type, String name, ParserTree tree) {
    this.name = name;
    this.type = type;
    this.tree = tree;
  }

  public String getName() {
    return name;
  }

  public String getType() {
    return type;
  }

  public ParserTree getTree() {
    return tree;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((name == null) ? 0 : name.hashCode());
    result = prime * result + ((tree == null) ? 0 : tree.hashCode());
    result = prime * result + ((type == null) ? 0 : type.hashCode());
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
    GrammarRule other = (GrammarRule) obj;
    if (name == null) {
      if (other.name != null)
        return false;
    } else if (!name.equals(other.name))
      return false;
    if (tree == null) {
      if (other.tree != null)
        return false;
    } else if (!tree.equals(other.tree))
      return false;
    if (type == null) {
      if (other.type != null)
        return false;
    } else if (!type.equals(other.type))
      return false;
    return true;
  }

}
