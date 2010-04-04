package languish.compiler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.hjfreyer.util.Pair;

public class BaseParser {

	private static final Pattern MODULE_REGEX =
			Pattern.compile("^#lang[ ]+([^ ]+);;(.*)$", Pattern.DOTALL);

	// public static Term parseFromString(String string) {
	// Pair<String, String> parserAndProgram = getParserAndProgram(string);
	// String parserName = parserAndProgram.getFirst();
	// String programBody = parserAndProgram.getSecond();
	//
	// Term programApplication = app(ref(3), primObj(programBody));
	//
	// return Modules.load(parserName, programApplication);
	// }

	public static Pair<String, String> getParserAndProgram(String input) {
		Matcher match = BaseParser.MODULE_REGEX.matcher(input.trim());

		if (!match.matches()) {
			return Pair.of(null, input);
		}

		String parser = match.group(1);
		String program = match.group(2);

		return Pair.of(parser, program);
	}

	private BaseParser() {
	}
}
