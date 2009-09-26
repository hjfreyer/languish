package languish.interpreter;

import java.io.FileNotFoundException;
import java.util.Scanner;

import languish.base.Tuple;

import com.google.common.collect.ImmutableList;

public class Shell {

  private final Tuple value = Tuple.of();

  private Shell() {
  }

  public static Tuple processReadable(Readable in) throws FileNotFoundException {
    StringBuilder input = new StringBuilder();
    Scanner scan = new Scanner(in);

    while (scan.hasNextLine()) {
      input.append(scan.nextLine()).append('\n');
    }

    return Interpreter.interpretStatement(input.toString(),
        new FileSystemDependencyManager(ImmutableList.of("languish")));
  }

  public Tuple getValue() {
    return value;
  }

  public static void main(String[] args) {
    new Shell();
  }
}
