package fishing;

import fishing.event.CatchLogFile;
import fishing.event.Statistics;
import fishing.event.TrophyAnnouncer;
import fishing.method.BottomFishing;
import fishing.method.FishingMethod;
import fishing.method.FloatFishing;
import fishing.method.Spinning;

import java.nio.file.Path;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {

    private static final Map<String, FishingMethod> METHODS = Map.of(
            "uszo", new FloatFishing(),
            "fenekezo", new BottomFishing(),
            "pergeto", new Spinning());

    private static final String HELP = """
            Parancsok:
              dob                  bedobás
              var                  várakozás
              bevag                bevágás kapáskor
              teker                tekerés (fárasztáskor közelebb hozza a halat)
              enged                zsinór engedése (csökken a feszülés)
              modszer <nev>        uszos | fenekezo | pergeto
              stat                 mai fogás
              sugo                 ez a lista
              kilep                hazamenés
            """;

    public static void main(String[] args) {
        FishingSession session = new FishingSession(METHODS.get("uszos"), new Random());

        Statistics stats = new Statistics();
        session.addListener(stats);
        session.addListener(new CatchLogFile(Path.of("fogasi_naplo.txt")));
        session.addListener(new TrophyAnnouncer());

        System.out.println("Horgásztó");
        System.out.println(HELP);

        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.print("[" + session.method().name() + " | " + session.state().name() + "] > ");
            if (!in.hasNextLine()) {
                break;
            }
            String[] parts = in.nextLine().trim().toLowerCase().split("\\s+");
            switch (parts[0]) {
                case "dob" -> session.cast();
                case "var" -> session.waitTurn();
                case "bevag" -> session.strike();
                case "teker" -> session.reel();
                case "enged" -> session.loosen();
                case "modszer" -> changeMethod(session, parts);
                case "stat" -> System.out.println(stats.summary());
                case "sugo" -> System.out.println(HELP);
                case "kilep" -> {
                    System.out.println(stats.summary());
                    System.out.println("Szép napot, görbüljön!");
                    return;
                }
                case "" -> { }
                default -> System.out.println("Ismeretlen parancs. Írd be: sugo");
            }
        }
    }

    private static void changeMethod(FishingSession session, String[] parts) {
        FishingMethod method = parts.length > 1 ? METHODS.get(parts[1]) : null;
        if (method == null) {
            System.out.println("Használat: modszer uszos | fenekezo | pergeto");
            return;
        }
        session.changeMethod(method);
    }
}
