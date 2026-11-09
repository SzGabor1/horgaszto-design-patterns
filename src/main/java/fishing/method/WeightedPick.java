package fishing.method;

import fishing.fish.Species;

import java.util.Map;
import java.util.Random;

final class WeightedPick {

    private WeightedPick() { }

    static Species pick(Random random, Map<Species, Integer> weights) {
        int total = weights.values().stream().mapToInt(Integer::intValue).sum();
        int roll = random.nextInt(total);
        for (Map.Entry<Species, Integer> e : weights.entrySet()) {
            roll -= e.getValue();
            if (roll < 0) {
                return e.getKey();
            }
        }
        throw new IllegalStateException("unreachable");
    }
}
