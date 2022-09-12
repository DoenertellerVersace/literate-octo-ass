package cli;

import java.util.Locale;
import java.util.Scanner;

import static java.lang.System.exit;
import static java.lang.System.out;
import static java.lang.Thread.sleep;


public class App {

  private static final Scanner scanner;
  private static final Context context;
  private static boolean running;

  static {
    running = true;
    scanner = new Scanner(System.in);
    context = new Context();
    out.println("Hallo!");
  }

  public static void main(String[] args) throws InterruptedException {
    while (running) {
      running = context.analyze(getLine().toLowerCase(Locale.ROOT));
      out.println(context.getAnswer());
      reset();
    }
    sleep(2000);
    exit(0);
  }

  private static void reset() {
    scanner.reset();
    context.reset();
  }

  static String getLine() {
    return scanner.nextLine();
  }
}
