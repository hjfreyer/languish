package languish.api;

import java.util.List;
import java.util.Map;

import languish.base.NativeFunction;
import languish.base.Primitive;
import languish.parsing.GrammarModule;
import languish.parsing.Sequence;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.hjfreyer.util.Pair;
import com.hjfreyer.util.Tree;
import com.hjfreyer.util.Trees;

public class GrammarFunctions {

  public static final NativeFunction PARSE_TEXT = new NativeFunction() {
    @Override
    public Tree<Primitive> apply(Tree<Primitive> arg) {
      Tree<Primitive> grammarTree = arg.asList().get(0);
      String text = arg.asList().get(1).asLeaf().asString();

      GrammarModule grammar =
          moduleFromStringTree(Trees.transform(grammarTree, AS_STRING));

      return Trees.transform(grammar.getAstParser().parse(text), AS_PRIMITIVE);
    }
  };

  public static final Map<String, ? extends NativeFunction> FUNCTION_MAP =
      ImmutableMap.<String, NativeFunction> builder().put(
          "grammar/parse",
          PARSE_TEXT).build();

  private static final Function<Primitive, String> AS_STRING =
      new Function<Primitive, String>() {
        @Override
        public String apply(Primitive from) {
          return from.asString();
        }
      };

  private static final Function<String, Primitive> AS_PRIMITIVE =
      new Function<String, Primitive>() {
        @Override
        public Primitive apply(String from) {
          return new Primitive(from);
        }
      };

  private static GrammarModule moduleFromStringTree(Tree<String> struct) {
    String root = struct.asList().get(0).asLeaf();
    List<Tree<String>> tokenTypesList = struct.asList().get(1).asList();
    List<Tree<String>> ignored = struct.asList().get(2).asList();
    List<Tree<String>> grammarRulesList = struct.asList().get(3).asList();

    return new GrammarModule(root, tokenTypesFromStringTree(tokenTypesList),
        ignoredFromStringTree(ignored),
        sequencesFromStringTree(grammarRulesList));
  }

  private static List<Pair<String, String>> tokenTypesFromStringTree(
      List<Tree<String>> struct) {
    return Lists.transform(
        struct,
        new Function<Tree<String>, Pair<String, String>>() {
          @Override
          public Pair<String, String> apply(Tree<String> from) {
            return Pair.of(from.asList().get(0).asLeaf(), from.asList().get(1)
                .asLeaf());
          }
        });
  }

  private static List<String> ignoredFromStringTree(List<Tree<String>> struct) {
    return Lists.transform(struct, Tree.<String> asLeafFunction());
  }

  private static List<Sequence> sequencesFromStringTree(
      List<Tree<String>> struct) {
    return Lists.transform(struct, new Function<Tree<String>, Sequence>() {

      @Override
      public Sequence apply(Tree<String> from) {
        List<String> args =
            Lists.transform(from.asList(), Tree.<String> asLeafFunction());

        String nonterminal = args.get(0);
        String name = args.get(1);
        List<String> content = args.subList(2, args.size());

        return new Sequence(nonterminal, name, content);
      }
    });
  }
}
