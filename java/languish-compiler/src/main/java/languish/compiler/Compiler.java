package languish.compiler;

import java.util.List;

import languish.base.Term;
import languish.base.Terms;
import languish.compiler.error.CompilationError;
import languish.parsing.stringparser.StringTreeParser;
import languish.serialization.StringTreeSerializer;

import com.hjfreyer.util.Pair;
import com.hjfreyer.util.Tree;

public class Compiler {

	private final DependencyManager dependencyManager;

	public Compiler(DependencyManager dependencyManager) {
		this.dependencyManager = dependencyManager;
	}

	public Term compileResource(String resourceName) {
		try {
			String document = dependencyManager.getResource(resourceName);

			Pair<String, String> parserAndProgram =
					BaseParser.getParserAndProgram(document);

			String parserName = parserAndProgram.getFirst();
			String program = parserAndProgram.getSecond();

			if (parserName == null) {
				Tree<String> ast =
						StringTreeParser.getStringTreeParser().parse(program);
				Term body = StringTreeSerializer.deserialize(ast);

				// Make sure you pass in an empty dependency list
				return Terms.app(body, Terms.NULL);
			}

			Term parser = compileResource(parserName);
			Module module = Modules.fromParserAndDocument(parser, program);

			return compileModule(module);
		} catch (Exception e) {
			throw new CompilationError("Error encountered compiling module "
					+ resourceName, e);
		}
	}

	public Term compileModule(Module module) {
		List<String> depNames = module.getDeps();

		Term depList = Terms.NULL;
		for (int i = depNames.size() - 1; i >= 0; i--) {
			Term dep = compileResource(depNames.get(i));
			depList = Terms.cons(dep, depList);
		}

		Term content = StringTreeSerializer.deserialize(module.getAst());

		return Terms.app(content, depList);
	}

	// public static Term compileDependency(String depName, DependencyManager
	// depman) {
	// String resource = depman.getResource(depName);
	//
	// return Compiler.compile(resource, depman);
	// }
	//
	// public static Term compile(String doc, DependencyManager depman) {
	// Pair<String, String> parserAndProgram =
	// BaseParser.getParserAndProgram(doc);
	//
	// String parserName = parserAndProgram.getFirst();
	// String program = parserAndProgram.getSecond();
	//
	// if (parserName == null) {
	// Tree<String> ast = StringTreeParser.getStringTreeParser().parse(program);
	// return StringTreeSerializer.deserialize(ast);
	// }
	//
	// Term parser = compileDependency(parserName, depman);
	// Module module = parseModuleFromString(parser, doc);
	//
	// return compileModule(module, depman);
	// }

}