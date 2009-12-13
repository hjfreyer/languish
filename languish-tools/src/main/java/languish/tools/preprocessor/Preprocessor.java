package languish.tools.preprocessor;

public class Preprocessor {

  public static String process(String input) {
    return PreprocessorGrammar.GRAMMAR.getAstParser().parse(input).toString();
  }

  private Preprocessor() {
  }
}
