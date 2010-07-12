package languish.exec;

import java.util.Arrays;
import java.util.List;

import languish.base.Primitive;
import languish.base.Term;
import languish.base.Terms;
import languish.compiler.Compiler;
import languish.compiler.FileSystemDependencyManager;
import languish.interpreter.Interpreter;
import languish.util.PrimitiveTree;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;
import org.apache.log4j.BasicConfigurator;

import com.hjfreyer.util.Tree;

public class Main {

	private static Compiler compiler;

	public static CommandLine getCommandLine(String[] args) throws Exception {
		Options opt = new Options();

		opt.addOption(OptionBuilder.withArgName("paths").hasArg().create("lp"));

		return new GnuParser().parse(opt, args);

	}

	public static void main(String[] main_args) throws Exception {
		BasicConfigurator.configure();

		CommandLine cli = getCommandLine(main_args);

		List<String> paths = Arrays.asList(cli.getOptionValue("lp").split(","));
		compiler = new Compiler(new FileSystemDependencyManager(paths));

		List<String> args = Arrays.asList(cli.getArgs());

		String moduleName = args.get(0);
		args = args.subList(1, args.size());

		Term module = compiler.compileResource(moduleName);
		Term argsTerm = Terms.fromPrimitiveTree(PrimitiveTree.from(args));

		Term application = Terms.app(module, argsTerm);

		// Try toString
		try {
			Term toString = Terms.car(application);
			Tree<Primitive> result = Interpreter.convertTermToJavaObject(toString);
			String stringValue = result.asLeaf().asString();
			System.out.println(stringValue);
			return;
		} catch (Exception e) {
		}

		System.err.println("No toString, trying reduction...");

		try {
			System.out.println(Interpreter.convertTermToJavaObject(application));
			return;
		} catch (Exception e) {
		}

		System.err.println("Reduction failed, doing what we can...");

		System.out.println(Interpreter.reduceCompletely(application));
	}
}
