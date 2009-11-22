package languish.tools.maven;

import java.io.File;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.codehaus.plexus.util.DirectoryScanner;

public abstract class AbstractPreprocessorMojo extends AbstractMojo {
  protected abstract File getSourceDirectory();

  protected abstract File getOutputDirectory();

  protected abstract String getSourceExtension();

  protected abstract String getOutputExtension();

  /**
   * Execute the tool.
   * 
   * @throws MojoExecutionException
   *           If the invocation of the tool failed.
   * @throws MojoFailureException
   *           If the tool reported a non-zero exit code.
   */
  public void execute() throws MojoExecutionException, MojoFailureException {
    scanForSources();
    //
    // if ( grammarInfos == null )
    // {
    // getLog().info( "Skipping non-existing source directory: " +
    // getSourceDirectory() );
    // return;
    // }
    // else if ( grammarInfos.length <= 0 )
    // {
    // getLog().info( "Skipping - all parsers are up to date" );
    // }
    // else
    // {
    // determineNonGeneratedSourceRoots();
    //
    // if ( StringUtils.isEmpty( grammarEncoding ) )
    // {
    // getLog().warn(
    // "File encoding for grammars has not been configured"
    // + ", using platform default encoding, i.e. build is platform dependent!"
    // );
    // }
    //
    // for ( int i = 0; i < grammarInfos.length; i++ )
    // {
    // processGrammar( grammarInfos[i] );
    // }
    //
    // getLog().info( "Processed " + grammarInfos.length + " grammar" + (
    // grammarInfos.length != 1 ? "s" : "" ) );
    // }
    //
    // Collection compileSourceRoots = new LinkedHashSet( Arrays.asList(
    // getCompileSourceRoots() ) );
    // for ( Iterator it = compileSourceRoots.iterator(); it.hasNext(); )
    // {
    // addSourceRoot( (File) it.next() );
    // }
  }

  private void scanForSources() throws MojoExecutionException {
    if (!getSourceDirectory().isDirectory()) {
      return;
    }

    getLog().debug("Scanning for grammars: " + getSourceDirectory());
    try {
      DirectoryScanner scanner = new DirectoryScanner();
      scanner.setFollowSymlinks(true);
      scanner.setBasedir(getSourceDirectory());
      scanner.addDefaultExcludes();

      scanner.scan();

      String[] includedFiles = scanner.getIncludedFiles();
      for (int i = 0; i < includedFiles.length; i++) {
        String includedFile = includedFiles[i];
        getLog().info("Found: " + includedFile);

        // if ( this.getOutputDirectory() != null )
        // {
        // File sourceFile = grammarInfo.getGrammarFile();
        // File[] targetFiles = getTargetFiles( this.outputDirectory,
        // includedFile, grammarInfo );
        // for ( int j = 0; j < targetFiles.length; j++ )
        // {
        // File targetFile = targetFiles[j];
        // if ( !targetFile.exists()
        // || targetFile.lastModified() + this.staleMillis <
        // sourceFile.lastModified() )
        // {
        // this.includedGrammars.add( grammarInfo );
        // break;
        // }
        // }
        // }
        // else
        // {
        // this.includedGrammars.add( grammarInfo );
        // }
      }
    } catch (Exception e) {
      throw new MojoExecutionException("Failed to scan for grammars: "
          + getSourceDirectory(), e);
    }
    // getLog().debug( "Found grammars: " + Arrays.asList( grammarInfos ) );

  }

}
