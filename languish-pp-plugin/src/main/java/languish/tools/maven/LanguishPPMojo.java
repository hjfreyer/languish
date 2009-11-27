package languish.tools.maven;

import java.io.File;
import java.io.IOException;

import languish.tools.preprocessor.Preprocessor;

/**
 * Parses a JavaCC grammar file (<code>*.jj</code>) and transforms it to Java
 * source files. Detailed information about the JavaCC options can be found on
 * the <a href="https://javacc.dev.java.net/">JavaCC website</a>.
 * 
 * @goal languish-preprocess
 * @phase generate-sources
 */
public class LanguishPPMojo extends AbstractPreprocessorMojo {

  /**
   * The directory where the JavaCC grammar files (<code>*.jj</code>) are
   * located.
   * 
   * @parameter expression="${sourceDirectory}"
   *            default-value="${basedir}/src/main/languish-pp"
   */
  private File sourceDirectory;

  /**
   * The directory where the parser files generated by JavaCC will be stored.
   * The directory will be registered as a compile source root of the project
   * such that the generated files will participate in later build phases like
   * compiling and packaging.
   * 
   * @parameter expression="${outputDirectory}"
   *            default-value="${project.build.directory}/generated-sources/languish"
   */
  private File outputDirectory;

  /**
   * The granularity in milliseconds of the last modification date for testing
   * whether a source needs recompilation.
   * 
   * @parameter expression="${lastModGranularityMs}" default-value="0"
   */
  private int staleMillis;

  /**
   * {@inheritDoc}
   */
  @Override
  protected File getSourceDirectory() {
    return this.sourceDirectory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected File getOutputDirectory() {
    return this.outputDirectory;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected int getStaleMillis() {
    return this.staleMillis;
  }

  /**
   * {@inheritDoc}
   */
  protected File[] getCompileSourceRoots() {
    return new File[]{getOutputDirectory()};
  }

  /**
   * {@inheritDoc}
   * 
   * @throws IOException
   */
  @Override
  protected String process(String input) {
    return Preprocessor.process(input);
  }

  @Override
  protected String getOutputExtension() {
    return ".lish";
  }

  @Override
  protected String getSourceExtension() {
    return ".lish-pp";
  }
}