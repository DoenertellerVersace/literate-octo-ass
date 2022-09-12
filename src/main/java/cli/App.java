package cli;

import static java.lang.String.format;
import static java.lang.System.exit;
import static java.lang.System.out;
import static java.lang.Thread.sleep;
import static java.util.Arrays.stream;

import java.util.Collection;
import java.util.Locale;
import java.util.Scanner;
import java.util.function.Function;
import org.apache.commons.text.similarity.IntersectionResult;
import org.apache.commons.text.similarity.IntersectionSimilarity;
import org.apache.commons.text.similarity.JaroWinklerSimilarity;
import org.apache.commons.text.similarity.LevenshteinDetailedDistance;
import org.apache.commons.text.similarity.SimilarityScore;


public class App {

  private static final Scanner scanner;
  private static final Context context;
  public static final String LINE_S_$ = "line %s:\n$ ";
  private static boolean running;

  static {
    running = true;
    scanner = new Scanner(System.in);
    context = new Context();
    out.println("Hallo!");
  }

  public static void main(String[] args) throws InterruptedException {
    while (running) {
      String input = getLine("").toLowerCase(Locale.ROOT);
      if (input.trim().equals("compare")) {
        String lineA = getLine(format(LINE_S_$, "A"));
        String lineB = getLine(format(LINE_S_$, "B"));
        SimilarityScore<IntersectionResult> intersection = new IntersectionSimilarity<>(convertToStringArray());
        CharSequence left = convertToCharSequence(lineA);
        CharSequence right = convertToCharSequence(lineB);
        out.println(intersection.apply(left, right));
        out.println("Jaro Winkler Similarity Score: " +
            new JaroWinklerSimilarity().apply(left, right));
        out.println("Levenshtein Detailed Distance: " +
            new LevenshteinDetailedDistance().apply(left, right));
      } else {
        running = context.analyze(input);
        out.println(context.getAnswer());
      }
      reset();
    }
    sleep(2000);
    exit(0);
  }

  private static CharSequence convertToCharSequence(String lineA) {
    return lineA.subSequence(0, lineA.length());
  }

  private static Function<CharSequence, Collection<String>> convertToStringArray() {
    return sequence -> stream(sequence.toString().replaceAll("[.?!,;:\\-].*", " ").split(" ")).toList();
  }

  private static void reset() {
    scanner.reset();
    context.reset();
  }

  static String getLine(String prompt) {
    out.print(prompt);
    return scanner.nextLine();
  }
}
