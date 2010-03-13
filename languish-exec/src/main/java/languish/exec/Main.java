package languish.exec;

import java.util.Arrays;
import java.util.List;

import languish.base.Primitive;
import languish.base.Term;
import languish.base.Terms;
import languish.interpreter.FileSystemDependencyManager;
import languish.interpreter.Interpreter;
import languish.interpreter.StandardLib;
import languish.util.PrimitiveTree;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.OptionBuilder;
import org.apache.commons.cli.Options;

import com.hjfreyer.util.Tree;

public class Main {
	public static void main(String[] main_args) throws Exception {
		Options opt = new Options();

		opt.addOption(OptionBuilder.withArgName("paths").hasArg().create("lp"));

		CommandLine cli = new GnuParser().parse(opt, main_args);

		List<String> paths = Arrays.asList(cli.getOptionValue("lp").split(","));

		System.out.println(paths);

		List<String> args = Arrays.asList(cli.getArgs());

		String moduleName = args.get(0);
		args = args.subList(1, args.size());

		Term module =
				Interpreter.loadAndInterpret(
						moduleName,
						new FileSystemDependencyManager(paths),
						StandardLib.NATIVE_FUNCTIONS);

		Term argsTerm = Terms.convertJavaObjectToTerm(PrimitiveTree.from(args));

		Tree<Primitive> result =
				Terms.convertTermToJavaObject(Terms.app(module, argsTerm));

		System.out.println(result);
	}
}
