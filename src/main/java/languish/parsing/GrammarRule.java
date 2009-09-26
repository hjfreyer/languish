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

}
