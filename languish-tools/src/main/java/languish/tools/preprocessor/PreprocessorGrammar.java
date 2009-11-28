package languish.tools.preprocessor;

import java.util.List;

import com.google.common.collect.ImmutableList;
import com.hjfreyer.util.Pair;

public class PreprocessorGrammar {

  @SuppressWarnings("unchecked")
  public static final List<Pair<String, String>> TOKENS = ImmutableList.of( // 
      Pair.of("CONS", "CONS"),
      Pair.of("CAR", "CAR"),
      Pair.of("CDR", "CDR"));

}
