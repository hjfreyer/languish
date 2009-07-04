package languish.prim.data;

import java.util.List;

import languish.lambda.DataFunction;
import languish.lambda.LObject;
import languish.lambda.Lambda;
import languish.lambda.Tuple;
import languish.prim.data.LGrammar.GrammarRule;
import languish.prim.data.LGrammar.HTreeWrapper;

import org.quenta.tedir.antonius.doc.StringDocument;
import org.quenta.tedir.hadrian.HTree;
import org.quenta.tedir.hadrian.INode;

public class LGrammars {

  public static final LGrammar EMPTY_GRAMMAR = new LGrammar();

  private LGrammars() {}

  public static final DataFunction INTERPRET_STATEMENT = new DataFunction() {
    @Override
    public Tuple apply(LObject obj) {
      final LGrammar grammar = (LGrammar) obj;

      return Lambda.prim(new DataFunction() {
        @Override
        public Tuple apply(LObject obj) {
          LSymbol code = (LSymbol) obj;

          INode root =
              grammar.getParser().parse(new StringDocument(code.stringValue()));

          return convertNode(root);
        }
      });
    }
  };

  public static final DataFunction ADD_RULE = new DataFunction() {
    @Override
    public Tuple apply(LObject obj) {
      final LGrammar grammar = (LGrammar) obj;

      return Lambda.prim(new DataFunction() {
        @Override
        public Tuple apply(LObject obj) {
          final LSymbol type = (LSymbol) obj;

          return Lambda.prim(new DataFunction() {
            @Override
            public Tuple apply(LObject obj) {
              final Tuple tree = (Tuple) obj;

              return Lambda.prim(new DataFunction() {
                @Override
                public Tuple apply(LObject obj) {
                  final Tuple exp = (Tuple) obj;

                  return Lambda.data(addRule(grammar, type.stringValue(), tree,
                      exp));
                }
              });
            }
          });
        }
      });
    }
  };

  private static LGrammar addRule(LGrammar grammar, String type, Tuple tree,
      Tuple exp) {

    HTree[] seq = new HTree[tree.size()];

    for (int i = 0; i < seq.length; i++) {
      seq[i] = ((HTreeWrapper) tree.get(i)).getTree();
    }

    return grammar.addRule(new GrammarRule(type, HTree.seq(seq), exp));
  }

  public static final DataFunction TERM = new DataFunction() {
    @Override
    public Tuple apply(LObject obj) {
      LSymbol symbol = (LSymbol) obj;

      return Lambda.data(new HTreeWrapper(HTree.term(symbol.stringValue())));
    }
  };

  public static final DataFunction VALUE = new DataFunction() {
    @Override
    public Tuple apply(LObject obj) {
      LSymbol symbol = (LSymbol) obj;

      return Lambda.data(new HTreeWrapper(HTree.value(symbol.stringValue())));
    }
  };

  public static final DataFunction NON_TERM = new DataFunction() {
    @Override
    public Tuple apply(LObject obj) {
      LSymbol symbol = (LSymbol) obj;

      return Lambda.data(new HTreeWrapper(HTree.nonterm(symbol.stringValue())));
    }
  };

  public static Tuple convertNode(INode node) {
    if (node.isText()) {
      return Lambda.data(LSymbol.of(node.asString()));
    } else if (node.isTagged()) {
      Tuple function = (Tuple) node.getTag();

      return Lambda.app(function, convertNode(node.getValue()));
    } else if (node.isList()) {
      List<INode> childNodes = node.getChildren();
      LObject[] children = new LObject[childNodes.size()];

      for (int i = 0; i < children.length; i++) {
        children[i] = convertNode(childNodes.get(i));
      }

      return Tuple.of(Lambda.TUPLE, Tuple.of(children));

    } else {
      throw new AssertionError();
    }
  }
}
