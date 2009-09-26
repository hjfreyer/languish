package languish.parsing;

import java.util.List;

public class ASTNode {
  private final String id;
  private final List<?> children;
  private final ASTNode wrapped;
  private final String content;

  @SuppressWarnings("unchecked")
  public ASTNode(String id, Object obj) {
    this.id = id;
    if (obj instanceof List<?>) {
      children = (List<ASTNode>) obj;
      content = null;
      wrapped = null;
    } else if (obj instanceof String) {
      children = null;
      content = (String) obj;
      wrapped = null;
    } else if (obj instanceof ASTNode) {
      children = null;
      content = null;
      wrapped = (ASTNode) obj;
    } else {
      throw new IllegalArgumentException(obj + " cannot be passed to ASTNode");
    }
  }

  public ASTNode(String id, List<?> children, ASTNode wrapped, String content) {
    super();
    this.id = id;
    this.children = children;
    this.wrapped = wrapped;
    this.content = content;
  }

  public String getId() {
    return id;
  }

  public List<?> getChildren() {
    return children;
  }

  public ASTNode getWrapped() {
    return wrapped;
  }

  public String getContent() {
    return content;
  }

  @Override
  public String toString() {
    return "ASTNode [children=" + children + ", content=" + content + ", id="
        + id + ", wrapped=" + wrapped + "]";
  }
}