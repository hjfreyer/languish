package languish.tools.preprocessor;

import java.util.List;

import languish.parsing.GrammarModule;
import languish.parsing.Sequence;
import languish.tools.parsing.TermParser;

import com.google.common.collect.ImmutableList;
import com.hjfreyer.util.Pair;

public class PreprocessorGrammar {

  @SuppressWarnings("unchecked")
  public static final List<Pair<String, String>> TOKENS = ImmutableList.of( // 
      Pair.of("CONS", "CONS"),
      Pair.of("CAR", "CAR"),
      Pair.of("CDR", "CDR"));

  public static final List<Sequence> RULES =
      ImmutableList
          .of( //
              Sequence.of("TERM", "ABS_TERM", "[", "ABS", "TERM", "]"),
              Sequence.of(
                  "TERM",
                  "CONS_TERM",
                  "[",
                  "CONS",
                  "TERM",
                  "TERM",
                  "]"),
              Sequence.of("TERM", "CAR_TERM", "[", "CAR", "TERM", "]"),
              Sequence.of("TERM", "CDR_TERM", "[", "CDR", "TERM", "]"),
              Sequence.of(
                  "TERM",
                  "SHORT_PRIM_TERM",
                  "[",
                  "PRIMITIVE",
                  "PRIM_LIT",
                  "]"),
              Sequence.of(
                  "TERM",
                  "SHORT_REF_TERM",
                  "[",
                  "REF",
                  "INTEGER_LIT",
                  "]"));

  public static final GrammarModule GRAMMAR =
      TermParser.TERM_GRAMMAR.extend(new GrammarModule("TERM", TOKENS,
          ImmutableList.<String> of(), RULES));

}
