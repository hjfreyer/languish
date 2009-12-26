package languish.tools.preprocessor;

import com.hjfreyer.util.Tree;import languish.base.Primitive;

public class Preprocessor {

  public static String process(String input) {
    Tree<String> ast = PreprocessorGrammar.GRAMMAR.getAstParser().parse(input);

    return PreprocessorSemantic.PREPROCESSOR_SEMANTIC.process(ast).toString();
  }

  private Preprocessor() {
  }
}
