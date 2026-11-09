package fishing.method;

import fishing.fish.Species;

import java.util.Map;
import java.util.Random;

public class FloatFishing implements FishingMethod {

    private static final Map<Species, Integer> WEIGHTS = Map.of(
            Species.KESZEG, 70,
            Species.PONTY, 25,
            Species.AMUR, 5);

    @Override
    public String name() { return "úszós"; }

    @Override
    public double biteChance(int waitedTurns) {
        return 0.5;
    }

    @Override
    public Species chooseSpecies(Random random, int waitedTurns) {
        return WeightedPick.pick(random, WEIGHTS);
    }
}
