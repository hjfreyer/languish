package languish.tools.parsing;

import java.util.List;

import languish.parsing.GrammarModule;
import languish.parsing.Sequence;

import com.google.common.collect.ImmutableList;
import com.hjfreyer.util.Pair;

public class LambdaPlusGrammar {

  @SuppressWarnings("unchecked")
  public static final List<Pair<String, String>> TOKENS = ImmutableList.of( // 
      Pair.of("CONS", "CONS"),
      Pair.of("CAR", "CAR"),
      Pair.of("CDR", "CDR"),
      Pair.of("IMPORT_DIRECTIVE", "#import"),
      Pair.of("IMPORT_ID", "[a-zA-Z0-9_/]+"),
      Pair.of(",", ","),
      Pair.of(";;", ";;"));

  public static final List<Sequence> RULES =
      ImmutableList.of( //
          // Compilation unit
          Sequence.of("COMPILATION_UNIT", "NON_IMPORTING_UNIT", "TERM"),
          Sequence.of(
              "COMPILATION_UNIT",
              "IMPORTING_UNIT",
              "IMPORT_STATEMENT",
              "TERM"),
          // Import stuff
          Sequence.of(
              "IMPORT_STATEMENT",
              "IMPORT_STATEMENT",
              "IMPORT_DIRECTIVE",
              "IMPORT_ID",
              "IMPORT_TAIL",
              ";;"),
          Sequence.of("IMPORT_TAIL", "IMPORT_TAIL_END"),
          Sequence.of(
              "IMPORT_TAIL",
              "IMPORT_TAIL_ITEM",
              ",",
              "IMPORT_ID",
              "IMPORT_TAIL"),
          // Term extensions
          Sequence.of("TERM", "ABS_TERM", "[", "ABS", "TERM", "]"),
          Sequence.of("TERM", "CONS_TERM", "[", "CONS", "TERM", "TERM", "]"),
          Sequence.of("TERM", "CAR_TERM", "[", "CAR", "TERM", "]"),
          Sequence.of("TERM", "CDR_TERM", "[", "CDR", "TERM", "]"),
          Sequence.of(
              "TERM",
              "SHORT_PRIM_TERM",
              "[",
              "PRIMITIVE",
              "PRIM_LIT",
              "]"),
          Sequence.of("TERM", "SHORT_REF_TERM", "[", "REF", "INTEGER_LIT", "]")

      );

  public static final GrammarModule GRAMMAR =
      TermParser.TERM_GRAMMAR.extend(new GrammarModule("COMPILATION_UNIT",
          TOKENS, ImmutableList.<String> of(), RULES));

}
