package languish.parsing.api;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.codehaus.jparsec.Parser;
import org.codehaus.jparsec.Parsers;
import org.codehaus.jparsec.functors.Map;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.hjfreyer.util.Tree;

public class Sequence {

	private final String nonterminal;
	private final String name;
	private final List<String> content;

	public Sequence(String nonterminal, String name, List<String> content) {
		this.nonterminal = nonterminal;
		this.name = name;
		this.content = content;
	}

	public Parser<Tree<String>> toParser(
			final HashMap<String, Parser.Reference<Tree<String>>> refs) {

		List<String> subExpressions = content;
		List<Parser<Tree<String>>> childParsers =
				Lists.transform(
						subExpressions,
						new Function<String, Parser<Tree<String>>>() {
							public Parser<Tree<String>> apply(String from) {
								if (!refs.containsKey(from)) {
									throw new IllegalArgumentException(
											"No parser reference for child: " + from);
								}

								return refs.get(from).lazy();
							}
						});

		Parser<Tree<String>> result = Parsers.list(childParsers).map(listToTree());

		return result.map(new Map<Tree<String>, Tree<String>>() {
			@SuppressWarnings("unchecked")
			@Override
			public Tree<String> map(Tree<String> from) {
				return Tree.inode(Tree.leaf(name), from);
			}
		});
	}

	public static Sequence of(String nonterm, String name, List<String> sequence) {
		return new Sequence(nonterm, name, sequence);
	}

	public static Sequence of(String nonterm, String name, String... sequence) {
		return of(nonterm, name, Arrays.asList(sequence));
	}

	private static Map<List<Tree<String>>, Tree<String>> listToTree() {
		return new Map<List<Tree<String>>, Tree<String>>() {
			@Override
			public Tree<String> map(List<Tree<String>> from) {
				return Tree.inode(from);
			}
		};
	}

	public String getNonterminal() {
		return nonterminal;
	}

	public String getName() {
		return name;
	}

	public List<String> getContent() {
		return content;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result =
				prime * result + ((nonterminal == null) ? 0 : nonterminal.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sequence other = (Sequence) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (nonterminal == null) {
			if (other.nonterminal != null)
				return false;
		} else if (!nonterminal.equals(other.nonterminal))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Sequence [content="
				+ content
				+ ", name="
				+ name
				+ ", nonterminal="
				+ nonterminal
				+ "]";
	}

}
