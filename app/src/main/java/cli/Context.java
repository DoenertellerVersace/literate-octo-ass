package cli;

import java.time.LocalDateTime;
import java.time.format.TextStyle;
import java.util.*;

import static java.lang.Math.random;

public class Context {

    private static List<String> keywords;
    private static Map<List<String>, String> context;
    private static List<String> startKeyWords;
    private static String answer;
    private static List<String> hits;

    static {
        hits = new LinkedList<>();
        context = new HashMap<>();
        startKeyWords = new LinkedList<>();
        startKeyWords.addAll(List.of("hallo", "?", "nein"));
        context.put(new ArrayList<>(List.of("")), "hallo");
        keywords = List.copyOf(startKeyWords);
    }

    public boolean analyze(String input) {
        reset();
        boolean clock = (input.contains("?") & input.contains("spät")) | (input.contains("?") & input.contains("uhr"));
        boolean dukannst = input.contains("?") & input.contains("du") & input.contains("kann");
        boolean was = input.contains("?") & input.contains("was");
        boolean tag = input.contains("?") & input.contains("tag");
        boolean raus = input.contains("raus");
        boolean ok = input.startsWith("ok");
        boolean dufrage = input.contains("du") & input.contains("?");
        boolean du = input.contains("du ");
        boolean doch = input.contains("nein");
        boolean question = input.contains("?");
        LocalDateTime now = LocalDateTime.now();
        if (dukannst) {
            answer = "Puh Brot kann schimmeln was kannst du?";
        } else if (was) {
            answer = "Alles!";
        } else if (clock) {
            String minute = String.valueOf(now.getMinute());
            String hour = String.valueOf(now.getHour());
            minute = minute.length() > 1 ? minute : "0" + minute;
            hour = hour.length() > 1 ? hour : "0" + hour;
            answer = "Es ist %s:%s Uhr.".formatted(hour, minute);
        } else if (tag) {
            answer = "Es ist der %d. %s %d.".formatted(now.getDayOfMonth(), now.getMonth().getDisplayName(TextStyle.FULL, Locale.GERMAN), now.getYear());
        } else if (ok) {
            answer = "";
            for (char c : input.toCharArray()) {
                String s = String.valueOf(c);
                answer = "%s%s".formatted(answer, (random() < 0.6) ? s.toLowerCase() : s.toUpperCase());
            }
        } else if (dufrage) {
            answer = "Frag mich was besseres...";
        } else if (du) {
            answer = "Ich bin wie ich bin!";
        } else if (doch) {
            answer = "Doch!";
        } else if (question) {
            answer = (random() < 0.6) ? "Ja" : "Nein";
        } else if (raus) {
            answer = "Goodbye! :)";
            return false;
        }
        return true;
    }

    public String getAnswer() {
        return answer;
    }

    public void reset() {
        answer = (random() < 0.5) ? "Hm." : "Ach was!";
    }


}
