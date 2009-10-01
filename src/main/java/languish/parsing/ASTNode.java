package languish.parsing;

public class ASTNode {
  private final String id;
  private final Object content;

  public ASTNode(String id, Object content) {
    super();
    this.id = id;
    this.content = content;
  }

  public String getId() {
    return id;
  }

  public Object getContent() {
    return content;
  }

  @Override
  public String toString() {
    return "ASTNode [content=" + content + ", id=" + id + "]";
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((content == null) ? 0 : content.hashCode());
    result = prime * result + ((id == null) ? 0 : id.hashCode());
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
    ASTNode other = (ASTNode) obj;
    if (content == null) {
      if (other.content != null)
        return false;
    } else if (!content.equals(other.content))
      return false;
    if (id == null) {
      if (other.id != null)
        return false;
    } else if (!id.equals(other.id))
      return false;
    return true;
  }

}