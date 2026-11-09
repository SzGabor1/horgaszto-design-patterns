package fishing.method;

import fishing.fish.Species;

import java.util.Map;
import java.util.Random;


public class Spinning implements FishingMethod {

    private static final Map<Species, Integer> WEIGHTS = Map.of(
            Species.CSUKA, 40,
            Species.SULLO, 35,
            Species.BALIN, 25);

    @Override
    public String name() { return "pergető"; }

    @Override
    public double biteChance(int waitedTurns) {
        return Math.max(0.4 - 0.1 * waitedTurns, 0.05);
    }

    @Override
    public Species chooseSpecies(Random random, int waitedTurns) {
        return WeightedPick.pick(random, WEIGHTS);
    }
}
