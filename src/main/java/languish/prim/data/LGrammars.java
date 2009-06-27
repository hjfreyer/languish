package languish.prim.data;

import java.util.List;

import languish.lambda.Application;
import languish.lambda.Expression;
import languish.lambda.NativeFunction;
import languish.lambda.Wrapper;
import languish.prim.data.LGrammar.GrammarRule;
import languish.prim.data.LGrammar.HTreeWrapper;

import org.quenta.tedir.antonius.doc.StringDocument;
import org.quenta.tedir.hadrian.HTree;
import org.quenta.tedir.hadrian.INode;

public class LGrammars {

  public static final LGrammar EMPTY_GRAMMAR = new LGrammar();

  private LGrammars() {}

  public static final NativeFunction INTERPRET_STATEMENT =
      new NativeFunction("INTERPRET_STATEMENT") {
        @Override
        public languish.lambda.Expression apply(LObject obj) {
          final LGrammar grammar = (LGrammar) obj;

          return new NativeFunction("INTERPRET_STATEMENT*") {
            @Override
            public Expression apply(LObject obj) {
              LSymbol code = (LSymbol) obj;

              INode root =
                  grammar.getParser().parse(
                      new StringDocument(code.stringValue()));

              return convertNode(root);
            }
          };
        }
      };

  public static final NativeFunction ADD_RULE = new NativeFunction("ADD_RULE") {
    @Override
    public Expression apply(LObject obj) {
      final LGrammar grammar = (LGrammar) obj;

      return new NativeFunction("ADD_RULE*") {
        @Override
        public Expression apply(LObject obj) {
          final LSymbol type = (LSymbol) obj;

          return new NativeFunction("ADD_RULE*") {
            @Override
            public Expression apply(LObject obj) {
              final LComposite tree = (LComposite) obj;

              return new NativeFunction("ADD_RULE*") {
                @Override
                public Expression apply(LObject obj) {
                  final Expression exp = (Expression) obj;

                  return Wrapper.of(addRule(grammar, type.stringValue(), tree,
                      exp));
                }
              };
            }
          };
        }
      };
    }
  };

  private static LGrammar addRule(LGrammar grammar, String type,
      LComposite tree, Expression exp) {

    HTree[] seq = new HTree[tree.size()];

    for (int i = 0; i < seq.length; i++) {
      seq[i] = ((HTreeWrapper) tree.getArray()[i]).getTree();
    }

    return grammar.addRule(new GrammarRule(type, HTree.seq(seq), exp));
  }

  public static final NativeFunction TERM = new NativeFunction("TERM") {
    @Override
    public Expression apply(LObject obj) {
      LSymbol symbol = (LSymbol) obj;

      return Wrapper.of(new HTreeWrapper(HTree.term(symbol.stringValue())));
    }
  };

  public static final NativeFunction VALUE = new NativeFunction("VALUE") {
    @Override
    public Expression apply(LObject obj) {
      LSymbol symbol = (LSymbol) obj;

      return Wrapper.of(new HTreeWrapper(HTree.value(symbol.stringValue())));
    }
  };

  public static final NativeFunction NON_TERM = new NativeFunction("NON_TERM") {
    @Override
    public Expression apply(LObject obj) {
      LSymbol symbol = (LSymbol) obj;

      return Wrapper.of(new HTreeWrapper(HTree.nonterm(symbol.stringValue())));
    }
  };

  public static Expression convertNode(INode node) {
    if (node.isText()) {
      return Wrapper.of(LSymbol.of(node.asString()));
    } else if (node.isTagged()) {
      Expression function = (Expression) node.getTag();

      return Application.of(function, convertNode(node.getValue()));
    } else if (node.isList()) {
      List<INode> childNodes = node.getChildren();
      LObject[] children = new LObject[childNodes.size()];

      for (int i = 0; i < children.length; i++) {
        children[i] = convertNode(childNodes.get(i));
      }

      return Wrapper.of(LComposite.of(children));

    } else {
      throw new AssertionError();
    }
  }
}
