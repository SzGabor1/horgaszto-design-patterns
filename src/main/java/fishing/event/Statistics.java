package fishing.event;

import fishing.fish.Fish;
import fishing.fish.Species;

import java.util.EnumMap;
import java.util.Map;

public class Statistics implements CatchListener {

    private final Map<Species, Integer> countBySpecies = new EnumMap<>(Species.class);
    private double totalKg;
    private Fish biggest;
    private int escaped;

    @Override
    public void onCatch(Fish fish) {
        countBySpecies.merge(fish.species(), 1, Integer::sum);
        totalKg += fish.weightKg();
        if (biggest == null || fish.weightKg() > biggest.weightKg()) {
            biggest = fish;
        }
    }

    @Override
    public void onEscape(Species species, String reason) {
        escaped++;
    }

    public String summary() {
        if (countBySpecies.isEmpty()) {
            return "Még nem fogtál semmit. Elszalasztott hal: " + escaped;
        }
        StringBuilder sb = new StringBuilder("--- Mai fogás ---\n");
        countBySpecies.forEach((species, count) ->
                sb.append("  %-8s %d db%n".formatted(species.displayName(), count)));
        sb.append("  Összesen:      %.2f kg%n".formatted(totalKg));
        sb.append("  Legnagyobb:    ").append(biggest).append(System.lineSeparator());
        sb.append("  Elszalasztott: ").append(escaped);
        return sb.toString();
    }
}
