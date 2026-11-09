package fishing.method;

import fishing.fish.Species;

import java.util.Map;
import java.util.Random;


public class BottomFishing implements FishingMethod {

    @Override
    public String name() { return "fenekező"; }

    @Override
    public double biteChance(int waitedTurns) {
        return Math.min(0.1 + 0.1 * waitedTurns, 0.7);
    }

    @Override
    public Species chooseSpecies(Random random, int waitedTurns) {
        return WeightedPick.pick(random, Map.of(
                Species.PONTY, 50,
                Species.AMUR, 30,
                Species.HARCSA, 5 + 5 * waitedTurns));
    }
}
