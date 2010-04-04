package languish.serialization;

import languish.base.Operation;
import languish.base.Primitive;
import languish.base.Term;
import languish.base.Terms;

import com.hjfreyer.util.Tree;

public class StringTreeSerializer {
	@SuppressWarnings("unchecked")
	public static Tree<String> serialize(Term term) {
		if (term.getOperation() == Operation.REF) {
			return Tree.inode(Tree.leaf("REF"), //
					Tree.leaf(term.getFirst().toString()),
					serialize((Term) term.getSecond()));
		}

		if (term.getOperation() == Operation.PRIMITIVE) {
			return Tree.copyOfList("PRIMITIVE", //
					serializePrimitive((Primitive) term.getFirst()),
					serializePrimitive((Primitive) term.getSecond()));
		}

		if (term.getOperation() == Operation.NATIVE_APPLY) {
			return Tree.inode(Tree.leaf("NATIVE_APPLY"), //
					Tree.leaf(term.getFirst().toString()),
					serialize((Term) term.getFirst()));
		}

		Tree<String> op = Tree.leaf(term.getOperation().toString());
		Tree<String> first = serialize((Term) term.getFirst());
		Tree<String> second = serialize((Term) term.getSecond());

		return Tree.inode(op, first, second);
	}

	// public static Term decompileTermToTermAst(Term term) {
	// Tree<String> ast = serialize(term);
	// Tree<Primitive> astPrim = Primitives.treeToPrimitiveTree(ast);
	//
	// return Terms.fromPrimitiveTree(astPrim);
	// }

	public static String serializePrimitive(Primitive prim) {
		if (prim.isInteger()) {
			return "i" + prim.asInteger();
		}

		if (prim.isString()) {
			return "s" + prim.asString().replace("\"", "\\\"");
		}

		throw new AssertionError();
	}

	public static Term deserialize(Tree<String> ast) {
		String opName = ast.asList().get(0).asLeaf();
		Operation op = Operation.valueOf(opName);
		Tree<String> first = ast.asList().get(1);
		Tree<String> second = ast.asList().get(2);
		if (op == Operation.PRIMITIVE) {
			return Terms.primitive(deserializePrimitive(first.asLeaf()));
		} else if (op == Operation.REF) {
			return Terms.ref(Integer.parseInt(first.asLeaf()));
		} else if (op == Operation.NATIVE_APPLY) {
			return Terms.nativeApply(first.asLeaf(), deserialize(second));
		}
		return new Term(op, deserialize(first), deserialize(second));
	}

	public static Primitive deserializePrimitive(String str) {
		if (str.startsWith("i")) {
			return new Primitive(Integer.parseInt(str.substring(1)));
		}

		if (str.startsWith("s")) {
			return new Primitive(str.substring(1).replace("\\\"", "\""));
		}

		throw new IllegalArgumentException("Not a vaild primitive: " + str);
	}
}
