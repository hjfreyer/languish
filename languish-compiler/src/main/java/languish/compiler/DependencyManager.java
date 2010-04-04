package languish.compiler;

public interface DependencyManager {

  public abstract String getResource(String resourceName);

  public abstract boolean hasResource(String resourceName);

}