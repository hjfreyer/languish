package languish.parsing;

//package languish.prim.data;
//
//import java.util.List;
//
//import languish.lambda.DataFunction;
//import languish.lambda.LObject;
//import languish.lambda.Tuple;
//import languish.prim.data.LGrammar.GrammarRule;
//import languish.prim.data.LGrammar.HTreeWrapper;
//
//import org.quenta.tedir.antonius.doc.StringDocument;
//import org.quenta.tedir.hadrian.HTree;
//import org.quenta.tedir.hadrian.INode;
//
//import com.apple.eawt.Application;
//
//public class LGrammars {
//
//  public static final LGrammar EMPTY_GRAMMAR = new LGrammar();
//
//  private LGrammars() {}
//
//  public static final DataFunction INTERPRET_STATEMENT = new DataFunction() {
//    @Override
//    public Tuple apply(LObject obj) {
//      final LGrammar grammar = (LGrammar) obj;
//
//      return new DataFunction("INTERPRET_STATEMENT*") {
//        @Override
//        public Expression apply(LObject obj) {
//          LSymbol code = (LSymbol) obj;
//
//          INode root =
//              grammar.getParser().parse(new StringDocument(code.stringValue()));
//
//          return convertNode(root);
//        }
//      };
//    }
//  };
//
//  public static final DataFunction ADD_RULE = new DataFunction("ADD_RULE") {
//    @Override
//    public Expression apply(LObject obj) {
//      final LGrammar grammar = (LGrammar) obj;
//
//      return new DataFunction("ADD_RULE*") {
//        @Override
//        public Expression apply(LObject obj) {
//          final LSymbol type = (LSymbol) obj;
//
//          return new DataFunction("ADD_RULE*") {
//            @Override
//            public Expression apply(LObject obj) {
//              final LComposite tree = (LComposite) obj;
//
//              return new DataFunction("ADD_RULE*") {
//                @Override
//                public Expression apply(LObject obj) {
//                  final Expression exp = (Expression) obj;
//
//                  return Data
//                      .of(addRule(grammar, type.stringValue(), tree, exp));
//                }
//              };
//            }
//          };
//        }
//      };
//    }
//  };
//
//  private static LGrammar addRule(LGrammar grammar, String type,
//      LComposite tree, Expression exp) {
//
//    HTree[] seq = new HTree[tree.size()];
//
//    for (int i = 0; i < seq.length; i++) {
//      seq[i] = ((HTreeWrapper) tree.getArray()[i]).getTree();
//    }
//
//    return grammar.addRule(new GrammarRule(type, HTree.seq(seq), exp));
//  }
//
//  public static final DataFunction TERM = new DataFunction("TERM") {
//    @Override
//    public Expression apply(LObject obj) {
//      LSymbol symbol = (LSymbol) obj;
//
//      return Data.of(new HTreeWrapper(HTree.term(symbol.stringValue())));
//    }
//  };
//
//  public static final DataFunction VALUE = new DataFunction("VALUE") {
//    @Override
//    public Expression apply(LObject obj) {
//      LSymbol symbol = (LSymbol) obj;
//
//      return Data.of(new HTreeWrapper(HTree.value(symbol.stringValue())));
//    }
//  };
//
//  public static final DataFunction NON_TERM = new DataFunction("NON_TERM") {
//    @Override
//    public Expression apply(LObject obj) {
//      LSymbol symbol = (LSymbol) obj;
//
//      return Data.of(new HTreeWrapper(HTree.nonterm(symbol.stringValue())));
//    }
//  };
//
//  public static Expression convertNode(INode node) {
//    if (node.isText()) {
//      return Data.of(LSymbol.of(node.asString()));
//    } else if (node.isTagged()) {
//      Expression function = (Expression) node.getTag();
//
//      return Application.of(function, convertNode(node.getValue()));
//    } else if (node.isList()) {
//      List<INode> childNodes = node.getChildren();
//      LObject[] children = new LObject[childNodes.size()];
//
//      for (int i = 0; i < children.length; i++) {
//        children[i] = convertNode(childNodes.get(i));
//      }
//
//      return Data.of(LComposite.of(children));
//
//    } else {
//      throw new AssertionError();
//    }
//  }
//}
