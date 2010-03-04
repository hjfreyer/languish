package languish.exec;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.GnuParser;
import org.apache.commons.cli.Options;

public class Main {
	public static void main(String[] args) throws Exception {
		Options opt = new Options();

		opt.addOption("lp", false, "path to root of languish files");

		CommandLine cli = new GnuParser().parse(opt, args);

		System.out.println(cli.getArgList());
	}
}
