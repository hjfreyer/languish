package languish.tools.maven;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.plexus.util.DirectoryScanner;

public abstract class AbstractPreprocessorMojo extends AbstractMojo {
  protected abstract File getSourceDirectory();

  protected abstract File getOutputDirectory();

  protected abstract String getSourceExtension();

  protected abstract String getOutputExtension();

  protected abstract String process(String in);

  protected abstract int getStaleMillis();

  /**
   * Execute the tool.
   * 
   * @throws MojoExecutionException
   *           If the invocation of the tool failed.
   * @throws MojoFailureException
   *           If the tool reported a non-zero exit code.
   */
  public void execute() throws MojoExecutionException, MojoFailureException {
    Map<File, File> sources = getSources();

    for (File source : sources.keySet()) {
      getLog().info("Processing " + source);

      File target = sources.get(source);

      try {
        target.getParentFile().mkdirs();

        Reader reader = new FileReader(source);
        Writer writer = new FileWriter(target);

        char[] sourceContents = new char[(int) source.length()];
        reader.read(sourceContents);

        String targetContents = process(new String(sourceContents));

        writer.write(targetContents);
        writer.close();
      } catch (IOException e) {
        getLog().error(e);
        throw new MojoFailureException("Failed while processing file: "
            + source);
      }
    }
  }

  private Map<File, File> getSources() throws MojoExecutionException {
    Map<File, File> result = new HashMap<File, File>();

    if (!getSourceDirectory().isDirectory()) {
      return result;
    }

    getLog().debug("Scanning for source files: " + getSourceDirectory());
    try {
      DirectoryScanner scanner = new DirectoryScanner();
      scanner.setFollowSymlinks(true);
      scanner.setBasedir(getSourceDirectory());
      scanner.addDefaultExcludes();

      scanner.scan();

      for (String sourceFilename : scanner.getIncludedFiles()) {
        if (!sourceFilename.endsWith(getSourceExtension())) {
          continue;
        }

        String filebase =
            sourceFilename.substring(0, sourceFilename.length()
                - getSourceExtension().length());

        File sourceFile = new File(getSourceDirectory(), sourceFilename);
        File targetFile =
            new File(getOutputDirectory(), filebase + getOutputExtension());

        if (!targetFile.exists()
            || targetFile.lastModified() + getStaleMillis() < sourceFile
                .lastModified()) {
          result.put(sourceFile, targetFile);
        }
      }
    } catch (Exception e) {
      throw new MojoExecutionException("Failed to scan for grammars: "
          + getSourceDirectory(), e);
    }
    getLog().debug("Found files: " + result);

    return result;
  }

}
