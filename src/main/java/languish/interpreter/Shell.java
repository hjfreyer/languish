package languish.interpreter;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.Scanner;

import languish.base.LObject;
import languish.base.Tuple;

public class Shell {

  private final Scanner source;

  private final Interpreter interpreter = new Interpreter();

  private LObject last = Tuple.of();

  public Shell() {
    this(new InputStreamReader(System.in));
  }

  public Shell(String filename) throws FileNotFoundException {
    this(new FileReader(filename));
  }

  public Shell(Readable in) {
    source = new Scanner(in).useDelimiter(";;");

    while (source.hasNext()) {
      String next = source.next();

      if (next.trim().length() > 0) {
        last = interpreter.processStatement(next);
      }
    }
  }

  public LObject getLast() {
    return last;
  }

  public static void main(String[] args) {
    new Shell();
  }
}
